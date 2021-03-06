package br.jus.stf.autuacao.infra.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Lucas Rodrigues
 * 
 * @since 1.0.0
 * @since 12.07.2016
 */
@FunctionalInterface
@FeignClient(name = "processos")
public interface NumeroProcessoRestClient {

	/**
	 * @param categoria
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/identificadores")
	Long identificador(@RequestParam(value = "categoria") String categoria);
	
}