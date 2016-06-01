package br.jus.stf.autuacao.originarios.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
@Entity
@Table(name = "REJEICAO", schema = "AUTUACAO")
public class Rejeicao extends ValueObjectSupport<Rejeicao> {
	
	@EmbeddedId
	private ProcessoId processo;
	
	@Column(name = "DSC_MOTIVO", nullable = false)
	private String motivo;
	
	public Rejeicao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public Rejeicao(ProcessoId processo, String motivo) {
		Validate.notNull(processo, "Processo requerido.");
		Validate.notBlank(motivo, "Motivo requerido.");
		
		this.processo = processo;
		this.motivo = motivo;
	}
	
	public ProcessoId processo() {
		return processo;
	}
	
	public String motivo() {
		return motivo;
	}
	
	@Override
	public String toString() {
		return String.format("%s", motivo);
	}

}
