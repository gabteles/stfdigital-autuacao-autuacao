package br.jus.stf.autuacao.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.model.preferencia.Preferencia;
import br.jus.stf.autuacao.domain.model.preferencia.PreferenciaRepository;
import br.jus.stf.core.shared.preferencia.PreferenciaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
@Repository
public class PreferenciaRepositoryImpl extends SimpleJpaRepository<Preferencia, PreferenciaId> implements PreferenciaRepository {

	/**
	 * @param entityManager
	 */
	@Autowired
    public PreferenciaRepositoryImpl(EntityManager entityManager) {
        super(Preferencia.class, entityManager);
    }
    
}
