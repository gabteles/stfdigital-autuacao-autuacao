package br.jus.stf.autuacao.interfaces.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.controletese.Tese;

@Component
public class AnaliseRepercussaoGeralDtoAssembler {
	
	@Autowired
	private TeseDtoAssembler teseDtoAssembler;
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;
	
	public AnaliseRepercussaoGeralDto toDto(ProcessoRecursal processo) {
		Validate.notNull(processo);
		
		ProcessoRecursalDto processoDto = (ProcessoRecursalDto) processoDtoAssembler.toDto(processo);
		return processo.analiseRepercussaoGeral()
				.map(analise -> new AnaliseRepercussaoGeralDto(processoDto, analise.temTeseRepercussaoGeral(),
															   analise.observacao(), toTeseDto(analise.teses())))
				.orElse(new AnaliseRepercussaoGeralDto(processoDto, false, null, Collections.emptyList()));
	}
	
	private List<TeseDto> toTeseDto(Set<Tese> teses) {
		return teses.stream()
				.map(teseDtoAssembler::toDto)
				.collect(Collectors.toList());
	}

}
