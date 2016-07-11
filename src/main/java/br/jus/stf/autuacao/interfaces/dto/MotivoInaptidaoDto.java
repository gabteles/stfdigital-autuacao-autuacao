package br.jus.stf.autuacao.interfaces.dto;

/**
 * 
 * @author viniciusk
 *
 */
public class MotivoInaptidaoDto {
	
	public Long codigo;
	
	private String descricao;
	
	public MotivoInaptidaoDto(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
