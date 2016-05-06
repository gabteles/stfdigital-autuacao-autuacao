package br.jus.stf.autuacao.originarios.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.originarios.application.AutuacaoDeOriginariosApplicationService;
import br.jus.stf.autuacao.originarios.application.commands.AutuarProcessoCommand;
import br.jus.stf.autuacao.originarios.application.commands.RejeitarProcessoCommand;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@RestController
@RequestMapping("/api/processos")
public class ProcessoOriginarioRestResource {
    
    @Autowired
    private AutuacaoDeOriginariosApplicationService autuarProcessoCommandHandler;

    @RequestMapping(value = "autuacao", method = RequestMethod.POST)
    public void autuar(@RequestBody @Valid AutuarProcessoCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }
    
    @RequestMapping(value = "rejeicao", method = RequestMethod.POST)
    public void rejeitar(@RequestBody @Valid RejeitarProcessoCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Processo Inválido: " + binding.getAllErrors());
        }
        
        autuarProcessoCommandHandler.handle(command);
    }

}
