package br.jus.stf.autuacao.interfaces.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.MotivoInaptidao;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;

@Component
public class AnalisePressupostosFormaisDtoAssembler {
	
	@Autowired
	private MotivoInaptidaoDtoAssembler motivoInaptidaoDtoAssembler;
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;
	
	public AnalisePressupostosFormaisDto toDto(ProcessoRecursal processo) {
		Validate.notNull(processo);
		
		ProcessoRecursalDto processoDto = (ProcessoRecursalDto) processoDtoAssembler.toDto(processo);
		return processo.analisePressupostoFormal()
				.map(analise -> new AnalisePressupostosFormaisDto(processoDto, analise.processoApto(),
															   analise.observacao(),
															   toMotivoInaptidaoDto(analise.motivosInaptidao())))
				.orElse(new AnalisePressupostosFormaisDto(processoDto, false, null, Collections.emptyList()));
	}
	
	private List<MotivoInaptidaoDto> toMotivoInaptidaoDto(Set<MotivoInaptidao> motivosInaptidao) {
		return motivosInaptidao.stream()
				.map(motivoInaptidaoDtoAssembler::toDto)
				.collect(Collectors.toList());
	}

}
