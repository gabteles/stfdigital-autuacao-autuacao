package br.jus.stf.autuacao.originarios.domain.model.documento;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
@Entity
@Table(name = "TIPO_PECA", schema = "AUTUACAO")
public class TipoPeca extends EntitySupport<TipoPeca, TipoDocumentoId> {
	
	@EmbeddedId
	private TipoDocumentoId id;
	
	@Column(name = "NOM_TIPO_DOCUMENTO", nullable = false)
	private String nome;
	
	public TipoPeca() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public TipoPeca(TipoDocumentoId id, String nome) {
		Validate.notNull(id, "Id requerido.");
		Validate.notBlank(nome, "Nome requerido.");
		
		this.id = id;
		this.nome = nome;
	}
	
	public String nome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return String.format("%d - %s", id.toLong(), nome);
	}
	
	@Override
	public TipoDocumentoId identity() {
		return id;
	}

}