package br.jus.stf.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.processo.Polo;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
@Entity
@Table(name = "PARTE", schema = "AUTUACAO")
public class Parte extends ValueObjectSupport<Parte> {
	
	@Id
	@Column(name = "SEQ_PARTE")
	@SequenceGenerator(name = "PARTE_ID", sequenceName = "AUTUACAO.SEQ_PARTE", allocationSize = 1)
	@GeneratedValue(generator = "PARTE_ID", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "NOM_APRESENTACAO", nullable = false)
	private String apresentacao;
	
	@Column(name = "TIP_POLO", nullable = false)
	@Enumerated(EnumType.STRING)
	private Polo polo;
	
	@Embedded
	private PessoaId pessoa;
	
	Parte() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param apresentacao
	 * @param polo
	 * @param pessoa
	 */
	public Parte(String apresentacao, Polo polo, PessoaId pessoa) {
		Validate.notBlank(apresentacao, "Apresentação requerida.");
		Validate.notNull(polo, "Polo requerido.");
		
		this.apresentacao = apresentacao;
		this.polo = polo;
		this.pessoa = pessoa;
	}
	
	/**
	 * @return
	 */
	public Long toLong() {
		return id;
	}
	
	/**
	 * @return
	 */
	public String apresentacao() {
		return apresentacao;
	}
	
	/**
	 * @return
	 */
	public Polo polo() {
		return polo;
	}
	
	/**
	 * @return
	 */
	public PessoaId pessoa() {
		return pessoa;
	}
	
	@Override
	public String toString() {
		return String.format("%s [%s]", apresentacao, polo.descricao());
	}

}
