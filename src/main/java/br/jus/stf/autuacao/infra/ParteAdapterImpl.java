package br.jus.stf.autuacao.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ParteAdapter;
import br.jus.stf.autuacao.infra.client.ParteRestClient;
import br.jus.stf.autuacao.interfaces.dto.ParteDto;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

@Component
public class ParteAdapterImpl implements ParteAdapter {
	
	@Autowired
	private ParteRestClient parteRestClient;

	@Override
	public List<ParteDto> consultar(ProtocoloId protocoloId) {
		return parteRestClient.envolvidos(protocoloId.toLong());
	}
}
