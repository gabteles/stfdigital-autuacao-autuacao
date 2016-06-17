package br.jus.stf.autuacao.domain.model;

import java.util.List;

import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
public interface ProcessoOriginarioRepository {

    /** Processo **/
	<P extends Processo> P save(P processo);
    
    Processo findOne(ProcessoId id);
    
    ProcessoId nextProcessoId();
    
    /** Motivo de inaptidão **/
    MotivoInaptidao findOneMotivoInaptidao(Long id);
    
    List<MotivoInaptidao> findAllMotivoInaptidao();
    
    <M extends MotivoInaptidao> M saveMotivoInaptidao(M motivoInaptidao);
    
    void deleteMotivoInaptidao(MotivoInaptidao motivoInaptidao);

    List<Parte> consultarPartes(Long processoId);

}
