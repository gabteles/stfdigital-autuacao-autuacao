package br.jus.stf.autuacao.infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.model.procedenciageografica.Pais;
import br.jus.stf.autuacao.domain.model.procedenciageografica.UnidadeFederacao;
import br.jus.stf.autuacao.domain.model.procedenciageografica.UnidadeFederacaoRepository;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 17.05.2016
 */
@Repository
public class UnidadeFederacaoRepositoryImpl extends SimpleJpaRepository<UnidadeFederacao, Long> implements UnidadeFederacaoRepository {

	private EntityManager entityManager;
	
	/**
	 * @param entityManager
	 */
	@Autowired
    public UnidadeFederacaoRepositoryImpl(EntityManager entityManager) {
        super(UnidadeFederacao.class, entityManager);
        this.entityManager = entityManager;
    }

	@Override
	public List<UnidadeFederacao> findByPais(Pais pais) {
		TypedQuery<UnidadeFederacao> query = entityManager.createQuery("FROM UnidadeFederacao uf WHERE uf.pais = :pais ORDER BY uf.sigla", UnidadeFederacao.class);
		
		query.setParameter("pais", pais);
		return query.getResultList();
	}
    
}
