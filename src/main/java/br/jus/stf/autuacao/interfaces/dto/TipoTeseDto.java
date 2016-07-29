package br.jus.stf.autuacao.interfaces.dto;

public class TipoTeseDto {
	
	private String id;
	private String nome;
	
	public TipoTeseDto(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
