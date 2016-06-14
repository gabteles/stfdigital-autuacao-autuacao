package br.jus.stf.autuacao.interfaces.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.controletese.Assunto;

/**
 * 
 * @author viniciusk
 *
 */
@Component
public class AssuntoDtoAssembler {
	
	public AssuntoDto toDto(Assunto assunto){
		Validate.notNull(assunto);		
		return new AssuntoDto(assunto.identity().toString(), assunto.descricao(), 0);
	}
	
	public List<AssuntoDto> toDto(List<Assunto> assuntos){
		List<Assunto> assuntosDesnormalizados = desnormalizar(assuntos);
		List<AssuntoDto> assuntosFinais = new ArrayList<AssuntoDto>();
		List<AssuntoDto> assuntosRaizes = new ArrayList<AssuntoDto>();
		
		Iterator<Assunto> iterator = assuntosDesnormalizados.iterator();
		while (iterator.hasNext()){
			Assunto assunto = iterator.next();
			if (assunto.assuntoPai() == null){
				AssuntoDto assDto = new AssuntoDto(assunto.identity().toString(), assunto.descricao(), 0);
				assuntosRaizes.add(assDto);
				assuntosFinais.add(assDto);
				iterator.remove();
			}
		}
		identaAssuntos(assuntosFinais, assuntosDesnormalizados, assuntosRaizes, 1);
		return assuntosFinais;
	}
	
	private List<Assunto> desnormalizar(List<Assunto> lista) {
		Set<Assunto> assuntos = new HashSet<>();
		for (Assunto ass: lista) {
			assuntos.add(ass);
			desnormalizarItem(assuntos, ass);
		}
		return new ArrayList<>(assuntos);
	}

	private void desnormalizarItem(Set<Assunto> assuntos, Assunto ass) {
		if (ass.assuntoPai() != null) {
			assuntos.add(ass.assuntoPai());
			desnormalizarItem(assuntos, ass.assuntoPai());
		}
	}

	private void identaAssuntos (List<AssuntoDto> listaFinal, List<Assunto> assuntos, List<AssuntoDto> listaDto,  int nivel){
		if (assuntos.size() == 0){
			return;
		}
		List<AssuntoDto> proximaLista = new ArrayList<>();
		for (AssuntoDto assuntoDto : listaDto){
			Iterator<Assunto> iterator = assuntos.iterator();
			while(iterator.hasNext()){
				Assunto assunto = iterator.next();
				if (assunto.assuntoPai().identity().toString().equals(assuntoDto.getCodigo())){
					AssuntoDto assDto = new AssuntoDto(assunto.identity().toString(), assunto.descricao(), nivel);
					listaFinal.add(listaFinal.indexOf(assuntoDto) + 1, assDto);
					proximaLista.add(assDto);
					iterator.remove();
				}
			}
		}
		identaAssuntos(listaFinal, assuntos, proximaLista, ++nivel );
	}
}
