package br.jus.stf.autuacao.originarios.interfaces.dto;

import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 29.04.2016
 */
public class ParteDto {
	
	private String apresentacao;
	
	private PessoaId pessoa;
	
	public ParteDto() {
		
	}
	
	public ParteDto(String apresentacao, PessoaId pessoa) {
		this.apresentacao = apresentacao;
		this.pessoa = pessoa;
	}
	
	public String getApresentacao() {
		return apresentacao;
	}
	
	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}
	
	public PessoaId getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(PessoaId pessoa) {
		this.pessoa = pessoa;
	}
	
}
