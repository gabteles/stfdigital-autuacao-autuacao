package br.jus.stf.autuacao.domain.model.procedenciageografica;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 17.05.2016
 */
@javax.persistence.Entity
@Table(name = "UNIDADE_FEDERACAO", schema = "AUTUACAO")
public class UnidadeFederacao extends EntitySupport<UnidadeFederacao, Long> {

	@Id
	@Column(name = "SEQ_UNIDADE_FEDERACAO")
	private Long id;
	
	@Column(name = "NOM_UNIDADE_FEDERACAO", nullable = false)
	private String nome;
	
	@Column(name = "SIG_UNIDADE_FEDERACAO", nullable = false)
	private String sigla;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_PAIS", nullable = false)
	private Pais pais;

	public UnidadeFederacao() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public UnidadeFederacao(Long id, String nome, String sigla, Pais pais){
		Validate.notNull(id, "Identificador requerido.");
		Validate.notBlank(nome, "Nome requerido.");
		Validate.notBlank(sigla, "Sigla requerida.");
		Validate.notNull(pais, "País requerido.");
		
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
		this.pais = pais;
	}

	@Override
	public Long identity(){
		return id;
	}
	
}