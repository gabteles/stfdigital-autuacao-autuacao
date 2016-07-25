package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.RemessaAdapter;
import br.jus.stf.autuacao.domain.model.AnaliseRepercussaoGeral;
import br.jus.stf.autuacao.domain.model.Parte;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoOriginario;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.controletese.Assunto;
import br.jus.stf.autuacao.infra.RemessaDto;

/**
 * Cria objetos ProcessoDto
 * 
 * @author anderson.araujo
 * @author lucas.rodrigues
 * @since 06/06/2016
 *
 */
@Component
public class ProcessoDtoAssembler {
	
	@Autowired
	private RemessaAdapter remessaAdapter;
	
	@Autowired
	private AssuntoDtoAssembler assuntoDtoAssembler;

	@Autowired
	private ParteDtoAssembler parteDtoAssembler;
	
	@Autowired
	private ClasseDtoAssembler classeDtoAssembler;
	
	@Autowired
	private TeseDtoAssembler teseDtoAssembler;

	public ProcessoDto toDto(Processo processo) {
		Validate.notNull(processo);
		
		ProcessoDto processoDto = null;
		
		RemessaDto remessa = processo.protocoloId().map(remessaAdapter::consultar).orElse(null);
	
		if (processo instanceof ProcessoRecursal){
			processoDto = toDto((ProcessoRecursal)processo, remessa);
		} else if (processo instanceof ProcessoOriginario) {
			processoDto = toDto((ProcessoOriginario)processo, remessa);
		}
		return processoDto;
	}

	private ProcessoDto toDto(ProcessoOriginario processo, RemessaDto remessa) {
		Long processoId = processo.identity().toLong();
		
		ProcessoOriginarioDto dto = new ProcessoOriginarioDto(processoId, remessa);
		dto.setMotivoRejeicao(processo.motivoRejeicao());
		setProcessoDtoCommons(processo, dto);
		return dto;
	}

	private ProcessoDto toDto(Processo processo, RemessaDto remessa) {
		ProcessoRecursal processoRecursal = (ProcessoRecursal) processo;
		Long processoId = processo.identity().toLong();
		
		ProcessoRecursalDto dto = new ProcessoRecursalDto(processoId, remessa);
		dto.setAssuntos(toAssuntoDto(processoRecursal.assuntos()));
		dto.setTeses(toTeseDto(processoRecursal.analiseRepercussaoGeral()));
		setProcessoDtoCommons(processo, dto);
		return dto;
	}
	

	private void setProcessoDtoCommons(Processo processo, ProcessoDto dto) {
		dto.setAutuador(processo.autuador().map(autuador -> autuador.login()).orElse(""));
		dto.setDataAutuacao(processo.dataAutuacao().orElse(null));
		dto.setClasse(processo.classe().map(classeDtoAssembler::toDto).orElse(null));
		dto.setMeioTramitacao(processo.meioTramitacao().descricao());
		dto.setNumero(processo.numero().orElse(null));
		dto.setIdentificacao(processo.identificacao());
		dto.setSigilo(processo.sigilo().descricao());
		dto.setStatus(processo.status().toString());
		dto.setPartes(toParteDto(processo.partes()));
	}

	private List<ParteDto> toParteDto(Set<Parte> partes) {
		return partes.stream()
				.map(parteDtoAssembler::toDto)
				.collect(Collectors.toList());
	}
	
	private List<AssuntoDto> toAssuntoDto(Set<Assunto> assuntos) {
		return assuntos.stream()
			.map(assuntoDtoAssembler::toDto)
			.collect(Collectors.toList());
	}
	
	private List<TeseDto> toTeseDto(Optional<AnaliseRepercussaoGeral> analiseRepercussaoGeral) {
		return analiseRepercussaoGeral.get().teses().stream()
				.map(teseDtoAssembler::toDto)
				.collect(Collectors.toList());
	}
}
