package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.PeticaoAdapter;
import br.jus.stf.autuacao.infra.client.PeticaoRestClient;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rafael Alencar
 *
 */
@Component
public class PeticaoAdapterImpl implements PeticaoAdapter {
	
	@Autowired
	private PeticaoRestClient peticaoRestClient;

	@Override
	public PeticaoDto consultar(ProtocoloId protocoloId) {
		return peticaoRestClient.peticao(protocoloId.toLong());
	}

}
