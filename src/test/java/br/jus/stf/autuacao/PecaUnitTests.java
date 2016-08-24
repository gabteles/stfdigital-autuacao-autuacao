package br.jus.stf.autuacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.jus.stf.autuacao.domain.model.Peca;
import br.jus.stf.autuacao.domain.model.documento.TipoPeca;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * Testes unitários para peca.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.08.2016
 */
public class PecaUnitTests {
	
	@Test
	public void criaPecaValida() {
		TipoPeca tipoPeca = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		DocumentoId documento = new DocumentoId(1L);
		Peca peca = new Peca(tipoPeca, documento);
		
		assertNotNull("Peça não pode ser nula.", peca);
		assertEquals("Tipo de peça deve ser igual a 101.", tipoPeca, peca.tipo());
		assertEquals("Descrição deve ser igual a Petição inicial.", tipoPeca.nome(), peca.descricao());
		assertEquals("Documento deve ser igual a 1.", documento, peca.documento());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarPecaComTipoNulo() {
		new Peca(null, new DocumentoId(1L));
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarPecaComDocumentoNulo() {
		new Peca(new TipoPeca(new TipoDocumentoId(101L), "Petição inicial"), null);
	}

}