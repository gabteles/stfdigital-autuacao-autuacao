package br.jus.stf.autuacao.infra;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.NumeroProcessoAdapter;
import br.jus.stf.autuacao.infra.client.NumeroProcessoRestClient;
import br.jus.stf.core.shared.processo.Identificacao;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.02.2016
 */
@Component
public class NumeroProcessoRestAdapter implements NumeroProcessoAdapter {
	
	@Autowired
    private NumeroProcessoRestClient numeroProcessoRestClient;
    
	@Override
	public Identificacao novoNumeroProcesso(String classe) {
		Validate.notBlank(classe, "A classe não pode ser nula ou vazia.");

		Long numero = numeroProcessoRestClient.identificador(classe);
		
		return new Identificacao(classe, numero);
	}
	
}
