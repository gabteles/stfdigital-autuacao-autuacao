package br.jus.stf.autuacao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.autuacao.ApplicationContextInitializer;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * Valida a API de consulta de processos
 * 
 * @author Rafael Alencar
 * @since 15.07.2016
 *
 */
@SpringApplicationConfiguration(ApplicationContextInitializer.class)
@WebIntegrationTest({"server.port:0", "eureka.client.enabled:false"})
public class ConsultasProcessoIntegrationTests extends IntegrationTestsSupport {
	
	@Test
	public void listarClasses() throws Exception{
		mockMvc.perform(get("/api/processos/classes")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(41)));
	}
	
	@Test
	public void listarPreferencias() throws Exception{
		mockMvc.perform(get("/api/processos/preferencias")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(6)));
	}
	
	@Test
    public void consultarProcesso() throws Exception {
		loadDataTests("consultarProcesso.sql");
		
		ResultActions result = mockMvc.perform(get("/api/processos/9004"));
        
		result.andExpect(status().isOk()).andExpect(jsonPath("$.classe.id", equalTo("ADI")))
				.andExpect(jsonPath("$.numero", equalTo(100)));
		
		loadDataTests("consultarProcesso-limpar.sql");
    }
	
	@Test
    public void consultarPartes() throws Exception {
		loadDataTests("consultarProcesso.sql");
		
		ResultActions result = mockMvc.perform(get("/api/processos/9004/partes"));
        
		result.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].apresentacao", equalTo("JOSE SILVA")))
				.andExpect(jsonPath("$[1].apresentacao", equalTo("MARIA PAULA")));
		
		loadDataTests("consultarProcesso-limpar.sql");
    }
	
}