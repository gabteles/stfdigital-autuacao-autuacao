package br.jus.stf.autuacao.originarios.interfaces.dto;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.originarios.domain.model.controletese.Assunto;

/**
 * 
 * @author viniciusk
 *
 */
@Component
public class AssuntoDtoAssembler {
	public AssuntoDto toDto(Assunto assunto){
		Validate.notNull(assunto);
		return new AssuntoDto(assunto.identity().toString(), assunto.descricao());
	}
}
