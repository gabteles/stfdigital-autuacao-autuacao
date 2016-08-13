package br.jus.stf.autuacao.application.commands;

import javax.validation.constraints.NotNull;


import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author viniciusk
 *
 */
@ApiModel(value="Command usado pelo front-end para enviar os dados da analise de repercussão geral para o back-end")
public class AnalisarRepercussaoGeralCommand extends RepercussaoGeralCommand{
	
	@NotNull
	@ApiModelProperty(value = "Sinaliza se o processo tem ou não teses de repercussão geral", required = true)
    private Boolean repercussaoGeral;
	
	public Boolean isRepercussaoGeral() {
		return repercussaoGeral;
	}
	
}
