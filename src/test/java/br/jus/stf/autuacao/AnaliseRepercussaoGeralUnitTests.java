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

import br.jus.stf.autuacao.domain.model.AnaliseRepercussaoGeral;
import br.jus.stf.autuacao.domain.model.suportejudicial.Tese;
import br.jus.stf.autuacao.domain.model.suportejudicial.TipoTese;
import br.jus.stf.core.shared.controletese.TeseId;

/**
 * Testes unitários para análise de repercussão.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.08.2016
 */
public class AnaliseRepercussaoGeralUnitTests {
	
	@Test
	public void criaAnaliseRepercussaoGeralSemRG() {
		Tese tese = new Tese(new TeseId(1037L), "Recurso extraodinário com agravo em que se discute...", TipoTese.PRE_TEMA, 466L);
		String observacao = "Análise realizada.";
		Set<Tese> teses = new HashSet<>(Arrays.asList(tese));
		AnaliseRepercussaoGeral analiseRG = new AnaliseRepercussaoGeral(observacao, teses);
		
		assertNotNull("Análise não pode ser nula.", analiseRG);
		assertEquals("Observação deve ser igual a Análise realizada.", observacao, analiseRG.observacao());
		assertEquals("Lista de teses devem ser iguais.", teses, analiseRG.teses());
		assertFalse("Análise deve ser de tema sem repercussão geral.", analiseRG.temTeseRepercussaoGeral());
	}
	
	@Test
	public void criaAnaliseRepercussaoGeralComRG() {
		Tese tese = new Tese(new TeseId(170L), "Recurso extraordinário em que se discute...", TipoTese.REPERCUSSAO_GERAL, 170L);
		Set<Tese> teses = new HashSet<>(Arrays.asList(tese));
		AnaliseRepercussaoGeral analiseRG = new AnaliseRepercussaoGeral(null, teses);
		
		assertNotNull("Análise não pode ser nula.", analiseRG);
		assertNull("Observação deve ser nula.", analiseRG.observacao());
		assertEquals("Lista de teses devem ser iguais.", teses, analiseRG.teses());
		assertTrue("Análise deve ser de tema com repercussão geral.", analiseRG.temTeseRepercussaoGeral());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarAnaliseRepercussaoGeralComTesesNulo() {
		new AnaliseRepercussaoGeral("Observação.", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveCriarAnaliseRepercussaoGeralComTesesVazio() {
		new AnaliseRepercussaoGeral("Observação.", Collections.emptySet());
	}

}