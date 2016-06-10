package br.jus.stf.autuacao.originarios.infra;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.jus.stf.autuacao.originarios.domain.ParteAdapter;
import br.jus.stf.autuacao.originarios.interfaces.dto.ParteDto;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

@Component
public class ParteAdapterImpl implements ParteAdapter {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;

	@Override
	public List<ParteDto> consultar(ProtocoloId protocoloId) {
		return discoveryClient.getInstances("gateway").stream()
				.findAny()
				.map(instance -> {
					URI servicesUri = instance.getUri();
					URI uri = UriComponentsBuilder.fromUri(servicesUri).path("/peticionamento/api/peticoes/{id}/envolvidos").queryParam("id", protocoloId.toLong()).build().toUri();
					return Arrays.asList(restTemplate.getForObject(uri, ParteDto[].class)); 
				}).orElseThrow(IllegalArgumentException::new);
	}
}
