package br.jus.stf.autuacao.originarios.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.originarios.domain.model.parte.ParteRepository;
import br.jus.stf.autuacao.originarios.infra.ParteDto;
import br.jus.stf.autuacao.originarios.interfaces.dto.ParteDtoAssembler;

/**
 * Serviço rest de consulta das partes
 * @author viniciusk
 *
 */
@RestController
@RequestMapping("/api/partes")
public class ParteRestResource {
	
	@Autowired
	private ParteRepository parteRepository;
	
	@Autowired
	private ParteDtoAssembler parteDtoAssembler;
	
	@RequestMapping(value = "/{processoId}", method = RequestMethod.GET)
	public List<ParteDto> listar(@PathVariable("processoId") Long id){
		return parteRepository.consultar(id).stream().map(parte -> parteDtoAssembler.toDto(parte)).collect(Collectors.toList());
	}

}
