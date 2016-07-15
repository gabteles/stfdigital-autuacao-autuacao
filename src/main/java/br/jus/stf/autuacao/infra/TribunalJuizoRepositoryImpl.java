package br.jus.stf.autuacao.infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.model.identidade.TribunalJuizo;
import br.jus.stf.autuacao.domain.model.identidade.TribunalJuizoRepository;
import br.jus.stf.autuacao.domain.model.procedenciageografica.UnidadeFederacao;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 29.06.2016
 */
@Repository
public class TribunalJuizoRepositoryImpl extends SimpleJpaRepository<TribunalJuizo, Long> implements TribunalJuizoRepository {

	private EntityManager entityManager;
	
	/**
	 * @param entityManager
	 */
	@Autowired
    public TribunalJuizoRepositoryImpl(EntityManager entityManager) {
        super(TribunalJuizo.class, entityManager);
        this.entityManager = entityManager;
    }
	
	@Override
	public List<TribunalJuizo> findByUnidadeFederacao(UnidadeFederacao uf) {
		TypedQuery<TribunalJuizo> query = entityManager.createQuery("FROM TribunalJuizo tj INNER JOIN tj.ufsAtuacao uf WITH uf = :uf", TribunalJuizo.class);
		
		query.setParameter("uf", uf);
		
		return query.getResultList();
	}
    
}
