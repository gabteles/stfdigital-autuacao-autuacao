package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * @author lucas.rodrigues
 *
 */
public class AnaliseRepercussaoGeralDto extends ProcessoRecursalDto {

	private Boolean temTeseRepercussaoGeral;
	private String observacao;
	private List<TeseDto> teses;
	
	public AnaliseRepercussaoGeralDto(ProcessoRecursalDto processo, Boolean temTeseRepercussaoGeral, String observacao, List<TeseDto> teses) {
		try {
			BeanUtils.copyProperties(processo, this);
		} catch (Exception e) {
			throw new RuntimeException("Não foi possível copiar propriedades do dto do processo.", e);
		}
		this.temTeseRepercussaoGeral = temTeseRepercussaoGeral;
		this.observacao = observacao;
		this.teses = teses;
	}
	
	public Boolean getTemTeseRepercussaoGeral() {
		return temTeseRepercussaoGeral;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public List<TeseDto> getTeses() {
		return teses;
	}
}
