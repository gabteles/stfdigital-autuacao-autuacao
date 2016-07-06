package br.jus.stf.autuacao.application.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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
    
    @NotBlank
	@ApiModelProperty(value = "O identificador da classe processual definitiva, selecionada pelo autuador", required=true)
    private String classeId;
    
    @NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required=true)
	private List<CadastrarParteCommand> poloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required=true)
	private List<CadastrarParteCommand> poloPassivo;
	
	@ApiModelProperty(value = "Motivo da invalidação do processo na autuação", required=true)
	private String motivo;
	
	@ApiModelProperty(value = "Indica se a autuacao do processo é válida ou inválida", required=true)
	private boolean valida;
	
    
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

	public boolean isValida() {
		return valida;
	}
	
	

}
