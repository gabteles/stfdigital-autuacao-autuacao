package br.jus.stf.autuacao.originarios.domain.model.classe;

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

	List<Classe> findAll();
	
	Classe findOne(ClasseId id);
	
	List<Classe> findByTipo(TipoProcesso tipo);

}
