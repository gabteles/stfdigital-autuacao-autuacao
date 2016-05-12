package br.jus.stf.autuacao.originarios;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.autuacao.originarios.application.AutuacaoDeOriginariosApplicationService;
import br.jus.stf.autuacao.originarios.application.commands.IniciarAutuacaoCommand;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

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
public class ProcessoIntegrationTests extends IntegrationTestsSupport {
	
	@Autowired
	private AutuacaoDeOriginariosApplicationService autuacaoOriginarioAppService;
	
	@Test
	public void autuarProcesso() throws Exception {
		IniciarAutuacaoCommand command = new IniciarAutuacaoCommand(new ProtocoloId(1L), "ADI");
		String processoId = autuacaoOriginarioAppService.handle(command).toString();
		
		String processo = "{\"processoId\":@processoId,\"classeId\":\"ADO\",\"poloAtivo\":[{\"apresentacao\":\"Maria\",\"pessoa\":1},{\"apresentacao\":\"João\"}],\"poloPassivo\":[{\"apresentacao\":\"Antônia\",\"pessoa\":3}]}";
		ResultActions result = mockMvc.perform(post("/api/processos/autuacao").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		result.andExpect(status().isOk());
	}

	@Test
	public void rejeitarProcesso() throws Exception {
		IniciarAutuacaoCommand command = new IniciarAutuacaoCommand(new ProtocoloId(1L), "ADI");
		String processoId = autuacaoOriginarioAppService.handle(command).toString();
		
		String processo = "{\"processoId\":@processoId,\"motivo\":\"Dados inconsistentes.\"}";
		ResultActions result = mockMvc.perform(post("/api/processos/rejeicao").contentType(APPLICATION_JSON).content(processo.replace("@processoId", processoId)));
		
		result.andExpect(status().isOk());
	}
	
    @Test
    public void naoDeveRegistrarUmaPeticaoInvalida() throws Exception {
        String processo = "{}";
        
        ResultActions result = mockMvc.perform(post("/api/processos/autuacao").contentType(APPLICATION_JSON).content(processo));
        
        result.andExpect(status().isBadRequest());
    }
    
}
