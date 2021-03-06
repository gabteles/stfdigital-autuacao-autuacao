package br.jus.stf.autuacao.domain.model;

import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static javax.persistence.CascadeType.ALL;

import java.util.Collections;
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

import br.jus.stf.autuacao.domain.model.suportejudicial.Classe;
import br.jus.stf.autuacao.domain.model.suportejudicial.Preferencia;
import br.jus.stf.core.framework.domaindrivendesign.AggregateRoot;
import br.jus.stf.core.framework.domaindrivendesign.DomainEvent;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.eventos.ProcessoAutuado;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;
import br.jus.stf.core.shared.processo.MeioTramitacao;
import br.jus.stf.core.shared.processo.Polo;
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
    
    @OneToMany(cascade = ALL)
    @JoinTable(name = "PROCESSO_EVENTO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_EVENTO", nullable = false))
    private Set<Evento> eventos = new TreeSet<>(comparing(Evento::criacao));
    
    Processo() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

    /**
     * @param id
     * @param protocoloId
     * @param classe
     * @param meioTramitacao
     * @param sigilo
     * @param status
     */
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
        
        registrarEvento(new ProcessoRegistrado(ofNullable(protocoloId).map(p -> p.toLong()).orElse(null), processoId.toString()));
    }
    
	private void registrarEvento(DomainEvent<?> evento) {
		eventos.add(new Evento(evento));
	}
	
    /**
     * @param id
     * @param protocoloId
     * @param classe
     * @param preferencias
     * @param meioTramitacao
     * @param sigilo
     * @param status
     */
    public Processo(ProcessoId id, ProtocoloId protocoloId, Classe classe, Set<Preferencia> preferencias, MeioTramitacao meioTramitacao, Sigilo sigilo, Status status) {
    	this(id, protocoloId, classe, meioTramitacao, sigilo, status);
    	
    	Validate.isTrue(!Optional.ofNullable(preferencias).isPresent() || classe.preferencias().containsAll(preferencias),
				"Alguma(s) preferência(s) não pertence(m) à classe selecionada.");
    	
    	this.preferencias = Optional.ofNullable(preferencias).orElse(new HashSet<>(0));
    }
    
    /**
     * @return
     */
    public abstract TipoProcesso tipo();
    
    /**
     * @param partes
     */
    public void atribuirPartes(Set<Parte> partes) {
		Validate.notEmpty(partes, "Partes requeridas.");
		Validate.isTrue(isPartesValidas(partes), "Polo ativo deve ter ao menos uma partes.");
		
		this.partes.addAll(partes);
	}
    
    /**
     * @param preferencias
     */
    public void atribuirPreferencias(Set<Preferencia> preferencias) {
		Validate.notEmpty(preferencias, "Preferências requeridas.");
		Validate.isTrue(classe.preferencias().containsAll(preferencias),
				"Alguma(s) preferência(s) não pertence(m) à classe selecionada.");
		
		this.preferencias.addAll(preferencias);
	}
    
    protected void autuar(Set<Parte> partes, Autuador autuador, Status status) {
		Validate.notEmpty(partes, "Partes requeridas.");
		Validate.notNull(autuador, "Autuador requerido.");
		Validate.notNull(status, "Status requerido.");
		
		atribuirPartes(partes);
		this.status = status;
		this.autuador = autuador;
		this.dataAutuacao = new Date();
		
		registrarEvento(new ProcessoAutuado(identity().toString(), numero.toString()));
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

	/**
	 * @return
	 */
	public Optional<ProtocoloId> protocoloId() {
		return Optional.ofNullable(protocoloId);
	}
	
	/**
	 * @return
	 */
	public Status status() {
		return status;
	}
	
	/**
	 * @return
	 */
	public Optional<Classe> classe() {
		return Optional.ofNullable(classe);
	}
	
	/**
	 * @return
	 */
	public Optional<Long> numero() {
		return Optional.ofNullable(numero);
	}
	
	/**
	 * @return
	 */
	public String identificacao() {
		return Optional.ofNullable(classe)
				.map(cl -> Optional.ofNullable(numero)
								.map(num -> new StringBuilder(cl.identity().toString())
												.append(" ").append(num).toString())
								.orElse(""))
				.orElse("");
	}
	
	/**
	 * @return
	 */
	public Set<Preferencia> preferencias() {
		return Collections.unmodifiableSet(preferencias);
	}
	
	/**
	 * @return
	 */
	public MeioTramitacao meioTramitacao() {
		return meioTramitacao;
	}
	
	/**
	 * @return
	 */
	public Sigilo sigilo() {
		return sigilo;
	}
	
	/**
	 * @return
	 */
	public Set<Parte> partes() {
		return Collections.unmodifiableSet(partes);
	}
	
	/**
	 * @return
	 */
	public Optional<Autuador> autuador() {
		return Optional.ofNullable(autuador);
	}
	
	/**
	 * @return
	 */
	public Optional<Date> dataAutuacao() {
		return Optional.ofNullable(dataAutuacao);
	}
	
	public boolean isCriminalEleitoral() {
		return preferencias.stream().anyMatch(preferencia -> preferencia.isCriminalEleitoral());
	}
	
	@Override
    public ProcessoId identity() {
        return processoId;
    }
	
	private boolean isPartesValidas(Set<Parte> partes) {
		return partes.stream().filter(parte -> Polo.ATIVO.equals(parte.polo())).count() >= 1;
	}

}
