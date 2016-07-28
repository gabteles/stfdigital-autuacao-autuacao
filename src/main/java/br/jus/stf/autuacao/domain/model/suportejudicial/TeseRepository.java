package br.jus.stf.autuacao.domain.model.suportejudicial;

import java.util.List;

import br.jus.stf.core.shared.controletese.AssuntoId;
import br.jus.stf.core.shared.controletese.TeseId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 13.05.2016
 */
public interface TeseRepository {
	
	// Assunto
	/**
	 * @param id
	 * @return
	 */
	Assunto findOneAssunto(AssuntoId id);
	
	/**
	 * @param descricao
	 * @return
	 */
	List<Assunto> findAssuntoByDescricao(String descricao);
	
	// Tese
	/**
	 * @param id
	 * @return
	 */
	Tese findOne(TeseId id);
	
	/**
	 * @param tipo
	 * @param numero
	 * @return
	 */
	List<Tese> findTeseByTipo(TipoTese tipo, Long numero);

	/**
	 * @param tesesIds
	 * @return
	 */
	List<Tese> findTesesByIds(List<TeseId> tesesIds);
	
}