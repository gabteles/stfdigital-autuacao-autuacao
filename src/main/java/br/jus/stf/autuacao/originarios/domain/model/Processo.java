package br.jus.stf.autuacao.originarios.domain.model;

import static java.util.Comparator.comparing;
import static javax.persistence.CascadeType.ALL;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.originarios.domain.model.classe.ClasseOriginaria;
import br.jus.stf.autuacao.originarios.domain.model.preferencia.Preferencia;
import br.jus.stf.core.framework.domaindrivendesign.AggregateRoot;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.processo.ProcessoId;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
@Entity
@Table(name = "PROCESSO", schema = "AUTUACAO")
public class Processo extends EntitySupport<Processo, ProcessoId> implements AggregateRoot<Processo, ProcessoId> {

    @EmbeddedId 
    private ProcessoId processoId;
    
    @Embedded
    private ProtocoloId protocoloId;
    
    @Column(name = "TIP_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToOne
    @JoinColumn(name = "SIG_CLASSE")
	private ClasseOriginaria classe;
    
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
    
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "SEQ_PROCESSO", referencedColumnName = "SEQ_PROCESSO", insertable = false, updatable = false)
    private Rejeicao rejeicao;
    
    public Processo() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

    public Processo(ProcessoId id, ProtocoloId protocoloId, ClasseOriginaria classe, Status status) {
    	Validate.notNull(id, "Processo requerido.");
    	Validate.notNull(protocoloId, "Protocolo requerido.");
    	Validate.notNull(classe, "Classe requerida");
    	Validate.notNull(status, "Status requerido.");
    	
        this.processoId = id;
        this.protocoloId = protocoloId;
        this.classe = classe;
        this.status = status;
    }
    
    public Processo(ProcessoId id, ProtocoloId protocoloId, ClasseOriginaria classe, Set<Preferencia> preferencias, Status status) {
    	this(id, protocoloId, classe, status);
    	
    	Validate.isTrue(!Optional.ofNullable(preferencias).isPresent() || classe.preferencias().containsAll(preferencias),
				"Alguma(s) preferência(s) não pertence(m) à classe selecionada.");
    	
    	this.preferencias = Optional.ofNullable(preferencias).orElse(new HashSet<>(0));
    }
    
    public void autuar(ClasseOriginaria classe, Set<Parte> partes, Autuador autuador, Status status) {
		Validate.notNull(classe, "Classe requerida.");
		Validate.notEmpty(partes, "Partes requeridas.");
		Validate.notNull(autuador, "Autuador requerido.");
		Validate.notNull(status, "Status requerido.");
		
		this.classe = classe;
		this.partes = partes;
		this.status = status;
		this.autuador = autuador;
		this.dataAutuacao = new Date();
	}
    
    public void rejeitar(String motivo, Status status) {
    	Validate.notNull(status, "Status requerido.");
    	
    	rejeicao = new Rejeicao(processoId, motivo);
    	this.status = status;
    }
    
    @Override
    public ProcessoId identity() {
        return processoId;
    }

}
