package br.jus.stf.autuacao.domain.model.identidade;

import static javax.persistence.CascadeType.ALL;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.domain.model.procedenciageografica.UnidadeFederacao;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 17.05.2016
 */
@javax.persistence.Entity
@Table(name = "TRIBUNAL_JUIZO", schema = "AUTUACAO")
public class TribunalJuizo extends EntitySupport<TribunalJuizo, Long> {

	@Id
	@Column(name = "COD_TRIBUNAL_JUIZO")
	private Long codigo;
	
	@Column(name = "NOM_TRIBUNAL_JUIZO", nullable = false)
	private String nome;
	
	@OneToMany(cascade = ALL)
	@JoinTable(name = "TRIBUNAL_JUIZO_ATUACAO", schema = "AUTUACAO",
		joinColumns = @JoinColumn(name = "COD_TRIBUNAL_JUIZO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_UNIDADE_FEDERACAO", nullable = false))
	private Set<UnidadeFederacao> ufsAtuacao = new HashSet<>(0);
	
	public TribunalJuizo() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public TribunalJuizo(Long codigo, String nome, Set<UnidadeFederacao> ufsAtuacao){
		Validate.notNull(codigo, "Código requerido.");
		Validate.notBlank(nome, "Nome requerido.");
		
		this.codigo = codigo;
		this.nome = nome;
		this.ufsAtuacao = Optional.ofNullable(ufsAtuacao).orElse(new HashSet<>(0));
	}

	@Override
	public Long identity(){
		return codigo;
	}
	
	public Set<UnidadeFederacao> ufsAtuacao(){
		return Collections.unmodifiableSet(ufsAtuacao);
	}
	
}