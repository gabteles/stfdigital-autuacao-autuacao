package br.jus.stf.autuacao.infra.e2e;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.RemessaAdapter;
import br.jus.stf.autuacao.interfaces.dto.RemessaDto;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author viniciusk
 *
 */
@Component
@Primary
@Profile("e2e")
public class RemessaAdapterE2EImpl implements RemessaAdapter {

	/* (non-Javadoc)
	 * @see br.jus.stf.autuacao.originarios.domain.RemessaAdapter#consulta(br.jus.stf.core.shared.protocolo.ProtocoloId)
	 */
	@Override
	public RemessaDto consultar(ProtocoloId protocoloId) {
		return new RemessaDto("RE", 1, 2, "MALOTE", "", protocoloId.toLong());
	}

}
