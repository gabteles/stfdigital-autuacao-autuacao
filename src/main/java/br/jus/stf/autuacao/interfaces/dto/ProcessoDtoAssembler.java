package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.PeticaoAdapter;
import br.jus.stf.autuacao.domain.RemessaAdapter;
import br.jus.stf.autuacao.domain.model.AnaliseRepercussaoGeral;
import br.jus.stf.autuacao.domain.model.Parte;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoOriginario;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.suportejudicial.Assunto;

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
	
	@Autowired
	private PeticaoAdapter peticaoAdapter;

	public ProcessoDto toDto(Processo processo) {
		Validate.notNull(processo);
		
		ProcessoDto processoDto = null;
		RemessaDto remessaDto = null;
		PeticaoDto peticaoDto = null;
		Long protocoloId = null;
		String classeSugerida = null;
		
		switch(processo.meioTramitacao()) {
			case FISICO:
				RemessaDto remessa = processo.protocoloId().map(remessaAdapter::consultar).orElse(null);
				remessaDto = remessa;
				protocoloId = remessa.getProtocolo();
				classeSugerida = remessa.getClasseSugerida();
				break;
			case ELETRONICO:
				PeticaoDto peticao = processo.protocoloId().map(peticaoAdapter::consultar).orElse(null);
				peticaoDto = peticao;
				protocoloId = peticao.getProtocolo();
				classeSugerida = peticao.getClasseSugerida();
				break;
			default:
				throw new IllegalArgumentException(String.format("Meio de tramitação não localizado: %s.", processo.meioTramitacao()));
		}
	
		if (processo instanceof ProcessoRecursal){
			processoDto = toDto((ProcessoRecursal)processo, protocoloId, classeSugerida, remessaDto, peticaoDto);
		} else if (processo instanceof ProcessoOriginario) {
			processoDto = toDto((ProcessoOriginario)processo, protocoloId, classeSugerida, remessaDto, peticaoDto);
		}
		return processoDto;
	}

	private ProcessoDto toDto(ProcessoOriginario processo, Long protocoloId, String classeSugerida, RemessaDto remessaDto, PeticaoDto peticaoDto) {
		Long processoId = processo.identity().toLong();
		
		ProcessoOriginarioDto dto = new ProcessoOriginarioDto(processoId, protocoloId, classeSugerida);
		dto.setMotivoRejeicao(processo.motivoRejeicao());
		setProcessoDtoCommons(processo, dto);
		dto.setRemessa(remessaDto);
		dto.setPeticao(peticaoDto);
		return dto;
	}

	private ProcessoDto toDto(Processo processo, Long protocoloId, String classeSugerida, RemessaDto remessaDto, PeticaoDto peticaoDto) {
		ProcessoRecursal processoRecursal = (ProcessoRecursal) processo;
		Long processoId = processo.identity().toLong();
		
		ProcessoRecursalDto dto = new ProcessoRecursalDto(processoId, protocoloId, classeSugerida);
		dto.setAssuntos(toAssuntoDto(processoRecursal.assuntos()));
		dto.setTeses(toTeseDto(processoRecursal.analiseRepercussaoGeral()));
		dto.setRemessa(remessaDto);
		dto.setPeticao(peticaoDto);
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
