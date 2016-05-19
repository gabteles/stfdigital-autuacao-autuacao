package br.jus.stf.autuacao.originarios.domain;

import br.jus.stf.autuacao.originarios.infra.ParteDto;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

public interface ParteAdapter {
	
	/**
	 * Consulta as partes do contexto do peticionamento.
	 * 
	 * @param protocoloId
	 * @return dto da Parte
	 */
	ParteDto consultar(ProtocoloId protocoloId);

}
