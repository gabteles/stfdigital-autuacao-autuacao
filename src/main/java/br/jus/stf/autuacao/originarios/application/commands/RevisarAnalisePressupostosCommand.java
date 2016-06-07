package br.jus.stf.autuacao.originarios.application.commands;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando usado para efetuar a revisão de uma análise de pressupostos.
 * 
 * @author anderson.araujo
 * @since 06/06/2016
 *
 */
@ApiModel(value = "Contém as informações necessárias para analisar os pressupostos de um processo originário")
public class RevisarAnalisePressupostosCommand {
	
    @NotNull
    @ApiModelProperty(value = "O identificador do processo originário criado ao iniciar o fluxo de autuação.", required=true)
    private Long processoId;
    
    @NotNull
    @ApiModelProperty(value = "Sinaliza se o processo é apto ou inapto para autução")
    private boolean aptidao;
    
    @ApiModelProperty(value = "O identificador do motivo de inaptidao da análise do processo")
    private Set<Long> motivos;
    
    @ApiModelProperty(value = "Observação da análise do processo")
    private String observacao;
    

	public Long getProcessoId() {
		return processoId;
	}

	public boolean isAptidao() {
		return aptidao;
	}

	public Set<Long> getMotivos() {
		return motivos;
	}

	public String getObservacao() {
		return observacao;
	}
}
