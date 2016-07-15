package br.jus.stf.autuacao.infra;

import java.rmi.RemoteException;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.NumeroProcessoAdapter;
import br.jus.stf.core.identificadores.interfaces.IdentificadorResource;
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
    private IdentificadorResource identificadorResource;
    
	@Override
	public Identificacao novoNumeroProcesso(String classe) {
		Validate.notBlank(classe, "A classe não pode ser nula ou vazia.");
		
		Long numero = null;
		try {
			numero = identificadorResource.numero(classe);
		} catch (RemoteException e) {
			throw new RuntimeException("Não foi possível gerar um número para o processo.", e);
		}
		return new Identificacao(classe, numero);
	}
	
}
