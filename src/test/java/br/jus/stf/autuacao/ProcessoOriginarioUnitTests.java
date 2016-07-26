package br.jus.stf.autuacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.jus.stf.autuacao.domain.model.Autuador;
import br.jus.stf.autuacao.domain.model.Parte;
import br.jus.stf.autuacao.domain.model.ProcessoOriginario;
import br.jus.stf.autuacao.domain.model.Status;
import br.jus.stf.autuacao.domain.model.suportejudicial.Classe;
import br.jus.stf.autuacao.domain.model.suportejudicial.Preferencia;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.preferencia.PreferenciaId;
import br.jus.stf.core.shared.processo.MeioTramitacao;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.processo.ProcessoId;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * Testes unitários para processo originário.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 19.07.2016
 */
public class ProcessoOriginarioUnitTests {
	
	@Test
	public void criaProcessoOriginarioValido() {
		ProcessoOriginario processo = processoOriginarioValido();
		
		assertNotNull("Processo não pode ser nulo.", processo);
		assertEquals("Tipo do processo deve ser ORIGINARIO.", TipoProcesso.ORIGINARIO, processo.tipo());
		assertEquals("ProtocoloId deve ser igual a 1.", new ProtocoloId(1L), processo.protocoloId().get());
		assertEquals("Identificador deve ser 1.", new ProcessoId(1L), processo.identity());
		assertEquals("Classe deve ser ADI.", new ClasseId("ADI"), processo.classe().get().identity());
		assertEquals("Sigilo deve ser PUBLICO.", Sigilo.PUBLICO, processo.sigilo());
		assertEquals("Meio de tramitação deve ser ELETRONICO.", MeioTramitacao.ELETRONICO, processo.meioTramitacao());
		assertTrue("Deve possuir uma preferência.", processo.preferencias().size() == 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveCriarProcessoOriginarioComClasseDeTipoProcessoIncompativel() {
		Classe classe = new Classe(new ClasseId("RE"), "Recurso Extraordinário", TipoProcesso.RECURSAL, null);
		
		new ProcessoOriginario(new ProcessoId(1L), new ProtocoloId(1L), classe, MeioTramitacao.ELETRONICO,
				Sigilo.PUBLICO, Status.AUTUACAO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveCriarProcessoOriginarioComPreferenciaIncompativel() {
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO,
				null);
		Preferencia medidaLiminar = new Preferencia(new PreferenciaId(8L), "Medida Liminar");
		Set<Preferencia> preferenciasProcesso = new HashSet<>(1);

		preferenciasProcesso.add(medidaLiminar);
		
		new ProcessoOriginario(new ProcessoId(1L), new ProtocoloId(1L), classe, preferenciasProcesso,
				MeioTramitacao.ELETRONICO, Sigilo.PUBLICO, Status.AUTUACAO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveAutuarProcessoOriginarioSemClasse() {
		ProcessoOriginario processo = processoOriginarioValido();
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(null, 1L, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveAutuarProcessoOriginarioSemNumero() {
		ProcessoOriginario processo = processoOriginarioValido();
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO, null);
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(classe, null, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAutuarProcessoOriginarioComNumeroMenorQue0() {
		ProcessoOriginario processo = processoOriginarioValido();
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO, null);
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(classe, 0L, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveAutuarProcessoOriginarioComPartesNulo() {
		ProcessoOriginario processo = processoOriginarioValido();
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO, null);
		
		processo.autuar(classe, 1L, null, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAutuarProcessoOriginarioComPartesVazio() {
		ProcessoOriginario processo = processoOriginarioValido();
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO, null);
		Set<Parte> partes = new HashSet<>(0);
		
		processo.autuar(classe, 1L, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAutuarProcessoOriginarioSemParteNoPoloAtivo() {
		ProcessoOriginario processo = processoOriginarioValido();
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO, null);
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.PASSIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(classe, 1L, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveAutuarProcessoOriginarioSemAutuador() {
		ProcessoOriginario processo = processoOriginarioValido();
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO, null);
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(classe, 1L, partes, null, Status.AUTUADO);
	}
	
	@Test
	public void autuaProcessoOriginario() {
		ProcessoOriginario processo = processoOriginarioValido();
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO, null);
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(classe, 1L, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
		
		assertEquals("Classes devem ser iguais a ADI.", classe, processo.classe().get());
		assertEquals("Partes devem ser iguais.", partes, processo.partes());
	}
	
	@Test
	public void rejeitaProcessoOriginario() {
		ProcessoOriginario processo = processoOriginarioValido();
		String motivo = "Duplicação de solicitação.";
		
		processo.rejeitar(motivo, Status.REJEITADO);
		
		assertEquals("Motivos de rejeição devem ser iguais.", motivo, processo.motivoRejeicao());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveRejeitarProcessoOriginarioSemMotivo() {
		ProcessoOriginario processo = processoOriginarioValido();
		
		processo.rejeitar(null, Status.REJEITADO);
	}
	
	private ProcessoOriginario processoOriginarioValido() {
		Preferencia medidaLiminar = new Preferencia(new PreferenciaId(8L), "Medida Liminar");
		Preferencia reuPreso = new Preferencia(new PreferenciaId(12L), "Réu Preso");
		Set<Preferencia> preferenciasClasse = new HashSet<>(2);
		
		preferenciasClasse.add(medidaLiminar);
		preferenciasClasse.add(reuPreso);
		
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO,
				preferenciasClasse);
		Set<Preferencia> preferenciasProcesso = new HashSet<>(1);

		preferenciasProcesso.add(medidaLiminar);
		
		return new ProcessoOriginario(new ProcessoId(1L), new ProtocoloId(1L), classe, preferenciasProcesso,
				MeioTramitacao.ELETRONICO, Sigilo.PUBLICO, Status.AUTUACAO);
	}

}
