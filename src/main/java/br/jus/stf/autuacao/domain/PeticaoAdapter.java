package br.jus.stf.autuacao.domain;

import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rafael Alencar
 *
 */
@FunctionalInterface
public interface PeticaoAdapter {
	
	/**
	 * Consulta a petição no contexto de peticionamento.
	 * 
	 * @param protocoloId
	 * @return dto da Petição
	 */
	PeticaoDto consultar(ProtocoloId protocoloId);

}
