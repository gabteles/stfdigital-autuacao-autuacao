package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.infra.RemessaDto;

/**
 * Cria objetos ProcessoDto
 * 
 * @author anderson.araujo
 * @since 06/06/2016
 *
 */
@Component
public class ProcessoDtoAssembler {

	public ProcessoDto toDto(Long processoId, RemessaDto remessaDto, List<ParteDto> partes, List<MotivoInaptidaoDto> motivosInaptidao ) {
		Validate.notNull(processoId);
		Validate.notNull(remessaDto);
		Validate.notNull(partes);
		ProcessoDto processoDto = new ProcessoDto(processoId, remessaDto, partes, motivosInaptidao);
		return processoDto;
	}
	
}
