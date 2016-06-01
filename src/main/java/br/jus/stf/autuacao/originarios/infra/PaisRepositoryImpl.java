package br.jus.stf.autuacao.originarios.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.originarios.domain.model.procedenciageografica.Pais;
import br.jus.stf.autuacao.originarios.domain.model.procedenciageografica.PaisRepository;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 17.05.2016
 */
@Repository
public class PaisRepositoryImpl extends SimpleJpaRepository<Pais, Long> implements PaisRepository {

	@Autowired
    public PaisRepositoryImpl(EntityManager entityManager) {
        super(Pais.class, entityManager);
    }
    
}
