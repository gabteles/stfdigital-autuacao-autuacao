package br.jus.stf.autuacao.infra;

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
	
	

}
