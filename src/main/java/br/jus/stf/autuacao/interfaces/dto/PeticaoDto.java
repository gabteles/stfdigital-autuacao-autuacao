package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;

/**
 * @author Rafael Alencar
 *
 */
public class PeticaoDto {
	
	private Long protocolo;
	
	private String classeSugerida;
	
	private List<ParteDto> partes;
	
	PeticaoDto() {
		// Construtor default
	}
	
	/**
	 * @param protocolo
	 * @param classeSugerida
	 * @param partes
	 */
	public PeticaoDto(Long protocolo, String classeSugerida, List<ParteDto> partes) {
		this.protocolo = protocolo;
		this.classeSugerida = classeSugerida;
		this.partes = partes;
	}
	
	public Long getProtocolo() {
		return protocolo;
	}
	
	public void setProtocolo(Long protocolo) {
		this.protocolo = protocolo;
	}

	public String getClasseSugerida() {
		return classeSugerida;
	}
	
	public void setClasseSugerida(String classeSugerida) {
		this.classeSugerida = classeSugerida;
	}

	public List<ParteDto> getPartes() {
		return partes;
	}

	public void setPartes(List<ParteDto> partes) {
		this.partes = partes;
	}

}