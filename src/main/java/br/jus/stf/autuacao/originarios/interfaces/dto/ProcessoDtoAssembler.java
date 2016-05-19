package br.jus.stf.autuacao.originarios.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.originarios.infra.RemessaDto;

@Component
public class ProcessoDtoAssembler {

	public ProcessoDto toDto(Long processoId, RemessaDto remessaDto ) {
		Validate.notNull(remessaDto);
		ProcessoDto processoDto = new ProcessoDto(processoId, remessaDto);
		return processoDto;
	}
	
}
