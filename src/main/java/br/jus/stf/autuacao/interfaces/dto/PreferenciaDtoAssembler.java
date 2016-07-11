package br.jus.stf.autuacao.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.preferencia.Preferencia;

/**
 * @author Lucas.rodrigues
 * 
 * @since 1.0.0
 * @since 21.07.2015
 */
@Component
public class PreferenciaDtoAssembler {
	
	public PreferenciaDto toDto(Preferencia preferencia) {
		Validate.notNull(preferencia);
		return new PreferenciaDto(preferencia.identity().toLong(), preferencia.nome());
	}
}
