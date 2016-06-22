package br.jus.stf.autuacao.domain.model;

import static java.util.Comparator.comparing;
import static javax.persistence.CascadeType.ALL;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.domain.model.classe.Classe;
import br.jus.stf.autuacao.domain.model.preferencia.Preferencia;
import br.jus.stf.core.framework.domaindrivendesign.AggregateRoot;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.processo.MeioTramitacao;
import br.jus.stf.core.shared.processo.ProcessoId;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIP_PROCESSO")
@Table(name = "PROCESSO", schema = "AUTUACAO")
public abstract class Processo extends EntitySupport<Processo, ProcessoId> implements AggregateRoot<Processo, ProcessoId> {

    @EmbeddedId 
    private ProcessoId processoId;
    
    @Embedded
    private ProtocoloId protocoloId;
    
    @Column(name = "TIP_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToOne
    @JoinColumn(name = "SIG_CLASSE")
	private Classe classe;
    
    @Column(name = "NUM_PROCESSO")
    private Long numero;
    
    @OneToMany(cascade = ALL)
    @JoinTable(name = "PROCESSO_PREFERENCIA", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PREFERENCIA", nullable = false))
    private Set<Preferencia> preferencias = new HashSet<>(0);
    
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "SEQ_PROCESSO", referencedColumnName = "SEQ_PROCESSO", nullable = false)
    private Set<Parte> partes = new TreeSet<>(comparing(Parte::apresentacao));
    
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "SEQ_PROCESSO", referencedColumnName = "SEQ_PROCESSO", nullable = false)
    private Set<Peca> pecas = new HashSet<>(0);
    
    @Embedded
    private Autuador autuador;
    
    @Column(name = "DAT_AUTUACAO")
    private Date dataAutuacao;
    
    @Column(name = "TIP_MEIO_TRAMITACAO", nullable = false)
	@Enumerated(EnumType.STRING)
    private MeioTramitacao meioTramitacao;
    
    @Column(name = "TIP_SIGILO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sigilo sigilo;
    
    public Processo() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

    public Processo(ProcessoId id, ProtocoloId protocoloId, Classe classe, MeioTramitacao meioTramitacao, Sigilo sigilo, Status status) {
    	Validate.notNull(id, "Processo requerido.");
    	Validate.notNull(classe, "Classe requerida.");
    	Validate.notNull(meioTramitacao, "Meio de tramitação requerido.");
    	Validate.notNull(sigilo, "Sigilo requerido.");
    	Validate.notNull(status, "Status requerido.");
    	Validate.isTrue(tipo().equals(classe.tipo()), "O tipo do processo e da classe são incompatíveis.");
    	
        this.processoId = id;
        this.protocoloId = protocoloId;
        this.classe = classe;
        this.meioTramitacao = meioTramitacao;
        this.sigilo = sigilo;
        this.status = status;
    }
    
    public Processo(ProcessoId id, ProtocoloId protocoloId, Classe classe, Set<Preferencia> preferencias, MeioTramitacao meioTramitacao, Sigilo sigilo, Status status) {
    	this(id, protocoloId, classe, meioTramitacao, sigilo, status);
    	
    	Validate.isTrue(!Optional.ofNullable(preferencias).isPresent() || classe.preferencias().containsAll(preferencias),
				"Alguma(s) preferência(s) não pertence(m) à classe selecionada.");
    	
    	this.preferencias = Optional.ofNullable(preferencias).orElse(new HashSet<>(0));
    }
    
    public abstract TipoProcesso tipo();
    
    @Override
    public ProcessoId identity() {
        return processoId;
    }
    
    public void atribuirPartes(Set<Parte> partes) {
		Validate.notEmpty(partes, "Partes requeridas.");
		
		this.partes.addAll(partes);
	}
    
    protected void autuar(Set<Parte> partes, Autuador autuador, Status status) {
		Validate.notEmpty(partes, "Partes requeridas.");
		Validate.notNull(autuador, "Autuador requerido.");
		Validate.notNull(status, "Status requerido.");
		
		this.partes = partes;
		this.status = status;
		this.autuador = autuador;
		this.dataAutuacao = new Date();
	}
    
    protected void identificar(Classe classe, Long numero) {
    	Validate.notNull(classe, "Classe requerida.");
		Validate.notNull(numero, "Número requerido.");
		Validate.isTrue(numero > 0, "Número deve ser maior que zero.");
		
		this.classe = classe;
		this.numero = numero;
    }
    
    protected void alterarStatus(Status status) {
    	Validate.notNull(status, "Status requerido.");
    	
    	this.status = status;
    }

	public ProtocoloId protocoloId() {
		return protocoloId;
	}
	
	public boolean isCriminalEleitoral() {
		return preferencias.stream().anyMatch(preferencia -> preferencia.isCriminalEleitoral());
	}

}
