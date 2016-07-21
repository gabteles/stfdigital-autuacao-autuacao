package br.jus.stf.autuacao.infra.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.autuacao.interfaces.dto.ParteDto;

/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 21.07.2016
 */
@FeignClient(name = "peticionamento")
public interface ParteRestClient {

	@RequestMapping(method = RequestMethod.GET, value = "/api/peticoes/{id}/envolvidos")
	List<ParteDto> envolvidos(@PathVariable("id") Long protocoloId);
	
}
