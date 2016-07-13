package br.jus.stf.autuacao.infra;

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
		
		IdentificacaoDto identificacao = numeroProcessoRestClient.identificador(classe);
    	
		return new Identificacao(identificacao.getCategoria(), identificacao.getNumero());
	}
	
}
