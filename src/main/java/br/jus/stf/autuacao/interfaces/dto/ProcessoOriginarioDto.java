package br.jus.stf.autuacao.interfaces.dto;

import br.jus.stf.autuacao.infra.RemessaDto;

/**
 * @author Lucas Rodrigues
 *
 */
public class ProcessoOriginarioDto extends ProcessoDto {
	
	private String motivoRejeicao;
	
	public ProcessoOriginarioDto(Long processoId, RemessaDto remessaDto) {
		super(processoId, remessaDto);
	}
	
	public String getMotivoRejeicao() {
		return motivoRejeicao;
	}
	
	public void setMotivoRejeicao(String motivo) {
		this.motivoRejeicao = motivo;
	}
}
