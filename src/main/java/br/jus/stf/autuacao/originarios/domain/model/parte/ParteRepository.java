package br.jus.stf.autuacao.originarios.domain.model.parte;

import java.util.List;

/**
 * 
 * @author viniciusk
 *
 */

public interface ParteRepository {

	List<Parte> consultar(Long processoId);
}
