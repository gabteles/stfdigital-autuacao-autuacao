package br.jus.stf.autuacao.originarios.domain.model.procedenciageografica;

import java.util.List;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 17.05.2016
 */
public interface UnidadeFederacaoRepository {
	
	public UnidadeFederacao findOne(Long id);
	
	public List<UnidadeFederacao> findAll();
	
	public List<UnidadeFederacao> findByPais(Pais pais);

}