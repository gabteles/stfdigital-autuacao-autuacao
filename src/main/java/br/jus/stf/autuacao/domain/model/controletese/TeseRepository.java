package br.jus.stf.autuacao.domain.model.controletese;

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
	
	/** Assunto **/
	public Assunto findOneAssunto(AssuntoId id);
	
	public List<Assunto> findAssuntoByDescricao(String descricao);
	
	/** Tese **/
	public Tese findOne(TeseId id);
	
	public List<Tese> findTeseByTipo(TipoTese tipo, Long numero);

	public List<Tese> findTesesByIds(List<TeseId> tesesIds);
	
}