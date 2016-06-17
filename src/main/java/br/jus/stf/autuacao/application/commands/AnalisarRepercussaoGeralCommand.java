package br.jus.stf.autuacao.application.commands;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

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
    
	@NotEmpty
	@ApiModelProperty(value = "Lista com os código das teses adicionadas", required = true)
	private Set<Long> teses;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista de assuntos", required = true)
	private Set<String> assuntos;
    
	@ApiModelProperty(value = "Observação da analise")
	private String observacao;
	
	@NotNull
	@ApiModelProperty(value = "Sinaliza se o processo tem ou não teses de repercussão geral", required = true)
    private Boolean repercussaoGeral;
	
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
	
	public Boolean isRepercussaoGeral() {
		return repercussaoGeral;
	}
	
}
