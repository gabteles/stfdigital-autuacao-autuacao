package br.jus.stf.autuacao.originarios.interfaces.dto;

/**
 * 
 * @author viniciusk
 *
 */
public class MotivoInaptidaoDto {
	
	public Long id;
	
	private String descricao;
	
	public MotivoInaptidaoDto(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
