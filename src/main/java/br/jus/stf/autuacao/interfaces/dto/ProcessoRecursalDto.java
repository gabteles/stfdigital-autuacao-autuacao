package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;

import br.jus.stf.autuacao.infra.RemessaDto;

/**
 * @author Lucas Rodrigues
 *
 */
public class ProcessoRecursalDto extends ProcessoDto {
	
	private List<AssuntoDto> assuntos;
	private List<TeseDto> teses;
	
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

	public List<TeseDto> getTeses() {
		return teses;
	}

	public void setTeses(List<TeseDto> teses) {
		this.teses = teses;
	}
	
}
