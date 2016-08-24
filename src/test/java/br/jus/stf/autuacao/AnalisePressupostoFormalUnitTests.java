package br.jus.stf.autuacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.jus.stf.autuacao.domain.model.AnalisePressupostoFormal;
import br.jus.stf.autuacao.domain.model.MotivoInaptidao;

/**
 * Testes unitários para análise de pressuposto formal.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.08.2016
 */
public class AnalisePressupostoFormalUnitTests {
	
	@Test
	public void criaAnalisePressupostoFormalComProcessoApto() {
		String observacao = "Processo apto.";
		AnalisePressupostoFormal analisePF = new AnalisePressupostoFormal(true, observacao, null);
		
		assertNotNull("Análise não pode ser nula.", analisePF);
		assertEquals("Observação deve ser igual a Análise realizada.", observacao, analisePF.observacao());
		assertTrue("Lista de motivos de inaptidão deve ser vazia.", analisePF.motivosInaptidao().isEmpty());
		assertTrue("Processo deve ser analisado como apto.", analisePF.processoApto());
	}
	
	@Test
	public void criaAnalisePressupostoFormalComProcessoInapto() {
		MotivoInaptidao motivo = new MotivoInaptidao(9L, "Outro");
		Set<MotivoInaptidao> motivos = new HashSet<>(Arrays.asList(motivo));
		AnalisePressupostoFormal analisePF = new AnalisePressupostoFormal(false, null, motivos);
		
		assertNotNull("Análise não pode ser nula.", analisePF);
		assertNull("Observação deve ser nula.", analisePF.observacao());
		assertEquals("Lista de motivos de inaptidão devem ser iguais.", motivos, analisePF.motivosInaptidao());
		assertFalse("Processo deve ser analisado como inapto.", analisePF.processoApto());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveCriarAnalisePressupostoFormalInaptaComMotivosNulo() {
		new AnalisePressupostoFormal(false, "Observação.", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveCriarAnalisePressupostoFormalInaptaComMotivosVazio() {
		new AnalisePressupostoFormal(false, "Observação.", Collections.emptySet());
	}

}