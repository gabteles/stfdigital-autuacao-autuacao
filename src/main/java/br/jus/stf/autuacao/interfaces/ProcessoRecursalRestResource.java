package br.jus.stf.autuacao.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.application.AutuacaoApplicationService;
import br.jus.stf.autuacao.application.commands.AnalisarPressupostosFormaisCommand;
import br.jus.stf.autuacao.application.commands.AnalisarRepercussaoGeralCommand;
import br.jus.stf.autuacao.application.commands.AutuarRecursalCommand;
import br.jus.stf.autuacao.application.commands.AutuarRecursalCriminalEleitoralCommand;
import br.jus.stf.autuacao.application.commands.EnviarProcessoRecursalCommand;
import br.jus.stf.autuacao.application.commands.RevisarPressupostosFormaisCommand;
import br.jus.stf.autuacao.application.commands.RevisarRepercussaoGeralCommand;
import br.jus.stf.autuacao.domain.model.ProcessoRepository;
import br.jus.stf.autuacao.domain.model.controletese.TeseRepository;
import br.jus.stf.autuacao.domain.model.controletese.TipoTese;
import br.jus.stf.autuacao.interfaces.dto.AssuntoDto;
import br.jus.stf.autuacao.interfaces.dto.AssuntoDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.MotivoInaptidaoDto;
import br.jus.stf.autuacao.interfaces.dto.MotivoInaptidaoDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.TeseDto;
import br.jus.stf.autuacao.interfaces.dto.TeseDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.TipoTeseDto;
import br.jus.stf.core.shared.controletese.AssuntoId;

/**
 * @author Lucas Rodrigues
 * 
 * @since 1.0.0
 * @since 05.07.2016
 */
@RestController
@RequestMapping("/api/processos/recursal")
public class ProcessoRecursalRestResource {
    
    @Autowired
    private AutuacaoApplicationService autuarProcessoCommandHandler;
    
    @Autowired
    private ProcessoRepository processoRepository;
    
    @Autowired
    private TeseRepository teseRepository;
    
    @Autowired
    private MotivoInaptidaoDtoAssembler motivoInaptidaoDtoAssembler;
    
    @Autowired
    private AssuntoDtoAssembler assuntoDtoAssembler;
    
    @Autowired
    private TeseDtoAssembler teseDtoAssembler;
    
	@RequestMapping(value = "/envio", method = RequestMethod.POST)
    public void enviarRecursal(@RequestBody @Valid EnviarProcessoRecursalCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/autuacao", method = RequestMethod.POST)
    public void autuarRecursal(@RequestBody @Valid AutuarRecursalCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/autuacao-criminal-eleitoral", method = RequestMethod.POST)
    public void autuarRecursalCriminalEleitoral(@RequestBody @Valid AutuarRecursalCriminalEleitoralCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/analise-pressupostos-formais", method = RequestMethod.POST)
    public void analisarPressupostosFormais(@RequestBody @Valid AnalisarPressupostosFormaisCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/revisao-pressupostos-formais", method = RequestMethod.POST)
    public void revisarPressupostosFormais(@RequestBody @Valid RevisarPressupostosFormaisCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/analise-repercussao-geral", method = RequestMethod.POST)
    public void analisarRepercussaoGeral(@RequestBody @Valid AnalisarRepercussaoGeralCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/revisao-repercussao-geral", method = RequestMethod.POST)
    public void revisarRepercussaoGeral(@RequestBody @Valid RevisarRepercussaoGeralCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
	@RequestMapping(value="/motivos-inaptidao", method = RequestMethod.GET)
    public List<MotivoInaptidaoDto> listarMotivosInaptidao(){
    	return processoRepository.findAllMotivoInaptidao().stream()
    			.map(motivo -> motivoInaptidaoDtoAssembler.toDto(motivo)).collect(Collectors.toList());
    }
	
	@RequestMapping(value="/tipos-tese", method = RequestMethod.GET)
    public List<TipoTeseDto> listarTiposTese(){
		return Arrays.asList(TipoTese.values()).stream().map(tipo -> new TipoTeseDto(tipo.name(), tipo.descricao()))
				.collect(Collectors.toList());
    }
	
	@RequestMapping(value="/teses", method = RequestMethod.GET)
    public List<TeseDto> listarTeses(@RequestParam("tipo")String tipo, @RequestParam("numero") Long numero){
		TipoTese tipoTese = TipoTese.valueOf(tipo.toUpperCase());
    	return teseRepository.findTeseByTipo(tipoTese, numero).stream()
    			.map(tese -> teseDtoAssembler.toDto(tese)).collect(Collectors.toList());
    }
	
	@RequestMapping(value="/assuntos", method = RequestMethod.GET)
	public List<AssuntoDto> listarAssuntos(@RequestParam("termo")String termo){
		List<AssuntoDto> assuntosDto = new ArrayList<AssuntoDto>();
		
		if(NumberUtils.isNumber(termo)){
			Optional.ofNullable(teseRepository.findOneAssunto(new AssuntoId(termo)))
				.ifPresent(assunto -> assuntosDto.add((assuntoDtoAssembler.toDto(assunto))));
		} else {
			assuntosDto.addAll(
				assuntoDtoAssembler.toDto(teseRepository.findAssuntoByDescricao(termo.toUpperCase())));
		}
		return assuntosDto;
	}

}
