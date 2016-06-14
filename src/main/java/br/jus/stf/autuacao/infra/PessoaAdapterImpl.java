package br.jus.stf.autuacao.infra;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.PessoaAdapter;
import br.jus.stf.autuacao.interfaces.dto.PessoaDto;

/**
 * Implementação do adaptador do contexto de identidades.
 * 
 * @author anderson.araujo
 * @since 31/05/2016
 */
@Repository
public class PessoaAdapterImpl implements PessoaAdapter {
	private static Long idPessoa = 0L;

	@Override
	public PessoaDto criarPessoa(String nome) {
		PessoaAdapterImpl.idPessoa++;
		return new PessoaDto(PessoaAdapterImpl.idPessoa, nome);
	}

	@Override
	public Set<PessoaDto> criarPessoas(Set<String> nomes) {
		Set<PessoaDto> pessoas = new HashSet<PessoaDto>();
		
		for(String nome : nomes){
			PessoaAdapterImpl.idPessoa++;
			pessoas.add(new PessoaDto(PessoaAdapterImpl.idPessoa, nome));
		}
		
		return pessoas;
	}
	
	
}
