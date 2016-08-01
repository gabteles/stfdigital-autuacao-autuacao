package br.jus.stf.autuacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.jus.stf.autuacao.domain.model.MotivoInaptidao;

/**
 * Testes unitários para motivo de inaptidão.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 01.08.2016
 */
public class MotivoInaptidaoUnitTests {
	
	@Test
	public void criaMotivoInaptidaoValido() {
		MotivoInaptidao motivoInaptidao = new MotivoInaptidao(9L, "Outro");
		
		assertNotNull("Motivo de inaptidão não pode ser nulo.", motivoInaptidao);
		assertEquals("Identificador deve ser igual a 1.", new Long(9), motivoInaptidao.identity());
		assertEquals("Descrição deve ser igual a .", "Outro", motivoInaptidao.descricao());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarMotivoComCodigoNulo() {
		new MotivoInaptidao(null, "Outro");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveCriarMotivoComDescricaoBranco() {
		new MotivoInaptidao(9L, "");
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarMotivoComDescricaoNulo() {
		new MotivoInaptidao(9L, null);
	}

}