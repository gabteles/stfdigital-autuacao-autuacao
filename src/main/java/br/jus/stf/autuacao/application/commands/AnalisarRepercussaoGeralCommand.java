package br.jus.stf.autuacao.application.commands;

import java.util.Set;

import javax.validation.constraints.NotNull;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author viniciusk
 *
 */
@ApiModel(value="Command usado pelo front-end para enviar os dados da analise de repercussão geral para o back-end")
public class AnalisarRepercussaoGeralCommand {
	
	
	@NotNull
    @ApiModelProperty(value = "O identificador do processo originário criado ao iniciar o fluxo de autuação.", required=true)
    private Long processoId;
    
	@ApiModelProperty(value = "Lista com os código das teses adicionadas")
	private Set<Long> teses;
	
	@ApiModelProperty(value = "Lista de assuntos")
	private Set<String> assuntos;
    
	@ApiModelProperty(value = "Observação da analise")
	private String observacao;
	
	public Long getProcessoId() {
	    return processoId;
	}

	public Set<Long> getTeses() {
		return teses;
	}

	public Set<String> getAssuntos() {
		return assuntos;
	}

	public String getObservacao() {
		return observacao;
	}
	
}
