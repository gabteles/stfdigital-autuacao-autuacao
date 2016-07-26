package br.jus.stf.autuacao.domain.model.suportejudicial;

import java.util.List;

import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.processo.TipoProcesso;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
public interface ClasseRepository {

	/**
	 * @return
	 */
	List<Classe> findAll();
	
	/**
	 * @param id
	 * @return
	 */
	Classe findOne(ClasseId id);
	
	/**
	 * @param tipo
	 * @return
	 */
	List<Classe> findByTipo(TipoProcesso tipo);

}
