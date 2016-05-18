package br.jus.stf.autuacao.originarios.domain;

import br.jus.stf.autuacao.originarios.infra.ParteDto;

/**
 * Adapter para trazer as informações das partes cadastradas no peticionamento 
 * @author viniciusk
 *
 */
public interface ParteAdapter {
	
	ParteDto consultar (Long id);

}
