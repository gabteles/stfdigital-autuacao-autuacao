package br.jus.stf.autuacao.originarios.interfaces.dto;

/**
 * 
 * @author rodrigo.barreiros
 *
 */
public class AssuntoDto {
	
	public String codigo;
	
	private String descricao;
	
	public AssuntoDto(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}
