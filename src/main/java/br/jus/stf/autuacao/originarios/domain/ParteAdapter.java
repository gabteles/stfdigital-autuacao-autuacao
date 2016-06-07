package br.jus.stf.autuacao.originarios.domain;

import java.util.List;

import br.jus.stf.autuacao.originarios.interfaces.dto.ParteDto;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

public interface ParteAdapter {
	
	/**
	 * Consulta as partes do contexto do peticionamento.
	 * 
	 * @param protocoloId
	 * @return dto da Parte
	 */
	List<ParteDto> consultar(ProtocoloId protocoloId);

}
