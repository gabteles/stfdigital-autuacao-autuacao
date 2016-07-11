package br.jus.stf.autuacao.interfaces.dto;

/**
 * 
 * @author rodrigo.barreiros
 *
 */
public class AssuntoDto {
	
	private String codigo;
	
	private String descricao;
	
	private int nivel;
	
	public AssuntoDto(String codigo, String descricao, int nivel) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.nivel = nivel;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getNivel() {
		return nivel;
	}

}
