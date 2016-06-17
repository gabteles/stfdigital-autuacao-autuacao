package br.jus.stf.autuacao.application.commands;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 11.02.2016
 */
public class IniciarAutuacaoCommand {
    
    @NotNull
    @ApiModelProperty(value = "O identificador do protocolo gerado durante o peticionamento digital ou o registro de uma remessa.", required=true)
    private Long protocoloId;
    
    @NotNull
    @ApiModelProperty(value = "O identificador da classe informado no peticionamento digital ou na preautuação de uma remessa.", required=true)
    private String classeId;
    
    @NotNull
    @ApiModelProperty(value = "O tipo de processo da petição ou da remessa.", required=true)
    private String tipoProcesso;
    
    @NotNull
    @ApiModelProperty(value = "O meio de tramitação do processo.", required=true)
    private String meioTramitacao;
    
    @NotNull
    @ApiModelProperty(value = "O grau de sigilo do processo.", required=true)
    private String sigilo;
    
    @NotNull
    @ApiModelProperty(value = "Sinaliza se o processo é ou não criminal/eleitoral.", required=true)
    private Boolean criminalEleitoral;
    
    public IniciarAutuacaoCommand(Long protocoloId, String classeId, String tipoProcesso, String meioTramitacao, String sigilo, Boolean criminalEleitoral) {
        this.protocoloId = protocoloId;
        this.classeId = classeId;
        this.tipoProcesso = tipoProcesso;
        this.meioTramitacao = meioTramitacao;
        this.sigilo = sigilo;
        this.criminalEleitoral = criminalEleitoral;
    }

    public Long getProtocoloId() {
        return protocoloId;
    }
    
    public String getClasseId() {
    	return classeId;
    }
    
    public String getTipoProcesso() {
    	return tipoProcesso;
    }
    
    public String getMeioTramitacao() {
    	return meioTramitacao;
    }
    
    public String getSigilo() {
    	return sigilo;
    }
    
    public Boolean isCriminalEleitoral() {
    	return criminalEleitoral;
    }

}
