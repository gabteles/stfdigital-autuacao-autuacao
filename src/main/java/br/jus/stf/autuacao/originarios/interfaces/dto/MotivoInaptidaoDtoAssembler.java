package br.jus.stf.autuacao.originarios.interfaces.dto;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.originarios.domain.model.MotivoInaptidao;

/**
 * 
 * @author viniciusk
 *
 */

@Component
public class MotivoInaptidaoDtoAssembler {
	
	public MotivoInaptidaoDto toDto(MotivoInaptidao motivo){
		Validate.notNull(motivo);
		return new MotivoInaptidaoDto(motivo.identity(), motivo.descricao());
	}

}
