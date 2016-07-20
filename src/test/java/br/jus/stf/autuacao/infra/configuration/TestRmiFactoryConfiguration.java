package br.jus.stf.autuacao.infra.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.jus.stf.core.identificadores.interfaces.IdentificadorResource;

/**
 * @author Tomas.Godoi
 *
 */
@Configuration
@Profile("test")
public class TestRmiFactoryConfiguration {
	
	@Bean
	public IdentificadorResource numeroProcessoResource() {
		return Mockito.mock(IdentificadorResource.class);
	}
	
}