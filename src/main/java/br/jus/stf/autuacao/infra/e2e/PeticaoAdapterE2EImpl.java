package br.jus.stf.autuacao.infra.e2e;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.PeticaoAdapter;
import br.jus.stf.autuacao.interfaces.dto.ParteDto;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rafael Alencar
 *
 */
@Component
@Primary
@Profile("e2e")
public class PeticaoAdapterE2EImpl implements PeticaoAdapter {
	

	@Override
	public PeticaoDto consultar(ProtocoloId protocoloId) {
		List<ParteDto> partes = new ArrayList<>();
		partes.add(new ParteDto("Thomas de Godoi", Polo.ATIVO.toString()));
		partes.add(new ParteDto("Maria da Silva", Polo.PASSIVO.toString()));
		return new PeticaoDto(protocoloId.toLong(), "RE", partes);
	}

}
