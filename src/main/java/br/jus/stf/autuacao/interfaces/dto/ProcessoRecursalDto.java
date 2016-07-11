package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;

import br.jus.stf.autuacao.infra.RemessaDto;

/**
 * @author Lucas Rodrigues
 *
 */
public class ProcessoRecursalDto extends ProcessoDto {
	
	private List<AssuntoDto> assuntos;
	
	ProcessoRecursalDto(){ } 
	
	public ProcessoRecursalDto(Long processoId, RemessaDto remessaDto) {
		super(processoId, remessaDto);
	}

	public List<AssuntoDto> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(List<AssuntoDto> assuntos) {
		this.assuntos = assuntos;
	}
}
