package br.jus.stf.autuacao.domain.model.preferencia;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.preferencia.PreferenciaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
@Entity
@Table(name = "PREFERENCIA", schema = "AUTUACAO")
public class Preferencia extends EntitySupport<Preferencia, PreferenciaId> {
	
	@EmbeddedId
	private PreferenciaId id;
	
	@Column(name = "NOM_PREFERENCIA", nullable = false)
	private String nome;
	
	public Preferencia() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param id
	 * @param nome
	 */
	public Preferencia(PreferenciaId id, String nome) {
		Validate.notNull(id, "Id requerido.");
		Validate.notBlank(nome, "Nome requerida.");
		
		this.id = id;
		this.nome = nome;
	}
	
	/**
	 * @return
	 */
	public String nome() {
		return nome;
	}
	
	public boolean isCriminalEleitoral() {
		// TODO: Verificar uma forma melhor de implementar essa verificação
		return id.toLong() == 2L || id.toLong() == 3L;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", id.toString(), nome);
	}
	
	@Override
	public PreferenciaId identity() {
		return id;
	}

}
