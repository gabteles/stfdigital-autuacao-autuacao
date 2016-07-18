package br.jus.stf.autuacao.application.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@ApiModel(value = "Contém as informações necessárias para autuar um processo originário")
public class AutuarOriginarioCommand {

    @NotNull
    @ApiModelProperty(value = "O identificador do processo originário criado ao iniciar o fluxo de autuação.", required=true)
    private Long processoId;
    
    @ApiModelProperty(value = "O identificador da classe processual definitiva, selecionada pelo autuador.")
    private String classeId;
    
    @ApiModelProperty(value = "Lista com as partes do polo ativo.")
	private List<CadastrarParteCommand> poloAtivo;
	
	@ApiModelProperty(value = "Lista com as partes do polo passivo.")
	private List<CadastrarParteCommand> poloPassivo;
	
	@ApiModelProperty(value = "Motivo da invalidação do processo na autuação.")
	private String motivo;
	
	@NotNull
	@ApiModelProperty(value = "Indica se a autuacao do processo é válida ou inválida.", required=true)
	private Boolean valida;
	
    
	public Long getProcessoId() {
	    return processoId;
	}

	public String getClasseId() {
		return classeId;
	}
	
	public List<CadastrarParteCommand> getPoloAtivo() {
		return poloAtivo;
	}
	
	public List<CadastrarParteCommand> getPoloPassivo() {
		return poloPassivo;
	}

	public String getMotivo() {
		return motivo;
	}

	public Boolean isValida() {
		return valida;
	}
	
	

}
