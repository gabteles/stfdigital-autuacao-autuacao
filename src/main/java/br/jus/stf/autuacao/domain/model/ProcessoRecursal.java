package br.jus.stf.autuacao.domain.model;

import static javax.persistence.CascadeType.ALL;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.domain.model.classe.Classe;
import br.jus.stf.autuacao.domain.model.controletese.Assunto;
import br.jus.stf.autuacao.domain.model.controletese.Tese;
import br.jus.stf.autuacao.domain.model.preferencia.Preferencia;
import br.jus.stf.core.shared.processo.MeioTramitacao;
import br.jus.stf.core.shared.processo.ProcessoId;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 13.05.2016
 */
@Entity
@DiscriminatorValue("RECURSAL")
public class ProcessoRecursal extends Processo {
	
	@Column(name = "QTD_RECURSO")
	private Integer quantidadeRecurso;
	
	@Embedded
	private AnalisePressupostoFormal analisePressupostoFormal;
	
	@Embedded
	private AnaliseRepercussaoGeral analiseRepercussaoGeral;
	
	@OneToMany(cascade = ALL)
    @JoinTable(name = "PROCESSO_ASSUNTO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "COD_ASSUNTO", nullable = false))
	private Set<Assunto> assuntos = new HashSet<>(0);
	
	@OneToMany(cascade = ALL, targetEntity = Origem.class)
	@JoinColumn(name = "SEQ_PROCESSO", nullable = false)
	private Set<Origem> origens = new HashSet<>(0);
	
    public ProcessoRecursal() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

	public ProcessoRecursal(ProcessoId id, ProtocoloId protocoloId, Classe classe, Long numero, MeioTramitacao meioTramitacao,
			Sigilo sigilo, Status status) {
    	super(id, protocoloId, classe, meioTramitacao, sigilo, status);
    	super.identificar(classe, numero);
    }
    
	public ProcessoRecursal(ProcessoId id, ProtocoloId protocoloId, Classe classe, Long numero,
			Set<Preferencia> preferencias, MeioTramitacao meioTramitacao, Integer quantidadeRecurso, Sigilo sigilo,
			Status status) {
    	super(id, protocoloId, classe, preferencias, meioTramitacao, sigilo, status);
    	super.identificar(classe, numero);
    	
    	Validate.isTrue(quantidadeRecurso >= 0, "Quantidade de recurso deve ser maior ou igual a zero.");
    	
    	this.quantidadeRecurso = quantidadeRecurso;
    }
    
    public void analisarPressupostosFormais(boolean analiseApta, String observacao, Set<MotivoInaptidao> motivacaoInaptidao, Status status) {
    	analisePressupostoFormal = new AnalisePressupostoFormal(analiseApta, observacao, motivacaoInaptidao);
    	super.alterarStatus(status);
	}
    
    public void analisarRepercussaoGeral(String observacao, Set<Tese> teses, Set<Assunto> assuntos, Status status) {
    	Validate.notEmpty(assuntos, "Assuntos requeridos.");
    	Validate.isTrue(teses.stream().allMatch(tese -> assuntos.containsAll(tese.assuntos())),
				"Assuntos devem conter ao menos os que pertencem às teses selecionadas.");
    	
    	analiseRepercussaoGeral = new AnaliseRepercussaoGeral(observacao, teses);
    	this.assuntos.addAll(assuntos);
    	super.alterarStatus(status);
	}
    
    public void atribuirAssuntos(Set<Assunto> assuntos) {
		Validate.notEmpty(assuntos, "Assuntos requeridos.");
		
		this.assuntos.addAll(assuntos);
	}
    
    public void atribuirOrigens(Set<Origem> origens) {
		Validate.notEmpty(origens, "Origens requeridas.");
		Validate.isTrue(origens.stream().filter(origem -> origem.origemPrincipal()).count() == 1,
				"Apenas uma origem deve ser principal.");
		
		this.origens.addAll(origens);
	}
    
    public void autuar(Set<Assunto> assuntos, Set<Parte> partes, Autuador autuador, Status status) {
    	Validate.notNull(assuntos, "Assuntos requeridos.");
    	
		super.autuar(partes, autuador, status);
		this.assuntos.addAll(assuntos);
	}
    
    public Set<MotivoInaptidao> motivosInaptidao(){
    	return this.analisePressupostoFormal.motivosInaptidao();
    }
    
    public boolean isRepercursaoGeral() {
    	return analiseRepercussaoGeral.revisarRepercussaoGeral();
    }
    
    @Override
    public TipoProcesso tipo() {
    	return TipoProcesso.RECURSAL;
    }

}
