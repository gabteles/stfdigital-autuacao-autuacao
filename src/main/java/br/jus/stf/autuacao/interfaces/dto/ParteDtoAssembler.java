package br.jus.stf.autuacao.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.Parte;

@Component
public class ParteDtoAssembler {
	public ParteDto toDto(Parte parte){
		return new ParteDto(parte.apresentacao(), parte.polo().descricao());
	}

}
