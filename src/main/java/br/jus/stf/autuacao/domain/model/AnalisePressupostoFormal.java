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
import org.hibernate.annotations.Type;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 16.05.2016
 */
@Embeddable
public class AnalisePressupostoFormal extends ValueObjectSupport<AnalisePressupostoFormal> {
	
	@Column(name = "FLG_PROCESSO_APTO")
	@Type(type = "yes_no")
	private Boolean processoApto;
	
	@Column(name = "TXT_ANALISE_PRESSUPOSTO_FORMAL")
	private String observacao;
	
	@OneToMany(cascade = ALL)
	@JoinTable(name = "MOTIVACAO_INAPTIDAO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "COD_MOTIVO_INAPTIDAO", nullable = false))
	private Set<MotivoInaptidao> motivacaoInaptidao = new HashSet<>(0);
	
	AnalisePressupostoFormal() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public AnalisePressupostoFormal(boolean processoApto, String observacao, Set<MotivoInaptidao> motivacaoInaptidao) {
		Validate.isTrue(processoApto || (motivacaoInaptidao != null && !motivacaoInaptidao.isEmpty()), "Motivação de inaptidão requedida.");
		
		this.processoApto = processoApto;
		this.observacao = observacao;
		this.motivacaoInaptidao = motivacaoInaptidao;
	}
	
	public boolean processoApto() {
		return Boolean.TRUE.equals(processoApto);
	}
	
	public String observacao() {
		return observacao;
	}
	
	public Set<MotivoInaptidao> motivosInaptidao(){
		return Collections.unmodifiableSet(motivacaoInaptidao);
	}

}
