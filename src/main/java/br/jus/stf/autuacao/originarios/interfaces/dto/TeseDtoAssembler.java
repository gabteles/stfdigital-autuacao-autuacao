package br.jus.stf.autuacao.originarios.interfaces.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.controletese.Tese;
import br.jus.stf.autuacao.interfaces.dto.AssuntoDto;
import br.jus.stf.autuacao.interfaces.dto.AssuntoDtoAssembler;

@Component
public class TeseDtoAssembler {
	
	@Autowired
	private AssuntoDtoAssembler assuntoDtoAssembler;
	
	public TeseDto toDto(Tese tese) {
		Validate.notNull(tese);
		List<AssuntoDto> assuntos = tese.assuntos().stream().map(assunto -> assuntoDtoAssembler.toDto(assunto, 0)).collect(Collectors.toList()); 
		
		return new TeseDto(tese.identity().toLong(), tese.descricao(), tese.numero(), assuntos, tese.tipo().name());
	}

}
