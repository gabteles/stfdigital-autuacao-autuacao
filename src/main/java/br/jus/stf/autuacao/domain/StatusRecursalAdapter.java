package br.jus.stf.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.Status;
import br.jus.stf.core.framework.workflow.StatusAdapterSupport;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 16.06.2016
 */
@Component("statusProcessoRecursalAdapter")
public class StatusRecursalAdapter extends StatusAdapterSupport<ProcessoId, Status> {

    private static final String RECURSAIS_PROCESS_KEY = "recursais";
    
    private static final String RECURSAIS_ID_PATTERN = "RE:%s";

    @Override
    protected Status statusFromDescription(String description) {
        return Status.valueOf(description);
    }

    @Override
    protected String processId(ProcessoId informationId) {
        return String.format(RECURSAIS_ID_PATTERN, informationId);
    }
    
    @Override
    protected String processKey() {
        return RECURSAIS_PROCESS_KEY;
    }

}
