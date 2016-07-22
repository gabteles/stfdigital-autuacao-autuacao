package br.jus.stf.autuacao;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.autuacao.application.AutuacaoApplicationService;
import br.jus.stf.autuacao.application.commands.IniciarAutuacaoCommand;
import br.jus.stf.autuacao.infra.NumeroProcessoRestAdapter;
import br.jus.stf.autuacao.infra.RabbitConfiguration;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;
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
@WithMockOauth2User("autuador")
@Ignore
public class ProcessoRecursalIntegrationTests extends IntegrationTestsSupport {
	
	@MockBean
	private RabbitTemplate rabbitTemplate;
	
	@MockBean
	private NumeroProcessoRestAdapter numeroProcessoAdapter;
	
	@Autowired
	private AutuacaoApplicationService appService;
	
	@Before
	public void configuracao() {
		willDoNothing().given(rabbitTemplate).convertAndSend(RabbitConfiguration.PROCESSO_REGISTRADO_QUEUE, ProcessoRegistrado.class);
		given(numeroProcessoAdapter.novoNumeroProcesso("ADO")).willReturn(new Identificacao("ADO", 1L));
	}
	
	@Ignore
	@Test
	public void criarCenariosProcessoRecursal() throws Exception {
		IniciarAutuacaoCommand iniciar = new IniciarAutuacaoCommand(500L, "RE", "RECURSAL", "ELETRONICO", "PUBLICO", false);
		
		appService.handle(iniciar);

		String processo = "{\"processoId\":@processoId,\"analiseApta\":true}";
		String processoId = "1";
		ResultActions result = mockMvc.perform(post("/api/processos/analise-pressupostos").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		processo = "{\"processoId\":@processoId,\"teses\":[170],\"assuntos\":[\"10912\",\"4291\"], \"repercussaoGeral\":true}";
		result = mockMvc.perform(post("/api/processos/analise-repercussao-geral").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		processo = "{\"processoId\":@processoId,\"teses\":[170],\"assuntos\":[\"10912\",\"4291\"]}";
		result = mockMvc.perform(post("/api/processos/revisao-repercussao-geral").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		processo = "{\"processoId\":@processoId,\"poloAtivo\":[\"Maria\"],\"poloPassivo\":[\"João\"],\"assuntos\":[\"10912\"]}";
		result = mockMvc.perform(post("/api/processos/autuacao/recursal").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		result.andExpect(status().isOk());
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
