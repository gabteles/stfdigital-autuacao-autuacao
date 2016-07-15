package br.jus.stf.autuacao.domain.model;

import java.util.List;

import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
public interface ProcessoRepository {

    // Processo
	/**
	 * @param processo
	 * @return
	 */
	<P extends Processo> P save(P processo);
    
    /**
     * @param id
     * @return
     */
    Processo findOne(ProcessoId id);
    
    /**
     * @return
     */
    ProcessoId nextProcessoId();
    
    // Motivo de inaptidão
    /**
     * @param id
     * @return
     */
    MotivoInaptidao findOneMotivoInaptidao(Long id);
    
    /**
     * @return
     */
    List<MotivoInaptidao> findAllMotivoInaptidao();
    
    /**
     * @param motivoInaptidao
     * @return
     */
    <M extends MotivoInaptidao> M saveMotivoInaptidao(M motivoInaptidao);
    
    /**
     * @param motivoInaptidao
     */
    void deleteMotivoInaptidao(MotivoInaptidao motivoInaptidao);

    /**
     * @param processoId
     * @return
     */
    List<Parte> consultarPartes(Long processoId);

}
