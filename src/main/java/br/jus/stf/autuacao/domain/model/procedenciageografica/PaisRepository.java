package br.jus.stf.autuacao.domain.model.procedenciageografica;

import java.util.List;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 17.05.2016
 */
public interface PaisRepository {
	
	public Pais findOne(Long id);
	
	public List<Pais> findAll();

}