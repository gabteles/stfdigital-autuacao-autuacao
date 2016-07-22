package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.RemessaAdapter;
import br.jus.stf.autuacao.infra.client.RemessaRestClient;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author viniciusk
 *
 */
@Component
public class RemessaAdapterImpl implements RemessaAdapter {
	
	@Autowired
	private RemessaRestClient remessaRestClient;

	/* (non-Javadoc)
	 * @see br.jus.stf.autuacao.originarios.domain.RemessaAdapter#consulta(br.jus.stf.core.shared.protocolo.ProtocoloId)
	 */
	@Override
	public RemessaDto consultar(ProtocoloId protocoloId) {
		return remessaRestClient.remessa(protocoloId.toLong());
	}

}
