package br.jus.stf.autuacao.domain.model;

import static javax.persistence.CascadeType.ALL;

import java.util.HashSet;
import java.util.Optional;
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
	
	@Column(name = "FLG_ANALISE_APTA")
	@Type(type = "yes_no")
	private Boolean analiseApta;
	
	@Column(name = "TXT_ANALISE_PRESSUPOSTO_FORMAL")
	private String observacao;
	
	@OneToMany(cascade = ALL)
	@JoinTable(name = "MOTIVACAO_INAPTIDAO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "COD_MOTIVO_INAPTIDAO", nullable = false))
	private Set<MotivoInaptidao> motivacaoInaptidao = new HashSet<>(0);
	
	public AnalisePressupostoFormal() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public AnalisePressupostoFormal(Boolean analiseApta, String observacao, Set<MotivoInaptidao> motivacaoInaptidao) {
		Validate.isTrue(analiseApta || !motivacaoInaptidao.isEmpty(), "Motivação de inaptidão requedida.");
		
		this.analiseApta = analiseApta;
		this.observacao = observacao;
		this.motivacaoInaptidao = Optional.ofNullable(motivacaoInaptidao).orElse(new HashSet<>(0));
	}
	
	public Set<MotivoInaptidao> motivosInaptidao(){
		return this.motivosInaptidao();
	}

}
