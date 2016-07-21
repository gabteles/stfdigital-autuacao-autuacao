package br.jus.stf.autuacao.originarios;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.autuacao.ApplicationContextInitializer;
import br.jus.stf.autuacao.domain.model.Autuador;
import br.jus.stf.autuacao.infra.AutuadorOauth2Adapter;
import br.jus.stf.autuacao.infra.NumeroProcessoRestAdapter;
import br.jus.stf.autuacao.infra.RabbitConfiguration;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.processo.Identificacao;

/**
 * Valida a API de autuação de originários.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.02.2016
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false"}, classes = ApplicationContextInitializer.class)
public class ProcessoOriginarioIntegrationTests extends IntegrationTestsSupport {
	
	@Configuration
	@Profile("test")
	static class ConfiguracaoTest {
		
		@Bean
		public RabbitTemplate rabbitTemplate() {
			RabbitTemplate rabbitTemplate = Mockito.mock(RabbitTemplate.class);
			
			willDoNothing().given(rabbitTemplate).convertAndSend(RabbitConfiguration.PROCESSO_REGISTRADO_QUEUE, ProcessoRegistrado.class);
			
			return rabbitTemplate;
		}
		
		@Bean
		public NumeroProcessoRestAdapter numeroProcessoAdapter() {
			NumeroProcessoRestAdapter numeroProcessoAdapter = Mockito.mock(NumeroProcessoRestAdapter.class);
			
			given(numeroProcessoAdapter.novoNumeroProcesso("ADO")).willReturn(new Identificacao("ADO", 1L));
			
			return numeroProcessoAdapter;
		}
		
		@Bean
		public AutuadorOauth2Adapter autuadorAdapter() {
			AutuadorOauth2Adapter autuadorAdapter = Mockito.mock(AutuadorOauth2Adapter.class);
			
			given(autuadorAdapter.autuador()).willReturn(new Autuador("autuador", new PessoaId(1L)));
			
			return autuadorAdapter;
		}
	}
	
	@Test
	public void autuarRemessa() throws Exception {
		loadDataTests("autuarRemessaOriginario.sql");
		
		String processo = "{\"processoId\":@processoId,\"classeId\":\"ADO\",\"poloAtivo\":[{\"apresentacao\":\"Maria\",\"pessoa\":1},{\"apresentacao\":\"João\"}],\"poloPassivo\":[{\"apresentacao\":\"Antônia\",\"pessoa\":3}],\"valida\":true}";
		String processoId = "9000";
		ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		result.andExpect(status().isOk());
	}

	@Test
	public void rejeitarRemessa() throws Exception {
		loadDataTests("rejeitarRemessaOriginario.sql");
		
		String processoId = "9001";
		String processo = "{\"processoId\":@processoId,\"motivo\":\"Protocolo já foi autuado.\",\"valida\":false}";
		ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		result.andExpect(status().isOk());
	}

	@Test
	public void autuarPeticao() throws Exception {
		loadDataTests("autuarPeticaoOriginario.sql");
		
		String processo = "{\"processoId\":@processoId,\"classeId\":\"ADO\",\"poloAtivo\":[{\"apresentacao\":\"Carlos\",\"pessoa\":1},{\"apresentacao\":\"Marta\"}],\"poloPassivo\":[{\"apresentacao\":\"Pedro\",\"pessoa\":3}],\"valida\":true}";
		String processoId = "9002";
		ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		result.andExpect(status().isOk());
	}

	@Test
	public void rejeitarPeticao() throws Exception {
		loadDataTests("rejeitarPeticaoOriginario.sql");
		
		String processoId = "9003";
		String processo = "{\"processoId\":@processoId,\"motivo\":\"Protocolo já foi autuado.\",\"valida\":false}";
		ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		result.andExpect(status().isOk());
	}

	@Test
    public void naoDeveRegistrarRejeitarUmProcessoInvalido() throws Exception {
        String processo = "{\"valida\":true}";
        ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON).content(processo));
        
        result.andExpect(status().isBadRequest());
    }
    
}
