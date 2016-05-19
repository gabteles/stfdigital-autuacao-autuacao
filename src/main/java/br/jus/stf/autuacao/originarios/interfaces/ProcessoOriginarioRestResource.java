package br.jus.stf.autuacao.originarios.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.originarios.application.AutuacaoDeOriginariosApplicationService;
import br.jus.stf.autuacao.originarios.application.commands.AutuarProcessoCommand;
import br.jus.stf.autuacao.originarios.application.commands.RejeitarProcessoCommand;
import br.jus.stf.autuacao.originarios.domain.ParteAdapter;
import br.jus.stf.autuacao.originarios.domain.RemessaAdapter;
import br.jus.stf.autuacao.originarios.domain.model.ProcessoOriginarioRepository;
import br.jus.stf.autuacao.originarios.domain.model.classe.ClasseRepository;
import br.jus.stf.autuacao.originarios.infra.ParteDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.ClasseDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.ClasseDtoAssembler;
import br.jus.stf.autuacao.originarios.interfaces.dto.ParteDtoAssembler;
import br.jus.stf.autuacao.originarios.interfaces.dto.ProcessoDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.ProcessoDtoAssembler;
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

    @RequestMapping(method = RequestMethod.POST)
    public void autuar(@RequestBody @Valid AutuarProcessoCommand command, BindingResult binding) {
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
		ProcessoId processoId = new ProcessoId(id);
		return Optional.ofNullable(processoOriginarioRepository.findOne(processoId))
				.map(processo -> processoDtoAssembler.toDto(processoId.toLong(), remessaAdapter.consultar(processo.protocoloId()), 
						parteAdapter.consultar(processo.protocoloId())))
				.orElseThrow(IllegalArgumentException::new);
    }
	
	@RequestMapping(value = "/parte/{processoId}", method = RequestMethod.GET)
	public List<ParteDto> listarPartes(@PathVariable("processoId") Long id){
		return processoOriginarioRepository.consultarPartes(id).stream().map(parte -> parteDtoAssembler.toDto(parte)).collect(Collectors.toList());
	}

}
