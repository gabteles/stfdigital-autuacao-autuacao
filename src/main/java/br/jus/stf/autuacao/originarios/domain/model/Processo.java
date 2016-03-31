package br.jus.stf.autuacao.originarios.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.processo.ProcessoId;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
@Entity
public class Processo extends EntitySupport<Processo, ProcessoId> {

    @EmbeddedId 
    private ProcessoId processoId;
    
    @Embedded
    private ProtocoloId protocoloId;
    
    @Column
    private Status status;
    
    @Column
    private String classeId;
    
    public Processo() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

    public Processo(ProcessoId id, ProtocoloId protocoloId, Status status) {
        this.processoId = id;
        this.protocoloId = protocoloId;
        this.status = status;
    }
    
	public void classificar(String classeId, Status status) {
		this.classeId = classeId;
		this.status = status;
	}
    
    @Override
    public ProcessoId identity() {
        return processoId;
    }

}
