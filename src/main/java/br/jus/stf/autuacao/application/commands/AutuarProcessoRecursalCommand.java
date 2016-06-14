package br.jus.stf.autuacao.application.commands;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando usado pelo front-end para enviar os dados da autuação de processo recursal para o back-end.
 * 
 * @author anderson.araujo
 * @since 30/05/2016
 *
 */
@ApiModel(value = "Comando usado pelo front-end para enviar os dados da autuação de processo recursal para o back-end.")
public class AutuarProcessoRecursalCommand {
	@NotNull
    @ApiModelProperty(value = "O identificador do processo originário criado ao iniciar o fluxo de autuação.", required=true)
    private Long processoId;
    
    @NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required=true)
	private Set<String> poloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required=true)
	private Set<String> poloPassivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista de assuntos", required=true)
	private Set<String> assuntos;
    
	public Long getProcessoId() {
	    return processoId;
	}
	
	public Set<String> getPoloAtivo() {
		return poloAtivo;
	}
	
	public Set<String> getPoloPassivo() {
		return poloPassivo;
	}
	
	public Set<String> getAssuntos() {
		return assuntos;
	}
}
