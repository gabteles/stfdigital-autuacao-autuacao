package br.jus.stf.autuacao;

import static com.github.jsonj.tools.JsonBuilder.array;
import static com.github.jsonj.tools.JsonBuilder.field;
import static com.github.jsonj.tools.JsonBuilder.object;
import static org.mockito.BDDMockito.given;
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

import br.jus.stf.autuacao.infra.NumeroProcessoRestAdapter;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;
import br.jus.stf.core.shared.processo.Identificacao;

/**
 * Valida a API de autuação de recursais.
 * 
 * @author Rafael Esdras
 * 
 * @since 1.0.0
 * @since 29.07.2016
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false", "spring.cloud.config.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("autuador")
public class ProcessoRecursalIntegrationTests extends IntegrationTestsSupport {
	
	@MockBean
	private RabbitTemplate rabbitTemplate;
	
	@MockBean
	private NumeroProcessoRestAdapter numeroProcessoAdapter;
	
	@Before
	public void configuracao() {
		given(numeroProcessoAdapter.novoNumeroProcesso("RE")).willReturn(new Identificacao("RE", 1L));
	}
	
	@Test
	@WithMockOauth2User(value = "autuador", components = "analisar-pressupostos-formais")
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
    public void naoDeveAnalisarPressupostosFormaisSemProcesso() throws Exception {
		JsonObject processoJson = object(
				field("processoApto", true),
				field("observacao", "Análise apta.")
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/analise-pressupostos-formais").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isBadRequest());
    }
	
	@Test
	@WithMockOauth2User(value = "autuador", components = "analisar-repercussao-geral")
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
    public void naoDeveAnalisarRepercussaoGeralSemProcesso() throws Exception {
		JsonObject processoJson = object(
				field("teses", array(170)),
				field("assuntos", array("10912","4291")),
				field("repercussaoGeral", true)
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/analise-repercussao-geral").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isBadRequest());
    }
	
	@Test
	@WithMockOauth2User(value = "autuador", components = "revisar-repercussao-geral")
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
    public void naoDeveRevisarRepercussaoGeralSemProcesso() throws Exception {
		JsonObject processoJson = object(
				field("teses", array(170)),
				field("assuntos", array("10912","4291")),
				field("observacao", "Tema de repercussão geral reconhecido.")
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/revisao-repercussao-geral").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isBadRequest());
    }
	
	@Test
	@WithMockOauth2User(value = "autuador-recursal", components = "autuar-recursal")
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
    public void naoDeveAutuarRecursalSemProcesso() throws Exception {
		JsonObject processoJson = object(
				field("poloAtivo", array("Pedro", "Paula")),
				field("poloPassivo", array("Joana")),
				field("assuntos", array("10912","4291"))
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/autuacao").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isBadRequest());
    }
	
	@Test
	@WithMockOauth2User(value = "autuador", components = "analisar-repercussao-geral")
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
	@WithMockOauth2User(value = "autuador", components = "analisar-pressupostos-formais")
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
	@WithMockOauth2User(value = "autuador", components = "revisar-pressupostos-formais")
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
	@WithMockOauth2User(value = "autuador", components = "revisar-pressupostos-formais")
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
    public void naoDeveRevisarPressupostosFormaisSemProcesso() throws Exception {
		JsonObject processoJson = object(
				field("processoApto", false),
				field("motivosInaptidao", array(6)),
				field("observacao", "Inaptidão mantida após revisão.")
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/revisao-pressupostos-formais").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isBadRequest());
    }
	
	@Test
	@WithMockOauth2User(value = "autuador", components = "autuar-recursal-criminal-eleitoral")
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
	
	@Test
    public void naoDeveAutuarCriminalEleitoralSemProcesso() throws Exception {
		JsonObject processoJson = object(
				field("poloAtivo", array("Maria", "José")),
				field("poloPassivo", array("Joaquim")),
				field("assuntos", array("5555","5865"))
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/autuacao-criminal-eleitoral").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isBadRequest());
    }
	
	@Test
	@WithMockOauth2User(value = "autuador", components = "enviar-processo-recursal")
	public void enviarProcesso() throws Exception {
		JsonObject processoJson = object(
				field("classeId", "RE"),
				field("sigilo", "SEGREDO_JUSTICA"),
				field("numeroRecursos", 2),
				field("preferencias", array(8)),
				field("origens", array(
						object(
								field("unidadeFederacaoId", 16),
								field("codigoJuizoOrigem", 5187),
								field("numeroProcesso", 1235201),
								field("numeroOrdem", 1),
								field("isPrincipal", true)
						))),
				field("assuntos", array("5555","5865")),
				field("partesPoloAtivo", array(
						object(
								field("apresentacao", "Severina"),
								field("pessoa", 3)
						))),
				field("partesPoloPassivo", array(
						object(
								field("apresentacao", "Severino"),
								field("pessoa", 5)
						))),
				field("criminalEleitoral", false)
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/envio").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveEnviarProcessoComDadosInvalidos() throws Exception {
		JsonObject processoJson = object(
				field("sigilo", "SEGREDO_JUSTICA"),
				field("numeroRecursos", 2),
				field("preferencias", array(8)),
				field("origens", array(
						object(
								field("unidadeFederacaoId", 16),
								field("codigoJuizoOrigem", 5187),
								field("numeroProcesso", 1235201),
								field("numeroOrdem", 1),
								field("isPrincipal", true)
						))),
				field("assuntos", array("5555","5865")),
				field("partesPoloAtivo", array(
						object(
								field("apresentacao", "Severina"),
								field("pessoa", 3)
						))),
				field("partesPoloPassivo", array(
						object(
								field("apresentacao", "Severino"),
								field("pessoa", 5)
						)))
		);
		ResultActions result = mockMvc.perform(post("/api/processos/recursal/envio").contentType(APPLICATION_JSON).content(processoJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
	
}