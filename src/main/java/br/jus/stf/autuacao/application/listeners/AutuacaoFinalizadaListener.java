package br.jus.stf.autuacao.application.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.infra.configuration.StreamConfigurer;
import br.jus.stf.core.shared.eventos.AutuacaoFinalizada;

/**
 * TODO Rodrigo Barreiros Analisar se esse listener está no lugar certo
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@Component
public class AutuacaoFinalizadaListener {
	
	@Autowired
	private StreamConfigurer.Channels channels;

	/** 
	 * TODO Rodrigo Barreiros Analisar se é melhor uma interface com implementação do Rabbit
	 * TODO Rodrigo Barreiros Passar o ProcessoId como variável do processo, como em {@link RecebimentoFinalizadoListener}
	 */
    public void handle(DelegateExecution execution) {
    	GenericMessage<AutuacaoFinalizada> message = new GenericMessage<>(new AutuacaoFinalizada(Long.valueOf(execution.getProcessBusinessKey().substring(3))));
    	
    	channels.autuacaoFinalizada().send(message);
    }
    
}
