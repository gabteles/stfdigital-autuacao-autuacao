package br.jus.stf.autuacao.infra.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.autuacao.infra.RemessaDto;

/**
 * Cliente REST para consultar de remessa.
 * 
 * @author Tomas.Godoi
 *
 */
@FeignClient(name = "recebimento")
public interface RemessaRestClient {

	@RequestMapping(method = RequestMethod.GET, value = "/api/remessas/{id}")
	RemessaDto remessa(@PathVariable("id") Long protocoloId);
	
}