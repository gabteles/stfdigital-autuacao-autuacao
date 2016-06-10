package br.jus.stf.autuacao.originarios.interfaces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import br.jus.stf.autuacao.originarios.application.AutuacaoDeOriginariosApplicationService;
import br.jus.stf.autuacao.originarios.application.commands.AnalisarPressupostosCommand;
import br.jus.stf.autuacao.originarios.application.commands.AutuarProcessoCommand;
import br.jus.stf.autuacao.originarios.application.commands.AutuarProcessoCriminalCommand;
import br.jus.stf.autuacao.originarios.application.commands.AutuarProcessoRecursalCommand;
import br.jus.stf.autuacao.originarios.application.commands.RejeitarProcessoCommand;
import br.jus.stf.autuacao.originarios.application.commands.RevisarAnalisePressupostosCommand;
import br.jus.stf.autuacao.originarios.domain.ParteAdapter;
import br.jus.stf.autuacao.originarios.domain.RemessaAdapter;
import br.jus.stf.autuacao.originarios.domain.model.Processo;
import br.jus.stf.autuacao.originarios.domain.model.ProcessoOriginarioRepository;
import br.jus.stf.autuacao.originarios.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.originarios.domain.model.classe.ClasseRepository;
import br.jus.stf.autuacao.originarios.domain.model.controletese.Assunto;
import br.jus.stf.autuacao.originarios.interfaces.dto.AssuntoDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.AssuntoDtoAssembler;
import br.jus.stf.autuacao.originarios.infra.RemessaDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.ClasseDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.ClasseDtoAssembler;
import br.jus.stf.autuacao.originarios.interfaces.dto.MotivoInaptidaoDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.MotivoInaptidaoDtoAssembler;
import br.jus.stf.autuacao.originarios.interfaces.dto.ParteDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.ParteDtoAssembler;
import br.jus.stf.autuacao.originarios.interfaces.dto.ProcessoDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.ProcessoDtoAssembler;
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
    private AutuacaoDeOriginariosApplicationService autuarProcessoCommandHandler;
    
    @Autowired
    private ClasseRepository classeOriginariaRepository;
    
    @Autowired
    private ProcessoOriginarioRepository processoOriginarioRepository;
    
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
	
	@RequestMapping(value="/assuntos", method = RequestMethod.GET)
	public List<AssuntoDto> listarAssuntos(@RequestParam("termo")String termo){
		List<AssuntoDto> assuntosDto = new ArrayList<AssuntoDto>();
		List<AssuntoDto> assuntosFinais = new ArrayList<>();
		if(NumberUtils.isNumber(termo)){
			Assunto assunto = processoOriginarioRepository.findOneAssunto(new AssuntoId(termo));
			
			if (assunto != null){
				assuntosDto.add((assuntoDtoAssembler.toDto(assunto, 0)));					
			}
		}else{
			List<Assunto> assuntos = desnormalizar(processoOriginarioRepository.listarAssuntos(termo.toUpperCase()));
			List<AssuntoDto> assuntosRaizes = new ArrayList<>();
			Iterator<Assunto> iterator = assuntos.iterator();
			while (iterator.hasNext()){
				Assunto assunto = iterator.next();
				if (assunto.assuntoPai() == null){
					AssuntoDto assDto = new AssuntoDto(assunto.identity().toString(), assunto.descricao(), 0);
					assuntosRaizes.add(assDto);
					assuntosFinais.add(assDto);
					iterator.remove();
				}
			}
			identaAssuntos(assuntosFinais, assuntos, assuntosRaizes, 1);
		}
		
		return assuntosFinais;
	}
	
	private List<Assunto> desnormalizar(List<Assunto> lista) {
		Set<Assunto> assuntos = new HashSet<>();
		for (Assunto ass: lista) {
			assuntos.add(ass);
			desnormalizarItem(assuntos, ass);
		}
		return new ArrayList<>(assuntos);
	}

	private void desnormalizarItem(Set<Assunto> assuntos, Assunto ass) {
		if (ass.assuntoPai() != null) {
			assuntos.add(ass.assuntoPai());
			desnormalizarItem(assuntos, ass.assuntoPai());
		}
	}

	private void identaAssuntos (List<AssuntoDto> listaFinal, List<Assunto> assuntos, List<AssuntoDto> listaDto,  int nivel){
		if (assuntos.size() == 0){
			return;
		}
		List<AssuntoDto> proximaLista = new ArrayList<>();
		for (AssuntoDto assuntoDto : listaDto){
			Iterator<Assunto> iterator = assuntos.iterator();
			while(iterator.hasNext()){
				Assunto assunto = iterator.next();
				if (assunto.assuntoPai().identity().toString().equals(assuntoDto.getCodigo())){
					AssuntoDto assDto = new AssuntoDto(assunto.identity().toString(), assunto.descricao(), nivel);
					listaFinal.add(listaFinal.indexOf(assuntoDto) + 1, assDto);
					proximaLista.add(assDto);
					iterator.remove();
				}
			}
		}
		identaAssuntos(listaFinal, assuntos, proximaLista, ++nivel );
	}

}
