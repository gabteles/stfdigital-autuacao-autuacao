package br.jus.stf.autuacao.originarios.interfaces.dto;

import br.jus.stf.autuacao.originarios.infra.RemessaDto;

/**
 * @author viniciusk
 *
 */
public class ProcessoDto {
	
	private Long processoId;
	
	private RemessaDto remessa;
	
	public ProcessoDto(Long processoId, RemessaDto remessaDto) {
		this.processoId = processoId;

		this.remessa = remessaDto;
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

	
 
}
