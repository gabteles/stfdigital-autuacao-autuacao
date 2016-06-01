package br.jus.stf.autuacao.originarios.domain.model.identidade;

import java.util.List;

import br.jus.stf.autuacao.originarios.domain.model.procedenciageografica.UnidadeFederacao;

/**
 * @author Rafael.Alencar
 */
public interface TribunalJuizoRepository {
	
	/**
	 * @param sequencial
	 * @return
	 */
	public TribunalJuizo findOne(Long sequencial);
	
	/**
	 * @return listas de tribunais e juízos
	 */
	public List<TribunalJuizo> findAll();
	
	/**
	 * @param uf
	 * @return lista de tribunais e juízos de uma unidade da federação
	 */
	public List<TribunalJuizo> findByUnidadeFederacao(UnidadeFederacao uf);

}