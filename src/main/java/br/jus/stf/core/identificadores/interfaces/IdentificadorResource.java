package br.jus.stf.core.identificadores.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface para o recurso de identificação
 * 
 * @author lucas.rodrigues
 *
 */
public interface IdentificadorResource extends Remote {

	/**
	 * Retorna um sequencial baseado na categoria informada,
	 * caso não seja informada será retornado um sequencial único
	 * @param categoria
	 * @return
	 */
	Long numero(String categoria) throws RemoteException;

}