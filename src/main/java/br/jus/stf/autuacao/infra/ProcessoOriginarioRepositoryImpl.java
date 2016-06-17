package br.jus.stf.autuacao.infra;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.model.MotivoInaptidao;
import br.jus.stf.autuacao.domain.model.Parte;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoOriginarioRepository;
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
    
	/** Processo **/
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

    /** Motido de inaptidão **/
	@Override
	public MotivoInaptidao findOneMotivoInaptidao(Long id) {
		TypedQuery<MotivoInaptidao> query = entityManager.createQuery("FROM MotivoInaptidao motivo WHERE motivo.codigo = :id", MotivoInaptidao.class);

		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	@Override
	public List<MotivoInaptidao> findAllMotivoInaptidao() {
		TypedQuery<MotivoInaptidao> query = entityManager.createQuery("FROM MotivoInaptidao motivo ORDER BY motivo.descricao", MotivoInaptidao.class);
		
		return query.getResultList();
	}
	
	@Override
	public <M extends MotivoInaptidao> M saveMotivoInaptidao(M motivoInaptidao) {
		return entityManager.merge(motivoInaptidao);
	}

	@Override
	public void deleteMotivoInaptidao(MotivoInaptidao motivoInaptidao) {
		entityManager.remove(motivoInaptidao);
	}

}
