package br.jus.stf.autuacao.originarios.infra;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.jus.stf.autuacao.originarios.domain.RemessaAdapter;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author viniciusk
 *
 */
@Component
public class RemessaAdapterImpl implements RemessaAdapter {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DiscoveryClient discoveryClient;

	/* (non-Javadoc)
	 * @see br.jus.stf.autuacao.originarios.domain.RemessaAdapter#consulta(br.jus.stf.core.shared.protocolo.ProtocoloId)
	 */
	@Override
	public RemessaDto consultar(ProtocoloId protocoloId) {
		
		return discoveryClient.getInstances("recebimento").stream()
				.findAny()
				.map(instance -> {
					URI servicesUri = instance.getUri();
					URI uri = UriComponentsBuilder.fromUri(servicesUri).path("/api/remessas/").queryParam("protocoloId", protocoloId.toLong()).build().toUri();
					return restTemplate.getForObject(uri, RemessaDto.class); 
				}).orElse(new RemessaDto());
	}

}
