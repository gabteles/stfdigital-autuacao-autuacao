package br.jus.stf.autuacao;

import static com.github.jsonj.tools.JsonBuilder.array;
import static com.github.jsonj.tools.JsonBuilder.field;
import static com.github.jsonj.tools.JsonBuilder.object;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import com.github.jsonj.JsonObject;

import br.jus.stf.autuacao.infra.RabbitConfiguration;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;

/**
 * Valida a API de autuação de recursais.
 * 
 * @author Rafael Esdras
 * 
 * @since 1.0.0
 * @since 29.07.2016
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("autuador")
public class ProcessoRecursalIntegrationTests extends IntegrationTestsSupport {
	
	@MockBean
	private RabbitTemplate rabbitTemplate;
	
	@Before
	public void configuracao() {
		willDoNothing().given(rabbitTemplate).convertAndSend(RabbitConfiguration.PROCESSO_REGISTRADO_QUEUE, ProcessoRegistrado.class);
	}
	
	@Test
	public void analiseAptaPressupostosFormais() throws Exception {
		loadDataTests("analisarAptoPressupostosFormais.sql");
		
		JsonObject processoJson = object(
				field("processoId", 9005),
				field("processoApto", true),
				field("observacao", "Análise apta.")
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/analise-pressupostos-formais").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void analiseComRepercussaoGeral() throws Exception {
		loadDataTests("analisarComRepercussaoGeral.sql");

		JsonObject processoJson = object(
				field("processoId", 9007),
				field("teses", array(170)),
				field("assuntos", array("10912","4291")),
				field("repercussaoGeral", true)
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/analise-repercussao-geral").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void revisarRepercussaoGeral() throws Exception {
		loadDataTests("revisarRepercussaoGeral.sql");

		JsonObject processoJson = object(
				field("processoId", 9009),
				field("teses", array(170)),
				field("assuntos", array("10912","4291")),
				field("observacao", "Tema de repercussão geral reconhecido.")
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/revisao-repercussao-geral").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void autuarRecursal() throws Exception {
		loadDataTests("autuarRecursal.sql");

		JsonObject processoJson = object(
				field("processoId", 9010),
				field("poloAtivo", array("Pedro", "Paula")),
				field("poloPassivo", array("Joana")),
				field("assuntos", array("10912","4291"))
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/autuacao").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void analiseSemRepercussaoGeral() throws Exception {
		loadDataTests("analisarSemRepercussaoGeral.sql");

		JsonObject processoJson = object(
				field("processoId", 9008),
				field("teses", array(1354)),
				field("assuntos", array("9981")),
				field("repercussaoGeral", false)
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/analise-repercussao-geral").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void analiseInaptaPressupostosFormais() throws Exception {
		loadDataTests("analisarInaptoPressupostosFormais.sql");
		
		JsonObject processoJson = object(
				field("processoId", 9006),
				field("processoApto", false),
				field("motivosInaptidao", array(5)),
				field("observacao", "Análise inapta.")
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/analise-pressupostos-formais").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void revisarAptoPressupostosFormais() throws Exception {
		loadDataTests("revisarAptoPressupostosFormais.sql");
		
		JsonObject analisarPressupostosFormaisJson = object(
				field("processoId", 9011),
				field("processoApto", true),
				field("observacao", "Inaptidão revertida após revisão.")
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/revisao-pressupostos-formais").contentType(APPLICATION_JSON).content(analisarPressupostosFormaisJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void revisarInaptoPressupostosFormais() throws Exception {
		loadDataTests("revisarInaptoPressupostosFormais.sql");
		
		JsonObject analisarPressupostosFormaisJson = object(
				field("processoId", 9012),
				field("processoApto", false),
				field("motivosInaptidao", array(6)),
				field("observacao", "Inaptidão mantida após revisão.")
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/revisao-pressupostos-formais").contentType(APPLICATION_JSON).content(analisarPressupostosFormaisJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void autuarCriminalEleitoral() throws Exception {
		loadDataTests("autuarCriminalEleitoral.sql");

		JsonObject processoJson = object(
				field("processoId", 9013),
				field("poloAtivo", array("Maria", "José")),
				field("poloPassivo", array("Joaquim")),
				field("assuntos", array("5555","5865"))
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/autuacao-criminal-eleitoral").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
}