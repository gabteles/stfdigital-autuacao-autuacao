package br.jus.stf.autuacao.originarios.infra;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.originarios.domain.model.Parte;
import br.jus.stf.autuacao.originarios.domain.model.Processo;
import br.jus.stf.autuacao.originarios.domain.model.ProcessoOriginarioRepository;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Lucas Mariano
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 12.02.2016
 */
@Repository
public class ProcessoOriginarioRepositoryImpl extends SimpleJpaRepository<Processo, ProcessoId> implements ProcessoOriginarioRepository {

    private EntityManager entityManager;

	@Autowired
    public ProcessoOriginarioRepositoryImpl(EntityManager entityManager) {
        super(Processo.class, entityManager);
        
        this.entityManager = entityManager;
    }
    
    @Override
    public ProcessoId nextProcessoId() {
    	Query q = entityManager.createNativeQuery("SELECT autuacao.seq_processo.NEXTVAL FROM DUAL");
    	
    	return new ProcessoId(((BigInteger) q.getSingleResult()).longValue());
    }
    
	@Override
	public List<Parte> consultarPartes(Long processoId) {
		TypedQuery<Parte> query = entityManager
				.createQuery("SELECT proc.partes FROM Processo proc WHERE processoId.id = :id", Parte.class);

		query.setParameter("id", processoId);

		return query.getResultList();
	}

}
