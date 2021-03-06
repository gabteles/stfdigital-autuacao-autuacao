package br.jus.stf.autuacao.domain.model.suportejudicial;

import java.util.List;

import br.jus.stf.core.shared.preferencia.PreferenciaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
public interface PreferenciaRepository {

	/**
	 * @return
	 */
	List<Preferencia> findAll();
	
	/**
	 * @param id
	 * @return
	 */
	Preferencia findOne(PreferenciaId id);

}
