package br.jus.stf.autuacao.domain.model.controletese;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.controletese.AssuntoId;

@Entity
@Table(name = "ASSUNTO", schema = "AUTUACAO")
public class Assunto extends EntitySupport<Assunto, AssuntoId> {
	
	@EmbeddedId
	private AssuntoId codigo;
	
	@Column(name = "DSC_ASSUNTO", nullable = false)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "COD_ASSUNTO_PAI")
	private Assunto assuntoPai;
	
	public Assunto() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public Assunto(AssuntoId codigo, String descricao, Assunto assuntoPai) {
		Validate.notNull(codigo, "Código requerido.");
		Validate.notBlank(descricao, "Descrição requerida.");
		
		this.codigo = codigo;
		this.descricao = descricao;
		this.assuntoPai = assuntoPai;
	}
	
	
	public String descricao() {
		return descricao;
	}
	
	public Assunto assuntoPai() {
		return assuntoPai;
	}

	@Override
	public String toString() {
		return String.format("%s - %s", codigo.toString(), descricao);
	}
	
	@Override
	public AssuntoId identity() {
		return codigo;
	}

}
