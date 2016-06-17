package br.jus.stf.autuacao.domain.model;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 12.02.2016
 */
public enum Status {
    
    AUTUACAO(),
    AUTUADO(),
    REJEITADO(),
    ANALISAR_PRESSUPOSTO(),
    REVISAR_PRESSUPOSTO(),
    ANALISAR_RG(),
    REVISAR_RG();

}
