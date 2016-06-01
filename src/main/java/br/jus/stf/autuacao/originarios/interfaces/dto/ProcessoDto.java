package br.jus.stf.autuacao.originarios.interfaces.dto;

import br.jus.stf.autuacao.originarios.infra.RemessaDto;

/**
 * @author viniciusk
 *
 */
public class ProcessoDto {
	
	private Long processoId;
	
	private RemessaDto remessa;
	
	private ParteDto partes;
	
	public ProcessoDto(Long processoId, RemessaDto remessaDto, ParteDto partes) {
		this.processoId = processoId;
		this.remessa = remessaDto;
		this.partes = partes;
	}
	
	public Long getProcessoId() {
		return processoId;
	}

	public RemessaDto getRemessa() {
		return remessa;
	}

	public void setRemessa(RemessaDto remessaDto) {
		this.remessa = remessaDto;
	}

	public ParteDto getPartes() {
		return partes;
	}

	public void setPartes(ParteDto partes) {
		this.partes = partes;
	}
	
	

	
 
}
