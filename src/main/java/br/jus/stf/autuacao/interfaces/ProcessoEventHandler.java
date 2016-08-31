package br.jus.stf.autuacao.interfaces;

import static br.jus.stf.core.shared.processo.MeioTramitacao.ELETRONICO;
import static br.jus.stf.core.shared.processo.MeioTramitacao.FISICO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.application.AutuacaoApplicationService;
import br.jus.stf.autuacao.application.commands.IniciarAutuacaoCommand;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;
import br.jus.stf.core.shared.eventos.RecebimentoFinalizado;

/**
 * Trata todos os eventos que possam dar início ao processo de autuação.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.12.2015
 */
@Component
public class ProcessoEventHandler {
    
    @Autowired
    private AutuacaoApplicationService autuacaoApplicationService;
    
	/**
	 * Trata os eventos do tipo {@link PeticaoRegistrada}, dando início ao processo de 
	 * autuação do processo eletrônico resultante da petição registrada.
	 * 
	 * @param event o evento de registro da petição
	 */
	@StreamListener(PeticaoRegistrada.EVENT_KEY)
    public void handle(PeticaoRegistrada event) {
        autuacaoApplicationService.handle(new IniciarAutuacaoCommand(
        		event.getProtocoloId(), 
        		event.getClasseId(), 
        		event.getTipoProcesso(),
        		ELETRONICO.toString(), 
        		event.getSigilo(), 
        		event.isCriminalEleitoral()));
    }

	/**
	 * Trata os eventos do tipo {@link RecebimentoFinalizado}, dando início ao processo de 
	 * autuação do processo físico resultante da remessa recebida.
	 * 
	 * @param event o evento de registro da remssa
	 */
	@StreamListener(RecebimentoFinalizado.EVENT_KEY)
    public void handle(RecebimentoFinalizado event) {
        autuacaoApplicationService.handle(new IniciarAutuacaoCommand(
        		event.getProtocoloId(), 
        		event.getClasseId(), 
        		event.getTipoProcesso(), 
        		FISICO.toString(), 
        		event.getSigilo(), 
        		event.isCriminalEleitoral()));
    }

}
