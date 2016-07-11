package br.jus.stf.autuacao.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.domain.model.ProcessoRepository;
import br.jus.stf.autuacao.domain.model.classe.ClasseRepository;
import br.jus.stf.autuacao.domain.model.preferencia.PreferenciaRepository;
import br.jus.stf.autuacao.interfaces.dto.ClasseDto;
import br.jus.stf.autuacao.interfaces.dto.ClasseDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.ParteDto;
import br.jus.stf.autuacao.interfaces.dto.ParteDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.PreferenciaDto;
import br.jus.stf.autuacao.interfaces.dto.PreferenciaDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDto;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDtoAssembler;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@RestController
@RequestMapping("/api/processos")
public class ProcessoRestResource {
    
    @Autowired
    private ClasseRepository classeRepository;
    
    @Autowired
    private ProcessoRepository processoRepository;
    
    @Autowired
    private PreferenciaRepository preferenciaRepository;
    
    @Autowired
    private ClasseDtoAssembler classeDtoAssembler;
    
    @Autowired
    private ProcessoDtoAssembler processoDtoAssembler;
    
    @Autowired
    private ParteDtoAssembler parteDtoAssembler;
    
    @Autowired
    private PreferenciaDtoAssembler preferenciaDtoAssembler;
    
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ProcessoDto consultarProcessoOriginario(@PathVariable("id") Long id){
		
		return Optional.ofNullable(processoRepository.findOne(new ProcessoId(id)))
				.map(processoDtoAssembler::toDto)
				.orElseThrow(() -> new IllegalArgumentException("Processo não encontrado"));
    }
	
	@RequestMapping(value = "/{id}/partes", method = RequestMethod.GET)
	public List<ParteDto> listarPartesProcessoOriginario(@PathVariable("id") Long id){
		return processoRepository.consultarPartes(id).stream().map(parteDtoAssembler::toDto).collect(Collectors.toList());
	}
    
	@RequestMapping(value="/classes", method = RequestMethod.GET)
    public List<ClasseDto> listarClasses(){
    	return classeRepository.findAll().stream()
    			.map(classeDtoAssembler::toDto).collect(Collectors.toList());
    }
	
	@RequestMapping(value="/preferencias", method = RequestMethod.GET)
    public List<PreferenciaDto> listarPreferencias(){
    	return preferenciaRepository.findAll().stream()
    			.map(preferenciaDtoAssembler::toDto).collect(Collectors.toList());
    }

}
