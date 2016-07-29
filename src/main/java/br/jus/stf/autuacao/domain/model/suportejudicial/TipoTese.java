package br.jus.stf.autuacao.domain.model.suportejudicial;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 13.05.2016
 */
public enum TipoTese {
	
	CONTROVERSIA("Controversia"),
	PRE_TEMA("Pré-tema"),
	REPERCUSSAO_GERAL("Repercussão Geral");
	
	private String descricao;
	
	private TipoTese(final String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * @return
	 */
	public String descricao() {
		return descricao;
	}

}
