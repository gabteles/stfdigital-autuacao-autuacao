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
 * Valida a API de autuação de originários.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.02.2016
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false", "spring.cloud.config.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User(value = "autuador", components = "autuar-originario")
public class ProcessoOriginarioIntegrationTests extends IntegrationTestsSupport {
	
	@MockBean
	private RabbitTemplate rabbitTemplate;
	
	@MockBean
	private NumeroProcessoRestAdapter numeroProcessoAdapter;
	
	@Before
	public void configuracao() {
		given(numeroProcessoAdapter.novoNumeroProcesso("ADO")).willReturn(new Identificacao("ADO", 1L));
	}
	
	@Test
	public void autuarRemessa() throws Exception {
		loadDataTests("autuarRemessaOriginario.sql");
		
		JsonObject autuarRemessaJson = object(
				field("processoId", 9000),
				field("classeId", "ADO"),
				field("poloAtivo", array(
						object(
								field("apresentacao", "Maria"),
								field("pessoa",1)
						),
						object(
								field("apresentacao", "João")
						))),
				field("poloPassivo", array(
						object(
								field("apresentacao", "Antônia"),
								field("pessoa", 3)
						))),
				field("valida", true)
		);
		ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON).content(autuarRemessaJson.toString()));
		
		result.andExpect(status().isOk());
	}

	@Test
	public void rejeitarRemessa() throws Exception {
		loadDataTests("rejeitarRemessaOriginario.sql");
		
		JsonObject rejeitarRemessaJson = object(
				field("processoId", 9001),
				field("motivo", "Protocolo já foi autuado."),
				field("valida", false)
		);
		ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON)
				.content(rejeitarRemessaJson.toString()));
		
		result.andExpect(status().isOk());
	}

	@Test
	public void autuarPeticao() throws Exception {
		loadDataTests("autuarPeticaoOriginario.sql");
		
		JsonObject autuarPeticaoJson = object(
				field("processoId", 9002),
				field("classeId", "ADO"),
				field("poloAtivo", array(
						object(
								field("apresentacao", "Carlos"),
								field("pessoa", 1)
						),
						object(
								field("apresentacao", "Marta")
						))),
				field("poloPassivo", array(
						object(
								field("apresentacao", "Pedro"),
								field("pessoa", 3)
						))),
				field("valida", true)
		);
		ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON).content(autuarPeticaoJson.toString()));
		
		result.andExpect(status().isOk());
	}

	@Test
	public void rejeitarPeticao() throws Exception {
		loadDataTests("rejeitarPeticaoOriginario.sql");
		
		JsonObject rejeitarPeticaoJson = object(
				field("processoId", 9003),
				field("motivo", "Protocolo já foi autuado."),
				field("valida", false)
		);
		ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON)
				.content(rejeitarPeticaoJson.toString()));
		
		result.andExpect(status().isOk());
	}

	@Test
    public void naoDeveRegistrarRejeitarUmProcessoInvalido() throws Exception {
        String processo = "{\"valida\":true}";
        ResultActions result = mockMvc.perform(post("/api/processos/originario/autuacao").contentType(APPLICATION_JSON).content(processo));
        
        result.andExpect(status().isBadRequest());
    }
    
}
