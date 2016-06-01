package br.jus.stf.autuacao.originarios.domain.model.documento;

import java.util.List;

import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 22.04.2016
 */
public interface TipoPecaRepository {

	List<TipoPeca> findAll();
	
	TipoPeca findOne(TipoDocumentoId id);

}
