package br.jus.stf.autuacao.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.application.AutuacaoApplicationService;
import br.jus.stf.autuacao.application.commands.AnalisarPressupostosCommand;
import br.jus.stf.autuacao.application.commands.AutuarProcessoCommand;
import br.jus.stf.autuacao.application.commands.AutuarProcessoCriminalCommand;
import br.jus.stf.autuacao.application.commands.AutuarProcessoRecursalCommand;
import br.jus.stf.autuacao.application.commands.RejeitarProcessoCommand;
import br.jus.stf.autuacao.application.commands.RevisarAnalisePressupostosCommand;
import br.jus.stf.autuacao.domain.ParteAdapter;
import br.jus.stf.autuacao.domain.RemessaAdapter;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoOriginarioRepository;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.classe.ClasseRepository;
import br.jus.stf.autuacao.domain.model.controletese.TeseRepository;
import br.jus.stf.autuacao.domain.model.controletese.TipoTese;
import br.jus.stf.autuacao.infra.RemessaDto;
import br.jus.stf.autuacao.interfaces.dto.AssuntoDto;
import br.jus.stf.autuacao.interfaces.dto.AssuntoDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.ClasseDto;
import br.jus.stf.autuacao.interfaces.dto.ClasseDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.MotivoInaptidaoDto;
import br.jus.stf.autuacao.interfaces.dto.MotivoInaptidaoDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.ParteDto;
import br.jus.stf.autuacao.interfaces.dto.ParteDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDto;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDtoAssembler;
import br.jus.stf.autuacao.originarios.interfaces.dto.TeseDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.TeseDtoAssembler;
import br.jus.stf.autuacao.originarios.interfaces.dto.TipoTeseDto;
import br.jus.stf.core.shared.controletese.AssuntoId;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@RestController
@RequestMapping("/api/processos")
public class ProcessoOriginarioRestResource {
    
    @Autowired
    private AutuacaoApplicationService autuarProcessoCommandHandler;
    
    @Autowired
    private ClasseRepository classeOriginariaRepository;
    
    @Autowired
    private ProcessoOriginarioRepository processoOriginarioRepository;
    
    @Autowired
    private TeseRepository teseRepository;
    
    @Autowired
    private ClasseDtoAssembler classeDtoAssembler;
    
    @Autowired
    private RemessaAdapter remessaAdapter;
    
    @Autowired 
    private ParteAdapter parteAdapter;
    
    @Autowired
    private ProcessoDtoAssembler processoDtoAssembler;
    
    @Autowired
    private ParteDtoAssembler parteDtoAssembler;
    
    @Autowired
    private MotivoInaptidaoDtoAssembler motivoInaptidaoDtoAssembler;
    
    @Autowired
    private AssuntoDtoAssembler assuntoDtoAssembler;
    
    @Autowired
    private TeseDtoAssembler teseDtoAssembler;

    @RequestMapping(value = "/autuacao", method = RequestMethod.POST)
    public void autuar(@RequestBody @Valid AutuarProcessoCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/autuacao/recursal", method = RequestMethod.POST)
    public void autuarProcessoRecursal(@RequestBody @Valid AutuarProcessoRecursalCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/autuacao/criminal", method = RequestMethod.POST)
    public void autuarProcessoCriminalEleitoral(@RequestBody @Valid AutuarProcessoCriminalCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/analise-pressupostos", method = RequestMethod.POST)
    public void analisarPressupostos(@RequestBody @Valid AnalisarPressupostosCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/revisao-analise-pressupostos", method = RequestMethod.POST)
    public void revisarAnalisePressupostos(@RequestBody @Valid RevisarAnalisePressupostosCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "/rejeicao", method = RequestMethod.POST)
    public void rejeitar(@RequestBody @Valid RejeitarProcessoCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
	@RequestMapping(value="/classe", method = RequestMethod.GET)
    public List<ClasseDto> listar(){
    	return classeOriginariaRepository.findAll().stream()
    			.map(classe -> classeDtoAssembler.toDto(classe)).collect(Collectors.toList());
    }
	
	@RequestMapping(value="/processo/{processoId}", method = RequestMethod.GET)
    public ProcessoDto consultar(@PathVariable("processoId") Long id){
		Processo processo = processoOriginarioRepository.findOne(new ProcessoId(id));
		
		if (processo == null){
			throw new IllegalArgumentException("Processo não encontrado");
		}
		
		List<ParteDto> partes = parteAdapter.consultar(processo.protocoloId());
		RemessaDto remessa = remessaAdapter.consultar(processo.protocoloId());
		List<MotivoInaptidaoDto> motivosInaptidao = new ArrayList<MotivoInaptidaoDto>();
		
		if (processo instanceof ProcessoRecursal){
			ProcessoRecursal processoRecursal = (ProcessoRecursal) processo;
			processoRecursal.motivosInaptidao().forEach(motivo -> new MotivoInaptidaoDto(motivo.identity(), motivo.descricao()));
		}
		
		return processoDtoAssembler.toDto(processo.identity().toLong(), remessa, partes, motivosInaptidao);
    }
	
	@RequestMapping(value = "/{id}/parte", method = RequestMethod.GET)
	public List<ParteDto> listarPartes(@PathVariable("id") Long id){
		return processoOriginarioRepository.consultarPartes(id).stream().map(parte -> parteDtoAssembler.toDto(parte)).collect(Collectors.toList());
	}
	
	@RequestMapping(value="/motivoinaptidao", method = RequestMethod.GET)
    public List<MotivoInaptidaoDto> listarMotivos(){
    	return processoOriginarioRepository.findAllMotivoInaptidao().stream()
    			.map(motivo -> motivoInaptidaoDtoAssembler.toDto(motivo)).collect(Collectors.toList());
    }
	
	@RequestMapping(value="/tipotese", method = RequestMethod.GET)
    public List<TipoTeseDto> listarTiposTese(){
		List<TipoTeseDto> listaTipo = new ArrayList<>();
		listaTipo.add(new TipoTeseDto("CONTROVERSIA", TipoTese.CONTROVERSIA.descricao()));
		listaTipo.add(new TipoTeseDto("PRE_TEMA", TipoTese.PRE_TEMA.descricao()));
		listaTipo.add(new TipoTeseDto("REPERCUSSAO_GERAL", TipoTese.REPERCUSSAO_GERAL.descricao()));
    	return listaTipo;
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
			Optional.ofNullable(processoOriginarioRepository.findOneAssunto(new AssuntoId(termo)))
				.ifPresent(assunto -> assuntosDto.add((assuntoDtoAssembler.toDto(assunto))));
		} else {
			assuntosDto.addAll(
				assuntoDtoAssembler.toDto(processoOriginarioRepository.listarAssuntos(termo.toUpperCase())));
		}
		return assuntosDto;
	}

}
