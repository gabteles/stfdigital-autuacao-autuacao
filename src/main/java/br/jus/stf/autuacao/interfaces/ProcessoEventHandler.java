package br.jus.stf.autuacao.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.application.AutuacaoApplicationService;
import br.jus.stf.autuacao.application.commands.IniciarAutuacaoCommand;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;
import br.jus.stf.core.shared.eventos.RecebimentoFinalizado;
import br.jus.stf.core.shared.processo.MeioTramitacao;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.12.2015
 */
@Component
@Profile("!test")
public class ProcessoEventHandler {
    
    @Autowired
    private AutuacaoApplicationService originariosApplicationService;
    
	@StreamListener(PeticaoRegistrada.EVENT_KEY)
    public void handle(PeticaoRegistrada event) {
        originariosApplicationService.handle(new IniciarAutuacaoCommand(event.getProtocoloId(), event.getClasseId(), event.getTipoProcesso(), MeioTramitacao.ELETRONICO.toString(), event.getSigilo(), event.isCriminalEleitoral()));
    }

	@StreamListener(RecebimentoFinalizado.EVENT_KEY)
    public void handle(RecebimentoFinalizado event) {
        originariosApplicationService.handle(new IniciarAutuacaoCommand(event.getProtocoloId(), event.getClasseId(), event.getTipoProcesso(), MeioTramitacao.FISICO.toString(), event.getSigilo(), event.isCriminalEleitoral()));
    }

}
