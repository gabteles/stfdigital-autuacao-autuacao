package br.jus.stf.autuacao.interfaces.dto;

/**
 * @author Lucas Rodrigues
 *
 */
public class ProcessoOriginarioDto extends ProcessoDto {
	
	private String motivoRejeicao;
	
	public ProcessoOriginarioDto(Long processoId, Long protocoloId, String classeSugerida) {
		super(processoId, protocoloId, classeSugerida);
	}
	
	public String getMotivoRejeicao() {
		return motivoRejeicao;
	}
	
	public void setMotivoRejeicao(String motivo) {
		this.motivoRejeicao = motivo;
	}
}
