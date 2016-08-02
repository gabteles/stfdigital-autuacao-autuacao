package br.jus.stf.autuacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.jus.stf.autuacao.domain.model.Autuador;
import br.jus.stf.autuacao.domain.model.Origem;
import br.jus.stf.autuacao.domain.model.Parte;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.Status;
import br.jus.stf.autuacao.domain.model.identidade.TribunalJuizo;
import br.jus.stf.autuacao.domain.model.procedenciageografica.Pais;
import br.jus.stf.autuacao.domain.model.procedenciageografica.UnidadeFederacao;
import br.jus.stf.autuacao.domain.model.suportejudicial.Assunto;
import br.jus.stf.autuacao.domain.model.suportejudicial.Classe;
import br.jus.stf.autuacao.domain.model.suportejudicial.Preferencia;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.controletese.AssuntoId;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.preferencia.PreferenciaId;
import br.jus.stf.core.shared.processo.MeioTramitacao;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.processo.ProcessoId;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * Testes unitários para processo recursal.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 21.07.2016
 */
public class ProcessoRecursalUnitTests {
	
	@Test
	public void criaProcessoRecursalValido() {
		ProcessoRecursal processo = processoRecursalValido();
		
		assertNotNull("Processo não pode ser nulo.", processo);
		assertEquals("Tipo do processo deve ser RECURSAL.", TipoProcesso.RECURSAL, processo.tipo());
		assertEquals("ProtocoloId deve ser igual a 1.", new ProtocoloId(1L), processo.protocoloId().get());
		assertEquals("Identificador deve ser 1.", new ProcessoId(1L), processo.identity());
		assertEquals("Classe deve ser RE.", new ClasseId("RE"), processo.classe().get().identity());
		assertEquals("Número deve ser igual a 1.", new Long(1), processo.numero().get());
		assertEquals("Sigilo deve ser PUBLICO.", Sigilo.PUBLICO, processo.sigilo());
		assertEquals("Meio de tramitação deve ser ELETRONICO.", MeioTramitacao.ELETRONICO, processo.meioTramitacao());
		assertTrue("Deve possuir uma preferência.", processo.preferencias().size() == 1);
		assertFalse("O processo não deve ser Criminal/Eleitoral.", processo.isCriminalEleitoral());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveCriarProcessoRecursalComClasseDeTipoProcessoIncompativel() {
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO, null);
		
		new ProcessoRecursal(new ProcessoId(1L), new ProtocoloId(1L), classe, 1L, MeioTramitacao.ELETRONICO,
				Sigilo.PUBLICO, Status.AUTUACAO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveCriarProcessoRecursalComPreferenciaIncompativel() {
		Classe classe = new Classe(new ClasseId("RE"), "Recurso Extraordinário", TipoProcesso.RECURSAL,
				null);
		Preferencia medidaLiminar = new Preferencia(new PreferenciaId(8L), "Medida Liminar");
		Set<Preferencia> preferenciasProcesso = new HashSet<>(1);

		preferenciasProcesso.add(medidaLiminar);
		
		new ProcessoRecursal(new ProcessoId(1L), new ProtocoloId(1L), classe, preferenciasProcesso, 1L,
				MeioTramitacao.ELETRONICO, Sigilo.PUBLICO, Status.AUTUACAO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveAutuarProcessoRecursalComAssuntoNulo() {
		ProcessoRecursal processo = processoRecursalValido();
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(null, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAutuarProcessoRecursalComAssuntoVazio() {
		ProcessoRecursal processo = processoRecursalValido();
		Set<Assunto> assuntos = new HashSet<>(0);
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(assuntos, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveAutuarProcessoRecursalComPartesNulo() {
		ProcessoRecursal processo = processoRecursalValido();
		Set<Assunto> assuntos = new HashSet<>(0);
		Assunto assunto = new Assunto(new AssuntoId("001000000"), "ADVOGADO", null);
		
		assuntos.add(assunto);
		
		processo.autuar(assuntos, null, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAutuarProcessoRecursalComPartesVazio() {
		ProcessoRecursal processo = processoRecursalValido();
		Set<Assunto> assuntos = new HashSet<>(0);
		Assunto assunto = new Assunto(new AssuntoId("1"), "", null);
		
		assuntos.add(assunto);
		
		Set<Parte> partes = new HashSet<>(0);
		
		processo.autuar(assuntos, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAutuarProcessoRecursalSemParteNoPoloAtivo() {
		ProcessoRecursal processo = processoRecursalValido();
		Set<Assunto> assuntos = new HashSet<>(0);
		Assunto assunto = new Assunto(new AssuntoId("1"), "", null);
		
		assuntos.add(assunto);
		
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.PASSIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(assuntos, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveAutuarProcessoRecursalSemAutuador() {
		ProcessoRecursal processo = processoRecursalValido();
		Set<Assunto> assuntos = new HashSet<>(0);
		Assunto assunto = new Assunto(new AssuntoId("001000000"), "ADVOGADO", null);
		
		assuntos.add(assunto);
		
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(assuntos, partes, null, Status.AUTUADO);
	}
	
	@Test
	public void autuaProcessoRecursal() {
		ProcessoRecursal processo = processoRecursalValido();
		Set<Assunto> assuntos = new HashSet<>(0);
		Assunto assunto = new Assunto(new AssuntoId("001000000"), "ADVOGADO", null);
		
		assuntos.add(assunto);
		
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		processo.autuar(assuntos, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
		
		assertEquals("Assuntos devem ser iguais.", assuntos, processo.assuntos());
		assertEquals("Partes devem ser iguais.", partes, processo.partes());
	}
	
	@Test
	public void autuaProcessoRecursalCriminalEleitoral() {
		ProcessoRecursal processo = processoRecursalValido();
		Set<Assunto> assuntos = new HashSet<>(0);
		Assunto assunto = new Assunto(new AssuntoId("001000000"), "ADVOGADO", null);
		
		assuntos.add(assunto);
		
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		processo.atribuirPreferencias(new HashSet<Preferencia>(Arrays.asList(new Preferencia(new PreferenciaId(2L), "Criminal"))));
		
		processo.autuar(assuntos, partes, new Autuador("autuador", new PessoaId(1L)), Status.AUTUADO);
		
		assertTrue("Processo deve ser Criminal/Eleitoral.", processo.isCriminalEleitoral());
		assertEquals("Assuntos devem ser iguais.", assuntos, processo.assuntos());
		assertEquals("Partes devem ser iguais.", partes, processo.partes());
	}
	
	@Test
	public void enviaProcessoRecursal() {
		Preferencia criminal = new Preferencia(new PreferenciaId(2L), "Criminal");
		Set<Preferencia> preferenciasClasse = new HashSet<>(2);
		
		preferenciasClasse.add(criminal);
		
		Classe classe = new Classe(new ClasseId("RE"), "Recurso Extraordinário", TipoProcesso.RECURSAL,
				preferenciasClasse);
		Set<Preferencia> preferenciasProcesso = new HashSet<>(1);

		preferenciasProcesso.add(criminal);
		
		Set<Assunto> assuntos = new HashSet<>(0);
		Assunto assunto = new Assunto(new AssuntoId("001000000"), "ADVOGADO", null);
		
		assuntos.add(assunto);
		
		Set<Parte> partes = new HashSet<>(0);
		Parte parte = new Parte("João Silva", Polo.ATIVO, new PessoaId(2L));
		
		partes.add(parte);
		
		Set<Origem> origens = new HashSet<>(0);
		UnidadeFederacao pernambuco = new UnidadeFederacao(16L, "Pernambuco", "PE", new Pais(1L, "Brasil"));
		Origem origem = new Origem(pernambuco,
				new TribunalJuizo(5187L, "TRIBUNAL DE JUSTIÇA ESTADUAL", new HashSet<UnidadeFederacao>(Arrays.asList(pernambuco))), 2L, true);
		
		origens.add(origem);
		
		ProcessoRecursal processo = new ProcessoRecursal(new ProcessoId(1L), null, classe, 1L, preferenciasProcesso,
				MeioTramitacao.ELETRONICO, 1, Sigilo.SEGREDO_JUSTICA, Status.AUTUACAO);
		
		processo.atribuirOrigens(origens);
		processo.atribuirAssuntos(assuntos);
		processo.atribuirPartes(partes);
		
		assertNotNull("Processo não pode ser nulo.", processo);
		assertEquals("Tipo do processo deve ser RECURSAL.", TipoProcesso.RECURSAL, processo.tipo());
		assertFalse("ProtocoloId deve ser nulo.", processo.protocoloId().isPresent());
		assertEquals("Identificador deve ser 1.", new ProcessoId(1L), processo.identity());
		assertEquals("Classe deve ser RE.", new ClasseId("RE"), processo.classe().get().identity());
		assertEquals("Número deve ser igual a 1.", new Long(1), processo.numero().get());
		assertEquals("Sigilo deve ser SEGREDO_JUSTICA.", Sigilo.SEGREDO_JUSTICA, processo.sigilo());
		assertEquals("Meio de tramitação deve ser ELETRONICO.", MeioTramitacao.ELETRONICO, processo.meioTramitacao());
		assertTrue("O processo deve ser Criminal/Eleitoral.", processo.isCriminalEleitoral());
		assertEquals("Origens devem ser iguais.", origens, processo.origens());
		assertEquals("Assuntos devem ser iguais.", assuntos, processo.assuntos());
		assertEquals("Partes devem ser iguais.", partes, processo.partes());
		assertEquals("Preferências devem ser iguais.", preferenciasProcesso, processo.preferencias());
	}
	
	private ProcessoRecursal processoRecursalValido() {
		Preferencia medidaLiminar = new Preferencia(new PreferenciaId(8L), "Medida Liminar");
		Preferencia reuPreso = new Preferencia(new PreferenciaId(12L), "Réu Preso");
		Preferencia criminal = new Preferencia(new PreferenciaId(2L), "Criminal");
		Set<Preferencia> preferenciasClasse = new HashSet<>(2);
		
		preferenciasClasse.add(medidaLiminar);
		preferenciasClasse.add(reuPreso);
		preferenciasClasse.add(criminal);
		
		Classe classe = new Classe(new ClasseId("RE"), "Recurso Extraordinário", TipoProcesso.RECURSAL,
				preferenciasClasse);
		Set<Preferencia> preferenciasProcesso = new HashSet<>(1);

		preferenciasProcesso.add(medidaLiminar);
		
		return new ProcessoRecursal(new ProcessoId(1L), new ProtocoloId(1L), classe, preferenciasProcesso, 1L,
				MeioTramitacao.ELETRONICO, Sigilo.PUBLICO, Status.AUTUACAO);
	}

}
