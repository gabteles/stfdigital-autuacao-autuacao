package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;

/**
 * @author Lucas Rodrigues
 *
 */
public class ProcessoRecursalDto extends ProcessoDto {
	
	private List<AssuntoDto> assuntos;
	private List<TeseDto> teses;
	
	ProcessoRecursalDto(){ } 
	
	public ProcessoRecursalDto(Long processoId, Long protocoloId, String classeSugerida) {
		super(processoId, protocoloId, classeSugerida);
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
