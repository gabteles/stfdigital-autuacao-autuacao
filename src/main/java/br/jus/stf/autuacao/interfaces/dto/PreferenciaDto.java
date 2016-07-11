package br.jus.stf.autuacao.interfaces.dto;

/**
 * @author lucas.rodrigues
 *
 */
public class PreferenciaDto {

	private Long id;
	private String nome;
	
	public PreferenciaDto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}	
	
}
