package br.jus.stf.autuacao.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.domain.ParteAdapter;
import br.jus.stf.autuacao.domain.RemessaAdapter;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.ProcessoRepository;
import br.jus.stf.autuacao.domain.model.classe.ClasseRepository;
import br.jus.stf.autuacao.infra.RemessaDto;
import br.jus.stf.autuacao.interfaces.dto.ClasseDto;
import br.jus.stf.autuacao.interfaces.dto.ClasseDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.MotivoInaptidaoDto;
import br.jus.stf.autuacao.interfaces.dto.ParteDto;
import br.jus.stf.autuacao.interfaces.dto.ParteDtoAssembler;
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
    private ClasseDtoAssembler classeDtoAssembler;
    
    @Autowired
    private RemessaAdapter remessaAdapter;
    
    @Autowired 
    private ParteAdapter parteAdapter;
    
    @Autowired
    private ProcessoDtoAssembler processoDtoAssembler;
    
    @Autowired
    private ParteDtoAssembler parteDtoAssembler;
    
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ProcessoDto consultarProcessoOriginario(@PathVariable("id") Long id){
		Processo processo = processoRepository.findOne(new ProcessoId(id));
		
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
	
	@RequestMapping(value = "/{id}/partes", method = RequestMethod.GET)
	public List<ParteDto> listarPartesProcessoOriginario(@PathVariable("id") Long id){
		return processoRepository.consultarPartes(id).stream().map(parte -> parteDtoAssembler.toDto(parte)).collect(Collectors.toList());
	}
    
	@RequestMapping(value="/classes", method = RequestMethod.GET)
    public List<ClasseDto> listar(){
    	return classeRepository.findAll().stream()
    			.map(classe -> classeDtoAssembler.toDto(classe)).collect(Collectors.toList());
    }

}
