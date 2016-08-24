package br.jus.stf.autuacao.domain.model.procedenciageografica;

import javax.persistence.Column;
import javax.persistence.Id;
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
@Table(name = "PAIS", schema = "AUTUACAO")
public class Pais extends EntitySupport<Pais, Long> {

	@Id
	@Column(name = "SEQ_PAIS")
	private Long id;
	
	@Column(name = "NOM_PAIS", nullable = false)
	private String nome;

	Pais() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param id
	 * @param nome
	 */
	public Pais(Long id, String nome){
		Validate.notNull(id, "Identificador requerido.");
		Validate.notBlank(nome, "Nome requerido.");
		
		this.id = id;
		this.nome = nome;
	}
	
	@Override
	public Long identity() {
		return id;
	}
	
}