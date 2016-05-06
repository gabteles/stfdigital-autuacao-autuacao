package br.jus.stf.autuacao.originarios.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.originarios.domain.model.classe.ClasseOriginaria;
import br.jus.stf.autuacao.originarios.domain.model.classe.ClasseOriginariaRepository;
import br.jus.stf.core.shared.classe.ClasseId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
@Repository
public class ClasseOriginariaRepositoryImpl extends SimpleJpaRepository<ClasseOriginaria, ClasseId> implements ClasseOriginariaRepository {

	@Autowired
    public ClasseOriginariaRepositoryImpl(EntityManager entityManager) {
        super(ClasseOriginaria.class, entityManager);
    }
    
}
