package br.jus.stf.autuacao.originarios.interfaces.dto;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 29.04.2016
 */
public class AutuarParteCommand {
	
	private String apresentacao;
	
	private Long pessoa;
	
	public AutuarParteCommand() {
		
	}
	
	public AutuarParteCommand(String apresentacao, Long pessoa) {
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
