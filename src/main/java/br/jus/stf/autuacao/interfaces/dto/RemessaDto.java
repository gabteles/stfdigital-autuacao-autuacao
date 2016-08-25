package br.jus.stf.autuacao.interfaces.dto;

/**
 * @author viniciusk
 *
 */
public class RemessaDto {
	
	private String classeSugerida;
	
	private int qtdVolumes;
	
	private int qtdApensos;
	
	private String formaRecebimento;
	
	private String numeroSedex;
	
	private Long protocolo;
	
	RemessaDto() {
		// Contrutor default
	}
	
	/**
	 * @param classeSugerida
	 * @param qtdVolumes
	 * @param qtdApensos
	 * @param formaRecebimento
	 * @param numeroSedex
	 * @param protocolo
	 */
	public RemessaDto(String classeSugerida, int qtdVolumes, int qtdApensos, String formaRecebimento, String numeroSedex, Long protocolo) {
		this.classeSugerida = classeSugerida;
		this.qtdVolumes = qtdVolumes;
		this.qtdApensos = qtdApensos;
		this.formaRecebimento = formaRecebimento;
		this.numeroSedex = numeroSedex;
		this.protocolo = protocolo;
	}

	public String getClasseSugerida() {
		return classeSugerida;
	}

	public int getQtdVolumes() {
		return qtdVolumes;
	}

	public void setQtdVolumes(int qtdVolumes) {
		this.qtdVolumes = qtdVolumes;
	}

	public int getQtdApensos() {
		return qtdApensos;
	}

	public void setQtdApensos(int qtdApensos) {
		this.qtdApensos = qtdApensos;
	}

	public String getFormaRecebimento() {
		return formaRecebimento;
	}

	public void setFormaRecebimento(String formaRecebimento) {
		this.formaRecebimento = formaRecebimento;
	}

	public String getNumeroSedex() {
		return numeroSedex;
	}

	public void setNumeroSedex(String numeroSedex) {
		this.numeroSedex = numeroSedex;
	}
	
	public Long getProtocolo() {
		return protocolo;
	}
	
	public void setProtocolo(Long protocolo) {
		this.protocolo = protocolo;
	}

}
