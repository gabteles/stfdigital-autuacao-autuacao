package br.jus.stf.autuacao.application.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
@ApiModel(value = "Contém as informações necessárias para rejeitar um processo originário")
public class RejeitarProcessoCommand {

    @NotNull
    @ApiModelProperty(value = "O identificador do processo originário criado ao iniciar o fluxo de autuação.", required=true)
    private Long processoId;
    
    @NotBlank
	@ApiModelProperty(value = "O motivo de rejeição do processo originário.", required=true)
    private String motivo;
    
	public Long getProcessoId() {
	    return processoId;
	}

	public String getMotivo() {
		return motivo;
	}

}
