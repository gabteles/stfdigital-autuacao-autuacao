package br.jus.stf.autuacao.application.commands;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 29.04.2016
 */
public class CadastrarParteCommand {
	
	private String apresentacao;
	
	private Long pessoa;
	
	public CadastrarParteCommand() {
		// Construtor default
	}
	
	/**
	 * @param apresentacao
	 * @param pessoa
	 */
	public CadastrarParteCommand(String apresentacao, Long pessoa) {
		this.apresentacao = apresentacao;
		this.pessoa = pessoa;
	}
	
	public String getApresentacao() {
		return apresentacao;
	}
	
	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}
	
	public Long getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Long pessoa) {
		this.pessoa = pessoa;
	}
	
}
