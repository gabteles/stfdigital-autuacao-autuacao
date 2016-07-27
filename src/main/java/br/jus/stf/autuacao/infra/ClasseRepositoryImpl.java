package br.jus.stf.autuacao.infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.model.suportejudicial.Classe;
import br.jus.stf.autuacao.domain.model.suportejudicial.ClasseRepository;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.processo.TipoProcesso;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
@Repository
public class ClasseRepositoryImpl extends SimpleJpaRepository<Classe, ClasseId> implements ClasseRepository {
	
	private EntityManager entityManager;

	@Autowired
    public ClasseRepositoryImpl(EntityManager entityManager) {
        super(Classe.class, entityManager);
        this.entityManager = entityManager;
    }
	
	@Override
	public List<Classe> findByTipo(TipoProcesso tipo) {
		TypedQuery<Classe> query = entityManager.createQuery("SELECT classe FROM Classe classe WHERE classe.tipo = :tipo", Classe.class);
		
		query.setParameter("tipo", tipo);
		return query.getResultList();
	}
    
}
