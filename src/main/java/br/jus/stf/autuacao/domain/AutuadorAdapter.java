package br.jus.stf.autuacao.domain;

import br.jus.stf.autuacao.domain.model.Autuador;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 15.07.2016
 */
@FunctionalInterface
public interface AutuadorAdapter {
	
	/**
	 * @return
	 */
	Autuador autuador();

}
