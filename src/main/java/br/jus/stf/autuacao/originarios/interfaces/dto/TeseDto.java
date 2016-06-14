package br.jus.stf.autuacao.originarios.interfaces.dto;

import java.util.List;

import br.jus.stf.autuacao.interfaces.dto.AssuntoDto;

/**
 * 
 * @author viniciusk
 *
 */
public class TeseDto {
	
	private Long codigo;
	
	private String descricao;
	
	private Long numero;
	
	private List<AssuntoDto> assuntos;
	
	private String tipoTese;
	
	public TeseDto(Long codigo, String descricao, Long numero, List<AssuntoDto> assuntos, String tipoTese) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.numero = numero;
		this.assuntos = assuntos;
		this.tipoTese = tipoTese;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Long getNumero() {
		return numero;
	}
	
	public List<AssuntoDto> getAssuntos(){
		return assuntos;
	}
	
	public String getTipoTese() {
		return tipoTese;
	}

}
