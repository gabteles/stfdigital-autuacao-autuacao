package br.jus.stf.autuacao.infra.configuration;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import br.jus.stf.core.framework.stream.StreamConfigurerSupport;
import br.jus.stf.core.shared.eventos.AutuacaoFinalizada;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;
import br.jus.stf.core.shared.eventos.ProcessoAutuado;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;
import br.jus.stf.core.shared.eventos.RecebimentoFinalizado;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.08.2016
 */
@EnableBinding(StreamConfigurer.Channels.class)
public class StreamConfigurer extends StreamConfigurerSupport {
	
	/**
	 * @see br.jus.stf.core.framework.stream.StreamConfigurerSupport#serviceSchema()
	 */
	@Override
	protected String serviceSchema() {
		return "autuacao";
	}
	
	/**
	 * Configuração dos canais que serão usados pelo serviço de peticionamento
	 * para publicação de eventos de domínio.
	 * 
	 * @author Rodrigo Barreiros
	 * 
	 * @since 1.0.0
	 * @since 18.08.2016
	 */
	public interface Channels {

		/**
		 * O canal que será usado para publicação de eventos do tipo {@link PeticaoRegistrada}.
		 * 
		 * @return o canal para as petições registradas
		 */
		@Input(PeticaoRegistrada.EVENT_KEY)
		SubscribableChannel peticaoRegistrada();

		@Input(RecebimentoFinalizado.EVENT_KEY)
		SubscribableChannel recebimentoFinalizado();
		
		@Output(ProcessoRegistrado.EVENT_KEY)
		MessageChannel processoRegistrado();
		
		@Output(ProcessoAutuado.EVENT_KEY)
		MessageChannel processoAutuado();
		
		@Output(AutuacaoFinalizada.EVENT_KEY)
		MessageChannel autuacaoFinalizada();

	}

}
