package br.jus.stf.autuacao.infra.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;

/**
 * Cliente REST para consultar de petição.
 * 
 * @author Rafael Alencar
 *
 */
@FeignClient(name = "peticionamento")
@FunctionalInterface
public interface PeticaoRestClient {

	/**
	 * @param protocoloId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/peticoes/{id}")
	PeticaoDto peticao(@PathVariable("id") Long protocoloId);
	
}