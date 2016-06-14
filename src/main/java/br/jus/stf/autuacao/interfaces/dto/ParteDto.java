package br.jus.stf.autuacao.interfaces.dto;

public class ParteDto {
	
	private String apresentacao;
	
	private String polo;
	
	public ParteDto(String apresentacao, String polo) {
		this.apresentacao = apresentacao;
		this.polo = polo;
	}
	
	public ParteDto() {}

	public String getApresentacao() {
		return apresentacao;
	}

	public String getPolo() {
		return polo;
	}
	
	

}
