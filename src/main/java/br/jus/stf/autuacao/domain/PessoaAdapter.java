package br.jus.stf.autuacao.domain;

import java.util.Set;

import br.jus.stf.autuacao.interfaces.dto.PessoaDto;

/**
 * Realiza operações relacioandas a pessoas.
 *  
 * @author anderson.araujo
 * @since 31/05/2016
 *
 */
public interface PessoaAdapter {
	/**
	 * Cria uma pessoa
	 * @param nome Nome da pessoa.
	 * @return Dados da pessoa criada.
	 */
	public PessoaDto criarPessoa(String nome);
	
	/**
	 * Cria várias pessoas.
	 * @param nome Nome das pessoas.
	 * @return Lista de pessoas criadas.
	 */
	public Set<PessoaDto> criarPessoas(Set<String> nome);
}
