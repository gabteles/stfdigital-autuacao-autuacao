package br.jus.stf.autuacao.originarios.application.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.autuacao.originarios.interfaces.dto.AutuarParteCommand;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@ApiModel(value = "Contém as informações necessárias para autuar um processo originário")
public class AutuarProcessoCommand {

    @NotNull
    @ApiModelProperty(value = "O identificador do processo originário criado ao iniciar o fluxo de autuação.", required=true)
    private Long processoId;
    
    @NotBlank
	@ApiModelProperty(value = "O identificador da classe processual definitiva, selecionada pelo autuador", required=true)
    private String classeId;
    
    @NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required=true)
	private List<AutuarParteCommand> poloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required=true)
	private List<AutuarParteCommand> poloPassivo;
    
	public Long getProcessoId() {
	    return processoId;
	}

	public String getClasseId() {
		return classeId;
	}
	
	public List<AutuarParteCommand> getPoloAtivo() {
		return poloAtivo;
	}
	
	public List<AutuarParteCommand> getPoloPassivo() {
		return poloPassivo;
	}

}
