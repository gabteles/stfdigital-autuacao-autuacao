package br.jus.stf.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.domain.model.documento.TipoPeca;
import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.documento.DocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
@Entity
@Table(name = "PECA", schema = "AUTUACAO")
public class Peca extends ValueObjectSupport<Peca> {
	
	@Id
	@Column(name = "SEQ_PECA")
	@SequenceGenerator(name = "PECA_ID", sequenceName = "AUTUACAO.SEQ_PECA", allocationSize = 1)
	@GeneratedValue(generator = "PECA_ID", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "DSC_PECA", nullable = false)
	private String descricao;
	
	@ManyToOne
    @JoinColumn(name = "SEQ_TIPO_PECA", nullable = false)
	private TipoPeca tipo;
	
	@Embedded
	@Column(nullable = false)
	private DocumentoId documento;
	
	public Peca() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public Peca(TipoPeca tipo, DocumentoId documento) {
		Validate.notNull(tipo, "Tipo requerido.");
		Validate.notNull(documento, "Documento requerido.");
		
		this.tipo = tipo;
		this.documento = documento;
		this.descricao = tipo.nome();
	}
	
	public Long toLong() {
		return id;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public TipoPeca tipo() {
		return tipo;
	}
	
	public DocumentoId documento() {
		return documento;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", tipo.nome(), descricao);
	}

}
