package br.jus.stf.autuacao.infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.model.controletese.Assunto;
import br.jus.stf.autuacao.domain.model.controletese.Tese;
import br.jus.stf.autuacao.domain.model.controletese.TeseRepository;
import br.jus.stf.autuacao.domain.model.controletese.TipoTese;
import br.jus.stf.core.shared.controletese.AssuntoId;
import br.jus.stf.core.shared.controletese.TeseId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 13.05.2016
 */
@Repository
public class TeseRepositoryImpl  extends SimpleJpaRepository<Tese, TeseId> implements TeseRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public TeseRepositoryImpl(EntityManager entityManager) {
		super(Tese.class, entityManager);
		this.entityManager = entityManager;
	}
	
	/** Assunto **/
	@Override
	public Assunto findOneAssunto(AssuntoId assuntoId) {
		TypedQuery<Assunto> query = entityManager.createQuery("FROM Assunto assu WHERE assu.codigo = :codigo", Assunto.class);
		
		query.setParameter("codigo", assuntoId);
		return query.getSingleResult();
	}
	
	@Override
	public List<Assunto> findAssuntoByDescricao(String descricao) {
		TypedQuery<Assunto> query = entityManager.createQuery("SELECT assu FROM Assunto assu LEFT JOIN FETCH assu.assuntoPai WHERE UPPER(assu.descricao) LIKE UPPER(:descricao) ORDER BY assu.assuntoPai", Assunto.class);
		
		query.setParameter("descricao", "%" + descricao + "%");
		return query.getResultList();
	}
	
	/** Tese **/
	@Override
	public List<Tese> findTeseByTipo(TipoTese tipo, Long numero){
		TypedQuery<Tese> query = entityManager.createQuery("FROM Tese tese WHERE tese.tipo = :tipo AND tese.numero = :numero", Tese.class);
		
		query.setParameter("tipo", tipo);
		query.setParameter("numero", numero);
		return query.getResultList();
	}

	@Override
	public List<Tese> findTesesByIds(List<TeseId> tesesIds) {
		TypedQuery<Tese> query = entityManager.createQuery("FROM Tese tese WHERE tese.id in :ids", Tese.class);
		
		query.setParameter("ids", tesesIds);
		return query.getResultList();
	}
	
}
