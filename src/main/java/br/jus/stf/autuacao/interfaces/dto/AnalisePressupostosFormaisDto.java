package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * @author lucas.rodrigues
 *
 */
public class AnalisePressupostosFormaisDto extends ProcessoRecursalDto {

	private Boolean processoApto;
	private String observacao;
	private List<MotivoInaptidaoDto> motivosInaptidao;
	
	/**
	 * @param processo
	 * @param processoApto
	 * @param observacao
	 * @param motivosInaptidao
	 */
	public AnalisePressupostosFormaisDto(ProcessoRecursalDto processo, Boolean processoApto, String observacao, List<MotivoInaptidaoDto> motivosInaptidao) {
		try {
			BeanUtils.copyProperties(processo, this);
		} catch (Exception e) {
			throw new RuntimeException("Não foi possível copiar propriedades do dto do processo.", e);
		}
		this.processoApto = processoApto;
		this.observacao = observacao;
		this.motivosInaptidao = motivosInaptidao;
	}

	public Boolean getProcessoApto() {
		return processoApto;
	}
	
	public String getObservacao() {
		return observacao;
	}

	public List<MotivoInaptidaoDto> getMotivosInaptidao() {
		return motivosInaptidao;
	}
	
}
