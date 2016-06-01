package br.jus.stf.autuacao.originarios;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * Valida a API de autuação de originários.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.02.2016
 */
@Ignore
@SpringApplicationConfiguration(ApplicationContextInitializer.class)
public class ProcessoOriginarioIntegrationTests extends IntegrationTestsSupport {
	
	@Test
	public void autuarProcesso() throws Exception {
		loadDataTests("autuarProcesso.sql");
		
		String processo = "{\"processoId\":@processoId,\"classeId\":\"ADO\",\"poloAtivo\":[{\"apresentacao\":\"Maria\",\"pessoa\":1},{\"apresentacao\":\"João\"}],\"poloPassivo\":[{\"apresentacao\":\"Antônia\",\"pessoa\":3}]}";
		String processoId = "9000";
		ResultActions result = mockMvc.perform(post("/api/processos/autuacao").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void rejeitarProcesso() throws Exception {
		loadDataTests("rejeitarProcesso.sql");
		
		String processoId = "9001";
		String processo = "{\"processoId\":@processoId,\"motivo\":\"Protocolo já foi autuado.\"}";
		ResultActions result = mockMvc.perform(post("/api/processos/rejeicao").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		result.andExpect(status().isOk());
	}

	@Test
    public void naoDeveRegistrarUmProcessoInvalido() throws Exception {
        String processo = "{}";
        ResultActions result = mockMvc.perform(post("/api/processos/autuacao").contentType(APPLICATION_JSON).content(processo));
        
        result.andExpect(status().isBadRequest());
    }
	
	@Test
    public void naoDeveRejeitarUmProcessoInvalido() throws Exception {
        String processo = "{}";
        ResultActions result = mockMvc.perform(post("/api/processos/rejeicao").contentType(APPLICATION_JSON).content(processo));
        
        result.andExpect(status().isBadRequest());
    }
    
}
