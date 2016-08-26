package br.jus.stf.autuacao.domain;

import br.jus.stf.autuacao.interfaces.dto.RemessaDto;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author viniciusk
 *
 */
@FunctionalInterface
public interface RemessaAdapter {
	
	/**
	 * Consulta a remessa no contexto de recebimento.
	 * 
	 * @param protocoloId
	 * @return dto da Remessa
	 */
	RemessaDto consultar(ProtocoloId protocoloId);

}
