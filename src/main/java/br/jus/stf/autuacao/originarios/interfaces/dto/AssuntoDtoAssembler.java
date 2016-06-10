package br.jus.stf.autuacao.originarios.interfaces.dto;

import java.util.Optional;

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
	public AssuntoDto toDto(Assunto assunto, int nivel){
		Validate.notNull(assunto);
		String codigoAssuntoPai = "";
		if (assunto.assuntoPai() != null){
			codigoAssuntoPai = assunto.assuntoPai().identity().toString();
		}
		
		return new AssuntoDto(assunto.identity().toString(), assunto.descricao(), nivel);
	}
}
