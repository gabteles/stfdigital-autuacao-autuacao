package br.jus.stf.autuacao.originarios.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.originarios.infra.ParteDto;
import br.jus.stf.autuacao.originarios.infra.RemessaDto;

@Component
public class ProcessoDtoAssembler {

	public ProcessoDto toDto(Long processoId, RemessaDto remessaDto, ParteDto parteDto ) {
		Validate.notNull(remessaDto);
		ProcessoDto processoDto = new ProcessoDto(processoId, remessaDto, parteDto);
		return processoDto;
	}
	
}
