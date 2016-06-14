package br.jus.stf.autuacao.interfaces.dto;

/**
 * Usado para receber os dados orindos do contexto de identidade (pessoas).
 * 
 * @author anderson.araujo
 * @since 31/05/2016
 *
 */
public class PessoaDto {
	private Long pessoaId;
	private String nome;
	
	public PessoaDto(Long pessoaId, String nome){
		this.pessoaId = pessoaId;
		this.nome = nome;
	}
	
	public Long getPessoaId(){
		return pessoaId;
	}
	
	public String getApresentacao(){
		return nome;
	}
}
