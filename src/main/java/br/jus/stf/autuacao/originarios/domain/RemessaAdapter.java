package br.jus.stf.autuacao.originarios.domain;

import br.jus.stf.autuacao.originarios.infra.RemessaDto;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author viniciusk
 *
 */
public interface RemessaAdapter {
	
	/**
	 * Consulta a remessa no contexto de recebimento.
	 * 
	 * @param protocoloId
	 * @return dto da Remessa
	 */
	RemessaDto consultar(ProtocoloId protocoloId);

}
