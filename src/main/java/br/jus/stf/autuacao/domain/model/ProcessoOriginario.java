package br.jus.stf.autuacao.domain.model;

import static javax.persistence.CascadeType.ALL;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.domain.model.classe.Classe;
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
@DiscriminatorValue("ORIGINARIO")
public class ProcessoOriginario extends Processo {
	
	@OneToOne(cascade = ALL)
    @JoinColumn(name = "SEQ_PROCESSO", referencedColumnName = "SEQ_PROCESSO", insertable = false, updatable = false)
    private Rejeicao rejeicao;

    public ProcessoOriginario() {
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
	public ProcessoOriginario(ProcessoId id, ProtocoloId protocoloId, Classe classe, MeioTramitacao meioTramitacao,
			Sigilo sigilo, Status status) {
    	super(id, protocoloId, classe, meioTramitacao, sigilo, status);
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
	public ProcessoOriginario(ProcessoId id, ProtocoloId protocoloId, Classe classe, Set<Preferencia> preferencias,
			MeioTramitacao meioTramitacao, Sigilo sigilo, Status status) {
    	super(id, protocoloId, classe, preferencias, meioTramitacao, sigilo, status);
    }
    
    /**
     * @param classe
     * @param numero
     * @param partes
     * @param autuador
     * @param status
     */
    public void autuar(Classe classe, Long numero, Set<Parte> partes, Autuador autuador, Status status) {
		super.identificar(classe, numero);
		super.autuar(partes, autuador, status);
	}
    
    /**
     * @param motivo
     * @param status
     */
    public void rejeitar(String motivo, Status status) {
    	Validate.notNull(status, "Status requerido.");
    	
    	rejeicao = new Rejeicao(identity(), motivo);
    	super.alterarStatus(status);
    }
    
    /**
     * @return
     */
    public String motivoRejeicao() {
    	return rejeicao != null ? rejeicao.motivo() : "";
    }
    
    @Override
    public TipoProcesso tipo() {
    	return TipoProcesso.ORIGINARIO;
    }

}
