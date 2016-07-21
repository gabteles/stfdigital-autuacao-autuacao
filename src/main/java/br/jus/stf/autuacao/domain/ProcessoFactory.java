package br.jus.stf.autuacao.domain;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.Origem;
import br.jus.stf.autuacao.domain.model.Parte;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoOriginario;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.Status;
import br.jus.stf.autuacao.domain.model.classe.Classe;
import br.jus.stf.autuacao.domain.model.controletese.Assunto;
import br.jus.stf.autuacao.domain.model.preferencia.Preferencia;
import br.jus.stf.core.shared.processo.MeioTramitacao;
import br.jus.stf.core.shared.processo.ProcessoId;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
@Component
public class ProcessoFactory {

	/**
	 * @param processoId
	 * @param protocoloId
	 * @param classe
	 * @param numero
	 * @param tipoProcesso
	 * @param meioTramitacao
	 * @param sigilo
	 * @param status
	 * @return
	 */
	public Processo novoProcesso(ProcessoId processoId, ProtocoloId protocoloId, Classe classe, Long numero,
			TipoProcesso tipoProcesso, MeioTramitacao meioTramitacao, Sigilo sigilo, Status status) {
    	Processo processo;
        
		switch (tipoProcesso) {
		case ORIGINARIO:
			processo = new ProcessoOriginario(processoId, protocoloId, classe, meioTramitacao, sigilo, status);
			break;
		case RECURSAL:
			processo = new ProcessoRecursal(processoId, protocoloId, classe, numero, meioTramitacao, sigilo, status);
			break;
		default:
			throw new IllegalArgumentException(String.format("Tipo de processo não localizado: %s.", tipoProcesso));
		}
    	return processo;
    }
    
	/**
	 * @param processoId
	 * @param classe
	 * @param numero
	 * @param preferencias
	 * @param quantidadeRecurso
	 * @param sigilo
	 * @param origens
	 * @param assuntos
	 * @param partes
	 * @param status
	 * @return
	 */
	public ProcessoRecursal novoEnvio(ProcessoId processoId, Classe classe, Long numero, Set<Preferencia> preferencias,
			Integer quantidadeRecurso, Sigilo sigilo, Set<Origem> origens, Set<Assunto> assuntos, Set<Parte> partes,
			Status status) {
		ProcessoRecursal processo = new ProcessoRecursal(processoId, null, classe, numero, preferencias,
				MeioTramitacao.ELETRONICO, quantidadeRecurso, sigilo, status);
    	
    	processo.atribuirOrigens(origens);
    	processo.atribuirAssuntos(assuntos);
    	processo.atribuirPartes(partes);
    	return processo;
    }

}
