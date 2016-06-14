package br.jus.stf.autuacao.domain.model.controletese;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.controletese.TeseId;

@Entity
@Table(name = "TESE", schema = "AUTUACAO")
public class Tese extends EntitySupport<Tese, TeseId> {
	
	@EmbeddedId
	private TeseId id;
	
	@Column(name = "DSC_TESE", nullable = false)
	private String descricao;
	
	@Column(name = "NUM_TESE", nullable = false)
	private Long numero;
	
	@Column(name = "TIP_TESE", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoTese tipo;
	
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "TESE_ASSUNTO", schema = "AUTUACAO",
		joinColumns = @JoinColumn(name = "SEQ_TESE", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "COD_ASSUNTO", nullable = false))
	private Set<Assunto> assuntos = new HashSet<Assunto>(0);
	
	public Tese() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public Tese(TeseId id, String descricao, TipoTese tipo, Long numero) {
		Validate.notNull(id, "Identificador requerido.");
		Validate.notBlank(descricao, "Descrição requerida.");
		Validate.notNull(tipo, "Tipo de tese requerido.");
		Validate.notNull(numero, "Número requerido.");
		
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.numero = numero;
	}
	
	public TipoTese tipo() {
		return tipo;
	}
	
	public Set<Assunto> assuntos() {
		return Collections.unmodifiableSet(assuntos);
	}
	
	public String descricao() {
		return descricao;
	}
	
	public Long numero() {
		return numero;
	}

	@Override
	public String toString() {
		return String.format("%d - %s [%s]", numero, descricao, tipo.descricao());
	}
	
	@Override
	public TeseId identity() {
		return id;
	}

}
