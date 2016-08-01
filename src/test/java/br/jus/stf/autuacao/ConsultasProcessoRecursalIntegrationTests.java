package br.jus.stf.autuacao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.autuacao.domain.RemessaAdapter;
import br.jus.stf.autuacao.infra.RemessaDto;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * Valida a API de consulta de processos recursais
 * 
 * @author Rafael Alencar
 * @since 01.08.2016
 *
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("autuador")
public class ConsultasProcessoRecursalIntegrationTests extends IntegrationTestsSupport {
	
	@MockBean
	private RemessaAdapter remessaAdapter;
	
	@Before
	public void configuracao() {
		RemessaDto remessa = new RemessaDto();
		
		willReturn(remessa).given(remessaAdapter).consultar(new ProtocoloId(9004L));
	}
	
	@Test
	public void listarMotivosInaptidao() throws Exception{
		mockMvc.perform(get("/api/processos/recursal/motivos-inaptidao")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(8)));
	}
	
	@Test
	public void listarTiposTese() throws Exception{
		mockMvc.perform(get("/api/processos/recursal/tipos-tese")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	public void listarTeses() throws Exception{
		mockMvc.perform(get("/api/processos/recursal/teses").param("tipo", "PRE_TEMA").param("numero", "574")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void listarAssuntosNumero() throws Exception{
		mockMvc.perform(get("/api/processos/recursal/assuntos").param("termo", "2567")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void listarAssuntosTexto() throws Exception{
		mockMvc.perform(get("/api/processos/recursal/assuntos").param("termo", "Coisas")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
    public void consultarProcesso() throws Exception {
		loadDataTests("consultarProcessoRecursal-limpar.sql", "consultarProcessoRecursal.sql");
		
		ResultActions result = mockMvc.perform(get("/api/processos/recursal/9014"));
        
		result.andExpect(status().isOk()).andExpect(jsonPath("$.classe.id", equalTo("RE")))
				.andExpect(jsonPath("$.numero", equalTo(100)));
    }
	
	@Test
    public void analisePressupostosFormais() throws Exception {
		loadDataTests("consultarProcessoRecursal-limpar.sql", "consultarProcessoRecursal.sql");
		
		ResultActions result = mockMvc.perform(get("/api/processos/recursal/9014/analise-pressupostos-formais"));
        
		result.andExpect(status().isOk()).andExpect(jsonPath("$.processoApto", equalTo(true)))
				.andExpect(jsonPath("$.assuntos[0].codigo", equalTo("4355")));
    }
	
	@Test
    public void analiseRepercussaoGeral() throws Exception {
		loadDataTests("consultarProcessoRecursal-limpar.sql", "consultarProcessoRecursal.sql");
		
		ResultActions result = mockMvc.perform(get("/api/processos/recursal/9014/analise-repercussao-geral"));
        
		result.andExpect(status().isOk()).andExpect(jsonPath("$.temTeseRepercussaoGeral", equalTo(false)))
				.andExpect(jsonPath("$.teses[0].codigo", equalTo(1001)));
    }
	
}