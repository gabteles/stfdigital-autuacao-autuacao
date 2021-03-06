package br.jus.stf.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.core.shared.processo.Identificacao;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.02.2016
 */
@Component
@FunctionalInterface
public interface NumeroProcessoAdapter {
	
	/**
	 * @param classe
	 * @return
	 */
	public Identificacao novoNumeroProcesso(String classe);
	
}
