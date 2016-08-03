package br.jus.stf.autuacao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.jus.stf.autuacao.infra.NumeroProcessoRestAdapter;
import br.jus.stf.autuacao.infra.client.NumeroProcessoRestClient;
import br.jus.stf.core.shared.processo.Identificacao;

/**
 * Valida a API de número de processo.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 02.08.2016
 */
public class NumeroProcessoRestAdapterUnitTests {
	
	@Mock
	private NumeroProcessoRestClient numeroProcessoRestClient;
	
	@InjectMocks
	private NumeroProcessoRestAdapter numeroProcessoRestAdapter;
	
	@Before
	public void configuracao() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(numeroProcessoRestClient.identificador("ADO")).thenReturn(1L);
	}
	
	@Test
	public void novoNumeroProcesso() {
		Identificacao identificacao = numeroProcessoRestAdapter.novoNumeroProcesso("ADO");
		
		Assert.assertNotNull("Identificação não pode ser nula.", identificacao);
		Assert.assertEquals("Número deve ser igual a 1.", new Long(1L), identificacao.numero());
		
		Mockito.verify(numeroProcessoRestClient, Mockito.times(1)).identificador("ADO");
	}
    
}