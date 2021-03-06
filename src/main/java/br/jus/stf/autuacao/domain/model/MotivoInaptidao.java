package br.jus.stf.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;

/**
 * @author Rafael Alencar
 *
 * @since 16.05.2016
 */
@Entity
@Table(name = "MOTIVO_INAPTIDAO", schema = "AUTUACAO")
public class MotivoInaptidao extends EntitySupport<MotivoInaptidao, Long> {
	
	@Id
	@Column(name = "COD_MOTIVO_INAPTIDAO")
	private Long codigo;
	
	@Column(name = "DSC_MOTIVO_INAPTIDAO", nullable = false)
	private String descricao;
	
	MotivoInaptidao() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param codigo
	 * @param descricao
	 */
	public MotivoInaptidao(final Long codigo, final String descricao) {
		Validate.notNull(codigo, "Código requerido.");
		Validate.notBlank(descricao, "Descrição requerida.");
		
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	@Override
	public Long identity() {
		return this.codigo;
	}
	
	@Override
	public String toString() {
		return String.format("%d - %s", codigo, descricao);
	}

	/**
	 * @return
	 */
	public String descricao() {
		return descricao;
	}

}
