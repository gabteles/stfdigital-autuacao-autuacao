package br.jus.stf.autuacao.originarios.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.originarios.domain.model.Parte;
import br.jus.stf.autuacao.originarios.infra.ParteDto;

@Component
public class ParteDtoAssembler {
	public ParteDto toDto(Parte parte){
		return new ParteDto(parte.apresentacao(), parte.polo().descricao());
	}

}
