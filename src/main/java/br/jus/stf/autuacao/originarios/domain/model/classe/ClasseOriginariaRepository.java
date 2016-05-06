package br.jus.stf.autuacao.originarios.domain.model.classe;

import java.util.List;

import br.jus.stf.core.shared.classe.ClasseId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
public interface ClasseOriginariaRepository {

	List<ClasseOriginaria> findAll();
	
	ClasseOriginaria findOne(ClasseId id);

}
