package br.jus.stf.autuacao.application.commands;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author viniciusk
 * 
 * @since 1.0.0
 * @since 25.05.2016
 */
@ApiModel(value = "Contém as informações necessárias para analisar os pressupostos de um processo originário")
public class AnalisarPressupostosFormaisCommand {
	
    @NotNull
    @ApiModelProperty(value = "O identificador do processo originário criado ao iniciar o fluxo de autuação.", required=true)
    private Long processoId;
    
    @NotNull
    @ApiModelProperty(value = "Sinaliza se o processo é apto ou inapto para autução", required = true)
    private Boolean processoApto;
    
    @ApiModelProperty(value = "O identificador do motivo de inaptidão da análise do processo")
    private Set<Long> motivosInaptidao;
    
    @ApiModelProperty(value = "Observação da análise do processo")
    private String observacao;

	public Long getProcessoId() {
		return processoId;
	}

	public Boolean isProcessoApto() {
		return processoApto;
	}

	public Set<Long> getMotivosInaptidao() {
		return motivosInaptidao;
	}

	public String getObservacao() {
		return observacao;
	}
    
}
