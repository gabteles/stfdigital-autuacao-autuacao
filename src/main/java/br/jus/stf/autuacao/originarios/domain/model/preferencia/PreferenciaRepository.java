package br.jus.stf.autuacao.originarios.domain.model.preferencia;

import java.util.List;

import br.jus.stf.core.shared.preferencia.PreferenciaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
public interface PreferenciaRepository {

	List<Preferencia> findAll();
	
	Preferencia findOne(PreferenciaId id);

}