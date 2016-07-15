package br.jus.stf.autuacao.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import br.jus.stf.core.identificadores.interfaces.IdentificadorResource;

/**
 * @author lucas.rodrigues
 *
 */
@Configuration
public class RmiFactoryConfiguration {

	private static String IDENTIFICADOR_SERVICE_URL = "rmi://services/IdentificadorResource";
	
	@Bean
	public RmiProxyFactoryBean numeroProcessoResource() {
		RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
		factory.setServiceInterface(IdentificadorResource.class);
		factory.setServiceUrl(IDENTIFICADOR_SERVICE_URL);
		factory.setRefreshStubOnConnectFailure(true);
		factory.setLookupStubOnStartup(false);
		return factory;
	}
	
}
