package br.jus.stf.autuacao.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.application.AutuacaoApplicationService;
import br.jus.stf.autuacao.application.commands.AutuarOriginarioCommand;

/**
 * @author Lucas Rodrigues
 * 
 * @since 1.0.0
 * @since 05.07.2016
 */
@RestController
@RequestMapping("/api/processos/originario")
public class ProcessoOriginarioRestResource {
    
    @Autowired
    private AutuacaoApplicationService autuarProcessoCommandHandler;

    /**
     * @param command
     * @param binding
     */
    @RequestMapping(value = "/autuacao", method = RequestMethod.POST)
    public void autuarProcessoOriginario(@RequestBody @Valid AutuarOriginarioCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }

}
