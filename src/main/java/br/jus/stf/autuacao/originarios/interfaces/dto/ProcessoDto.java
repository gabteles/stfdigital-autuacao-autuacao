package br.jus.stf.autuacao.originarios.interfaces.dto;

import java.util.List;

import br.jus.stf.autuacao.originarios.infra.RemessaDto;

/**
 * @author viniciusk
 *
 */
public class ProcessoDto {
	
	private Long processoId;
	
	private RemessaDto remessa;
	
	private List<ParteDto> partes;
	
	private List<MotivoInaptidaoDto> motivosInaptidao;
	
	public ProcessoDto(Long processoId, RemessaDto remessaDto, List<ParteDto> partes) {
		this.processoId = processoId;
		this.remessa = remessaDto;
		this.partes = partes;
	}
	
	public ProcessoDto(Long processoId, RemessaDto remessaDto, List<ParteDto> partes, List<MotivoInaptidaoDto> motivosInaptidao) {
		this.processoId = processoId;
		this.remessa = remessaDto;
		this.partes = partes;
		this.motivosInaptidao = motivosInaptidao;
	}
	
	public Long getProcessoId() {
		return processoId;
	}

	public RemessaDto getRemessa() {
		return remessa;
	}

	public List<ParteDto> getPartes() {
		return partes;
	}
	
	public List<MotivoInaptidaoDto> getMotivoInaptidao() {
		return motivosInaptidao;
	}
}
