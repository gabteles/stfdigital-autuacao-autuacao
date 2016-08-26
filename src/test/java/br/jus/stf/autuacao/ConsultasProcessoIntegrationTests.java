package br.jus.stf.autuacao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.autuacao.domain.PeticaoAdapter;
import br.jus.stf.autuacao.domain.RemessaAdapter;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.autuacao.interfaces.dto.RemessaDto;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * Valida a API de consulta de processos
 * 
 * @author Rafael Alencar
 * @since 15.07.2016
 *
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false", "spring.cloud.config.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("autuador")
public class ConsultasProcessoIntegrationTests extends IntegrationTestsSupport {
	
	@MockBean
	private RemessaAdapter remessaAdapter;
	
	@MockBean
	private PeticaoAdapter peticaoAdapter;
	
	@Before
	public void configuracao() {
		RemessaDto remessa = new RemessaDto("ADI", 1, 1, "BALCAO", "", 9004L);
		PeticaoDto peticao = new PeticaoDto(9004L, "ADI", Collections.emptyList());
		
		willReturn(remessa).given(remessaAdapter).consultar(new ProtocoloId(9002L));
		willReturn(peticao).given(peticaoAdapter).consultar(new ProtocoloId(9002L));
	}
	
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
		loadDataTests("consultarProcesso-limpar.sql", "consultarProcesso.sql");
		
		ResultActions result = mockMvc.perform(get("/api/processos/9004"));
        
		result.andExpect(status().isOk()).andExpect(jsonPath("$.classe.id", equalTo("ADI")))
				.andExpect(jsonPath("$.numero", equalTo(100)));
    }
	
	@Test
    public void consultarPartes() throws Exception {
		loadDataTests("consultarProcesso-limpar.sql", "consultarProcesso.sql");
		
		ResultActions result = mockMvc.perform(get("/api/processos/9004/partes"));
        
		result.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].apresentacao", equalTo("JOSE SILVA")))
				.andExpect(jsonPath("$[1].apresentacao", equalTo("MARIA PAULA")));
    }
	
}