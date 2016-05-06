package br.jus.stf.autuacao.originarios.application;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.HashSet;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.originarios.application.commands.AutuarProcessoCommand;
import br.jus.stf.autuacao.originarios.application.commands.IniciarAutuacaoCommand;
import br.jus.stf.autuacao.originarios.application.commands.RejeitarProcessoCommand;
import br.jus.stf.autuacao.originarios.domain.NumeroProcessoAdapter;
import br.jus.stf.autuacao.originarios.domain.ProcessoFactory;
import br.jus.stf.autuacao.originarios.domain.StatusAdapter;
import br.jus.stf.autuacao.originarios.domain.model.Autuador;
import br.jus.stf.autuacao.originarios.domain.model.Parte;
import br.jus.stf.autuacao.originarios.domain.model.Processo;
import br.jus.stf.autuacao.originarios.domain.model.ProcessoOriginarioRepository;
import br.jus.stf.autuacao.originarios.domain.model.Status;
import br.jus.stf.autuacao.originarios.domain.model.classe.ClasseOriginaria;
import br.jus.stf.autuacao.originarios.domain.model.classe.ClasseOriginariaRepository;
import br.jus.stf.autuacao.originarios.infra.RabbitConfiguration;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.eventos.ProcessoAutuado;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.processo.Identificacao;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.01.2016
 */
@Component
public class AutuacaoDeOriginariosApplicationService {
    
    @Autowired
    private ProcessoOriginarioRepository processoRepository;
    
    @Autowired
    private ClasseOriginariaRepository classeRepository;
    
    @Autowired
    private NumeroProcessoAdapter numeroProcessoAdapter;
    
    @Autowired
    private ProcessoFactory processoFactory;
    
    @Autowired
    private StatusAdapter statusAdapter;

    @Autowired
	private RabbitTemplate rabbitTemplate;
    
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(IniciarAutuacaoCommand command) {
        ProcessoId processoId = processoRepository.nextProcessoId();
        Status status = statusAdapter.nextStatus(processoId);
        ClasseOriginaria classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
        Processo processo = processoFactory.novoProcesso(processoId, command.getProtocoloId(), classe, status);
        
        processoRepository.save(processo);
        
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_REGISTRADO_QUEUE, new ProcessoRegistrado(command.getProtocoloId().toLong(), processoId.toString()));
    }

    @Transactional
    public void handle(AutuarProcessoCommand command) {
        Processo processo = processoRepository.findOne(command.getProcessoId());
        Status status = statusAdapter.nextStatus(processo.identity(), "DISTRIBUIR");
        Identificacao numero = numeroProcessoAdapter.novoNumeroProcesso(command.getClasseId());
        ClasseOriginaria classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
        //TODO: Alterar para pegar dados do autuador pelo usuário da sessão.
        Autuador autuador = new Autuador("USUARIO_FALSO", new PessoaId(1L));
        //TODO: Alterar para reutilizar pessoas.
        Set<Parte> partes = new HashSet<>(0);
        command.getPoloAtivo().forEach(parteDto -> partes.add(new Parte(parteDto.getApresentacao(), Polo.ATIVO, parteDto.getPessoa())));
        command.getPoloPassivo().forEach(parteDto -> partes.add(new Parte(parteDto.getApresentacao(), Polo.PASSIVO, parteDto.getPessoa())));
        
        processo.autuar(classe, partes, autuador, status);
        
        processoRepository.save(processo);
        
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_AUTUADO_QUEUE, new ProcessoAutuado(processo.identity().toString(), numero.toString()));
    }
    
    @Transactional
    public void handle(RejeitarProcessoCommand command) {
        Processo processo = processoRepository.findOne(command.getProcessoId());
        Status status = statusAdapter.nextStatus(processo.identity(), "REJEITAR");

        processo.rejeitar(command.getMotivo(), status);
        
        processoRepository.save(processo);
    }

}
