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
public class AnalisarPressupostosCommand {
	
    @NotNull
    @ApiModelProperty(value = "O identificador do processo originário criado ao iniciar o fluxo de autuação.", required=true)
    private Long processoId;
    
    @NotNull
    @ApiModelProperty(value = "Sinaliza se o processo é apto ou inapto para autução", required = true)
    private Boolean analiseApta;
    
    @ApiModelProperty(value = "O identificador do motivo de inaptidao da análise do processo")
    private Set<Long> motivos;
    
    @ApiModelProperty(value = "Observação da análise do processo")
    private String observacao;

	public Long getProcessoId() {
		return processoId;
	}

	public Boolean isAnaliseApta() {
		return analiseApta;
	}

	public Set<Long> getMotivos() {
		return motivos;
	}

	public String getObservacao() {
		return observacao;
	}
    
    

}
