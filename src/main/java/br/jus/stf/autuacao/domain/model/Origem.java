package br.jus.stf.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;

import br.jus.stf.autuacao.domain.model.identidade.TribunalJuizo;
import br.jus.stf.autuacao.domain.model.procedenciageografica.UnidadeFederacao;
import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 17.05.2016
 */
@Entity
@Table(name = "PROCESSO_ORIGEM", schema = "AUTUACAO")
public class Origem extends ValueObjectSupport<Origem> {
	
	@Id
	@Column(name = "SEQ_PROCESSO_ORIGEM")
	@SequenceGenerator(name = "PROCESSOORIGEMID", sequenceName = "AUTUACAO.SEQ_PROCESSO_ORIGEM", allocationSize = 1)
	@GeneratedValue(generator = "PROCESSOORIGEMID", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_UNIDADE_FEDERACAO")
	private UnidadeFederacao procedenciaGeografica;
	
	@ManyToOne
	@JoinColumn(name = "COD_TRIBUNAL_JUIZO")
	private TribunalJuizo tribunalJuizo;
	
	@Column(name = "NUM_PROCESSO")
	private Long numeroProcesso;
	
	@Column(name = "FLG_PRINCIPAL")
	@Type(type = "yes_no")
	private Boolean origemPrincipal;
	
	public Origem() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param procedenciaGeografica
	 * @param tribunalJuizo
	 * @param numeroProcesso
	 * @param origemPrincipal
	 */
	public Origem(UnidadeFederacao procedenciaGeografica, TribunalJuizo tribunalJuizo, Long numeroProcesso,
			Boolean origemPrincipal) {
		Validate.notNull(procedenciaGeografica, "Procedência geográfica requerida.");
		Validate.notNull(tribunalJuizo, "Tribunal de juízo requerido.");
		Validate.notNull(numeroProcesso, "Número do processo requerido.");
		
		this.procedenciaGeografica = procedenciaGeografica;
		this.tribunalJuizo = tribunalJuizo;
		this.numeroProcesso = numeroProcesso;
		this.origemPrincipal = origemPrincipal;
	}
	
	/**
	 * @return
	 */
	public Long toLong() {
		return id;
	}
	
	/**
	 * @return
	 */
	public Boolean origemPrincipal() {
		return origemPrincipal;
	}

}