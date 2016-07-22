package br.jus.stf.autuacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.jus.stf.autuacao.domain.ProcessoFactory;
import br.jus.stf.autuacao.domain.model.Origem;
import br.jus.stf.autuacao.domain.model.Parte;
import br.jus.stf.autuacao.domain.model.ProcessoOriginario;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.Status;
import br.jus.stf.autuacao.domain.model.classe.Classe;
import br.jus.stf.autuacao.domain.model.controletese.Assunto;
import br.jus.stf.autuacao.domain.model.identidade.TribunalJuizo;
import br.jus.stf.autuacao.domain.model.preferencia.Preferencia;
import br.jus.stf.autuacao.domain.model.procedenciageografica.Pais;
import br.jus.stf.autuacao.domain.model.procedenciageografica.UnidadeFederacao;
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
 * Testes unitários para a fábrica de processos.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 21.07.2016
 */
public class ProcessoFactoryUnitTests {
	
	@Test
	public void criaProcessoRecursalValido() {
		Classe classe = new Classe(new ClasseId("RE"), "Recurso Extraordinário", TipoProcesso.RECURSAL, null);
		ProcessoRecursal processo = (ProcessoRecursal) new ProcessoFactory().novoProcesso(new ProcessoId(1L),
				new ProtocoloId(1L), classe, 1L, TipoProcesso.RECURSAL, MeioTramitacao.ELETRONICO, Sigilo.PUBLICO,
				Status.AUTUACAO);
		
		assertNotNull("Processo não pode ser nulo.", processo);
		assertEquals("Tipo do processo deve ser RECURSAL.", TipoProcesso.RECURSAL, processo.tipo());
		assertEquals("ProtocoloId deve ser igual a 1.", new ProtocoloId(1L), processo.protocoloId().get());
		assertEquals("Identificador deve ser 1.", new ProcessoId(1L), processo.identity());
		assertEquals("Classe deve ser RE.", new ClasseId("RE"), processo.classe().get().identity());
		assertEquals("Número deve ser igual a 1.", new Long(1), processo.numero().get());
		assertEquals("Sigilo deve ser PUBLICO.", Sigilo.PUBLICO, processo.sigilo());
		assertEquals("Meio de tramitação deve ser ELETRONICO.", MeioTramitacao.ELETRONICO, processo.meioTramitacao());
		assertFalse("O processo não deve ser Criminal/Eleitoral.", processo.isCriminalEleitoral());
	}
	
	@Test
	public void criaProcessoOriginarioValido() {
		Classe classe = new Classe(new ClasseId("ADI"), "Ação Direta de Inconstitucionalidade", TipoProcesso.ORIGINARIO, null);
		ProcessoOriginario processo = (ProcessoOriginario) new ProcessoFactory().novoProcesso(new ProcessoId(1L),
				new ProtocoloId(1L), classe, null, TipoProcesso.ORIGINARIO, MeioTramitacao.ELETRONICO, Sigilo.PUBLICO,
				Status.AUTUACAO);
		
		assertNotNull("Processo não pode ser nulo.", processo);
		assertEquals("Tipo do processo deve ser ORIGINARIO.", TipoProcesso.ORIGINARIO, processo.tipo());
		assertEquals("ProtocoloId deve ser igual a 1.", new ProtocoloId(1L), processo.protocoloId().get());
		assertEquals("Identificador deve ser 1.", new ProcessoId(1L), processo.identity());
		assertEquals("Classe deve ser ADI.", new ClasseId("ADI"), processo.classe().get().identity());
		assertFalse("Número deve ser nulo.", processo.numero().isPresent());
		assertEquals("Sigilo deve ser PUBLICO.", Sigilo.PUBLICO, processo.sigilo());
		assertEquals("Meio de tramitação deve ser ELETRONICO.", MeioTramitacao.ELETRONICO, processo.meioTramitacao());
		assertFalse("O processo não deve ser Criminal/Eleitoral.", processo.isCriminalEleitoral());
	}
	
	@Test
	public void criaProcessoRecursalPorEnvioValido() {
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
		UnidadeFederacao pernambuco = new UnidadeFederacao(1L, "Pernambuco", "PE", new Pais(1L, "Brasil"));
		Origem origem = new Origem(pernambuco,
				new TribunalJuizo(1L, "TJPE", new HashSet<UnidadeFederacao>(Arrays.asList(pernambuco))), 2L, true);
		
		origens.add(origem);
		
		ProcessoRecursal processo = new ProcessoFactory().novoEnvio(new ProcessoId(1L), classe, 1L,
				preferenciasProcesso, 1, Sigilo.SEGREDO_JUSTICA, origens, assuntos, partes, Status.AUTUACAO);
		
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
	
}
