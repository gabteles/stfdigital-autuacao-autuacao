package br.jus.stf.autuacao.application.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Commando usado para enviar um processo.
 * 
 * @author Anderson.Araujo
 * @since 18.03.2016
 *
 */
@ApiModel("Commando usado para enviar um processo.")
public class EnviarProcessoRecursalCommand {
	@NotBlank
	@ApiModelProperty(value = "Id da classe processual.", required=true)
	private String classeId;
	
	@NotBlank
	@ApiModelProperty(value = "Sigilo do processo.", required = true)
	private String sigilo;
	
	@NotNull
	@ApiModelProperty(value = "Número de recursos do processo.", required = true)
	private Integer numeroRecursos;
	
	@ApiModelProperty(value = "Lista de preferências do processo.")
	private List<Long> preferencias;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista das origens do processo.", required = true)
	private List<AssociarOrigemProcessoCommand> origens;
	
	@NotEmpty
	@ApiModelProperty(value = "Assuntos do processo.", required = true)
	private List<String> assuntos;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo.", required = true)
	private List<CadastrarParteCommand> partesPoloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo.", required = true)
	private List<CadastrarParteCommand> partesPoloPassivo;
	
	@NotNull
    @ApiModelProperty(value = "Sinaliza se o processo é ou não criminal/eleitoral.", required=true)
    private Boolean criminalEleitoral;
	
	public String getClasseId() {
		return classeId;
	}

	public void setClasseId(String classeId) {
		this.classeId = classeId;
	}

	public String getSigilo() {
		return sigilo;
	}

	public void setSigilo(String sigilo) {
		this.sigilo = sigilo;
	}

	public Integer getNumeroRecursos() {
		return numeroRecursos;
	}

	public void setNumeroRecursos(Integer numeroRecursos) {
		this.numeroRecursos = numeroRecursos;
	}

	public List<Long> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(List<Long> preferencias) {
		this.preferencias = preferencias;
	}

	public List<AssociarOrigemProcessoCommand> getOrigens() {
		return origens;
	}

	public void setOrigens(List<AssociarOrigemProcessoCommand> origens) {
		this.origens = origens;
	}

	public List<String> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(List<String> assuntos) {
		this.assuntos = assuntos;
	}

	public List<CadastrarParteCommand> getPartesPoloAtivo() {
		return partesPoloAtivo;
	}

	public void setPartesPoloAtivo(List<CadastrarParteCommand> partesPoloAtivo) {
		this.partesPoloAtivo = partesPoloAtivo;
	}

	public List<CadastrarParteCommand> getPartesPoloPassivo() {
		return partesPoloPassivo;
	}

	public void setPartesPoloPassivo(List<CadastrarParteCommand> partesPoloPassivo) {
		this.partesPoloPassivo = partesPoloPassivo;
	}
	
	public Boolean isCriminalEleitoral() {
    	return criminalEleitoral;
    }
	
	public void setCriminalEleitora(Boolean criminalEleitoral) {
		this.criminalEleitoral = criminalEleitoral;
	}
}
