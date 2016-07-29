package br.jus.stf.autuacao.domain.model;

import static javax.persistence.CascadeType.ALL;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.domain.model.suportejudicial.Tese;
import br.jus.stf.autuacao.domain.model.suportejudicial.TipoTese;
import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 17.05.2016
 */
@Embeddable
public class AnaliseRepercussaoGeral extends ValueObjectSupport<AnaliseRepercussaoGeral> {
	
	@Column(name = "TXT_ANALISE_REPERCUSSAO_GERAL")
	private String observacao;
	
	@OneToMany(cascade = ALL)
    @JoinTable(name = "PROCESSO_TESE", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_TESE", nullable = false))
	private Set<Tese> teses = new HashSet<>(0);
	
	public AnaliseRepercussaoGeral() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param observacao
	 * @param teses
	 */
	public AnaliseRepercussaoGeral(String observacao, Set<Tese> teses) {
		Validate.notEmpty(teses, "Teses requeridas.");
		
		this.observacao = observacao;
		this.teses.addAll(teses);
	}
	
	/**
	 * @return
	 */
	public String observacao() {
		return observacao;
	}
	
	/**
	 * @return
	 */
	public Set<Tese> teses() {
		return Collections.unmodifiableSet(teses);
	}
	
	/**
	 * @return
	 */
	public boolean temTeseRepercussaoGeral() {
		return teses.stream().anyMatch(tese -> TipoTese.REPERCUSSAO_GERAL.equals(tese.tipo()));
	}

}
