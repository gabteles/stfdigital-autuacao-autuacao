package br.jus.stf.autuacao.application;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.application.commands.AnalisarPressupostosCommand;
import br.jus.stf.autuacao.application.commands.AnalisarRepercussaoGeralCommand;
import br.jus.stf.autuacao.application.commands.AutuarProcessoCommand;
import br.jus.stf.autuacao.application.commands.AutuarProcessoCriminalCommand;
import br.jus.stf.autuacao.application.commands.AutuarProcessoRecursalCommand;
import br.jus.stf.autuacao.application.commands.IniciarAutuacaoCommand;
import br.jus.stf.autuacao.application.commands.RejeitarProcessoCommand;
import br.jus.stf.autuacao.application.commands.RevisarAnalisePressupostosCommand;
import br.jus.stf.autuacao.application.commands.RevisarRepercussaoGeralCommand;
import br.jus.stf.autuacao.domain.NumeroProcessoAdapter;
import br.jus.stf.autuacao.domain.PessoaAdapter;
import br.jus.stf.autuacao.domain.ProcessoFactory;
import br.jus.stf.autuacao.domain.StatusAdapter;
import br.jus.stf.autuacao.domain.StatusRecursalAdapter;
import br.jus.stf.autuacao.domain.model.Autuador;
import br.jus.stf.autuacao.domain.model.MotivoInaptidao;
import br.jus.stf.autuacao.domain.model.Parte;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoOriginario;
import br.jus.stf.autuacao.domain.model.ProcessoOriginarioRepository;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.Status;
import br.jus.stf.autuacao.domain.model.classe.Classe;
import br.jus.stf.autuacao.domain.model.classe.ClasseRepository;
import br.jus.stf.autuacao.domain.model.controletese.Assunto;
import br.jus.stf.autuacao.domain.model.controletese.Tese;
import br.jus.stf.autuacao.domain.model.controletese.TeseRepository;
import br.jus.stf.autuacao.infra.RabbitConfiguration;
import br.jus.stf.core.framework.component.command.Command;
import br.jus.stf.core.framework.domaindrivendesign.ApplicationService;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.controletese.AssuntoId;
import br.jus.stf.core.shared.controletese.TeseId;
import br.jus.stf.core.shared.eventos.ProcessoAutuado;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.processo.Identificacao;
import br.jus.stf.core.shared.processo.MeioTramitacao;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.processo.ProcessoId;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.01.2016
 */
@ApplicationService
@Transactional
public class AutuacaoApplicationService {
    
    @Autowired
    private ProcessoOriginarioRepository processoRepository;
    
    @Autowired
    private ClasseRepository classeRepository;
    
    @Autowired
    private NumeroProcessoAdapter numeroProcessoAdapter;
    
    @Autowired
    private ProcessoFactory processoFactory;
    
    @Autowired
    @Qualifier("statusProcessoAdapter")
    private StatusAdapter statusAdapter;
    
    @Autowired
    @Qualifier("statusProcessoRecursalAdapter")
    private StatusRecursalAdapter statusRecursalAdapter;

    @Autowired
	private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private PessoaAdapter pessoaAdapter;
    
    @Autowired
    private TeseRepository teseRepository;
    
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(IniciarAutuacaoCommand command) {
        ProcessoId processoId = processoRepository.nextProcessoId();
        Classe classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
        TipoProcesso tipoProcesso = TipoProcesso.valueOf(command.getTipoProcesso());
		Status status = TipoProcesso.ORIGINARIO.equals(tipoProcesso) ? statusAdapter.nextStatus(processoId)
				: command.isCriminalEleitoral() ? statusRecursalAdapter.nextStatus(processoId, "CRIMINAL_ELEITORAL")
						: statusRecursalAdapter.nextStatus(processoId);
        MeioTramitacao meioTramitacao = MeioTramitacao.valueOf(command.getMeioTramitacao());
        Sigilo sigilo = Sigilo.valueOf(command.getSigilo());
        Processo processo = processoFactory.novoProcesso(processoId, new ProtocoloId(command.getProtocoloId()), classe, tipoProcesso, meioTramitacao, sigilo, status);
        
        processoRepository.save(processo);
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_REGISTRADO_QUEUE, new ProcessoRegistrado(command.getProtocoloId(), processoId.toString()));
    }

    @Command(description = "Autuação de Originários")
    public void handle(AutuarProcessoCommand command) {
        ProcessoOriginario processo = (ProcessoOriginario) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
        Status status = statusAdapter.nextStatus(processo.identity(), "DISTRIBUIR");
        Identificacao numero = numeroProcessoAdapter.novoNumeroProcesso(command.getClasseId());
        Classe classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
        //TODO: Alterar para pegar dados do autuador pelo usuário da sessão.
        Autuador autuador = new Autuador("USUARIO_FALSO", new PessoaId(1L));
        //TODO: Alterar para reutilizar pessoas.
        Set<Parte> partes = new HashSet<>(0);
        command.getPoloAtivo().forEach(parteDto -> partes.add(new Parte(parteDto.getApresentacao(), Polo.ATIVO, new PessoaId(parteDto.getPessoa()))));
        command.getPoloPassivo().forEach(parteDto -> partes.add(new Parte(parteDto.getApresentacao(), Polo.PASSIVO, new PessoaId(parteDto.getPessoa()))));
        
        processo.autuar(classe, numero.numero(), partes, autuador, status);
        processoRepository.save(processo);
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_AUTUADO_QUEUE, new ProcessoAutuado(processo.identity().toString(), numero.toString()));
    }
    
    @Command(description = "Autuação de Recursais")
    public void handle(AutuarProcessoRecursalCommand command) {
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
        Status status = statusRecursalAdapter.nextStatus(processo.identity());
        //TODO: Alterar para pegar dados do autuador pelo usuário da sessão.
        Autuador autuador = new Autuador("USUARIO_FALSO", new PessoaId(1L));
        Set<Parte> partes = new HashSet<Parte>();
        pessoaAdapter.criarPessoas(command.getPoloAtivo()).forEach(p1 -> partes.add(new Parte(p1.getApresentacao(), Polo.ATIVO, new PessoaId(p1.getPessoaId()))));
        pessoaAdapter.criarPessoas(command.getPoloPassivo()).forEach(p2 -> partes.add(new Parte(p2.getApresentacao(), Polo.PASSIVO, new PessoaId(p2.getPessoaId()))));

        Set<Assunto> assuntos = Optional.ofNullable(command.getAssuntos())
				.map(assuntosCmd -> assuntosCmd.stream()
						.map(assunto -> teseRepository.findOneAssunto(new AssuntoId(assunto)))
						.collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
        
        processo.autuar(assuntos, partes, autuador, status);
        processoRepository.save(processo);
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_AUTUADO_QUEUE, new ProcessoAutuado(processo.identity().toString(), processo.toString()));
    }
    
    @Command(description = "Autuação de Processos Criminais")
    public void handle(AutuarProcessoCriminalCommand command) {
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
        Status status = statusRecursalAdapter.nextStatus(processo.identity());
        //TODO: Alterar para pegar dados do autuador pelo usuário da sessão.
        Autuador autuador = new Autuador("USUARIO_FALSO", new PessoaId(1L));
        Set<Parte> partes = new HashSet<Parte>();
        pessoaAdapter.criarPessoas(command.getPoloAtivo()).forEach(p1 -> partes.add(new Parte(p1.getApresentacao(), Polo.ATIVO, new PessoaId(p1.getPessoaId()))));
        pessoaAdapter.criarPessoas(command.getPoloPassivo()).forEach(p2 -> partes.add(new Parte(p2.getApresentacao(), Polo.PASSIVO, new PessoaId(p2.getPessoaId()))));

		Set<Assunto> assuntos = Optional.ofNullable(command.getAssuntos())
				.map(assuntosCmd -> assuntosCmd.stream()
						.map(assunto -> teseRepository.findOneAssunto(new AssuntoId(assunto)))
						.collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
        
        processo.autuar(assuntos, partes, autuador, status);
        processoRepository.save(processo);
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_AUTUADO_QUEUE, new ProcessoAutuado(processo.identity().toString(), processo.toString()));
    }
    
    @Command(description = "Análise de Pressupostos Formais")
    public void handle(AnalisarPressupostosCommand command) {
		ProcessoRecursal processoRecursal = (ProcessoRecursal) processoRepository
				.findOne(new ProcessoId(command.getProcessoId()));
		Status status = command.isAnaliseApta() ? statusRecursalAdapter.nextStatus(processoRecursal.identity())
				: statusRecursalAdapter.nextStatus(processoRecursal.identity(), "REVISAR_ANALISE");
		Set<MotivoInaptidao> motivos = Optional.ofNullable(command.getMotivos())
				.map(motvs -> motvs.stream().map(motivo -> processoRepository.findOneMotivoInaptidao(motivo))
						.collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
        
		processoRecursal.analisarPressupostosFormais(command.isAnaliseApta(), command.getObservacao(), motivos, status);
        processoRepository.save(processoRecursal);
    }
    
    @Command(description = "Revisão de Análise de Pressupostos")
	public void handle(RevisarAnalisePressupostosCommand command) {
		ProcessoRecursal processoRecursal = (ProcessoRecursal) processoRepository
				.findOne(new ProcessoId(command.getProcessoId()));
		Status status = command.isAnaliseApta() ? statusRecursalAdapter.nextStatus(processoRecursal.identity())
				: statusRecursalAdapter.nextStatus(processoRecursal.identity(), "INAPTO");
		Set<MotivoInaptidao> motivos = Optional.ofNullable(command.getMotivos())
				.map(motvs -> motvs.stream().map(motivo -> processoRepository.findOneMotivoInaptidao(motivo))
						.collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
		
		processoRecursal.analisarPressupostosFormais(command.isAnaliseApta(), command.getObservacao(), motivos, status);
		processoRepository.save(processoRecursal);
	}
    
    @Command(description = "Análise da Repercussão Geral")
    public void handle(AnalisarRepercussaoGeralCommand command) {
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
		Status status = command.isRepercussaoGeral() ? statusRecursalAdapter.nextStatus(processo.identity(), "REVISAR_RG") : statusRecursalAdapter.nextStatus(processo.identity());
		Set<Assunto> assuntos = Optional.ofNullable(command.getAssuntos())
				.map(assuntosCmd -> assuntosCmd.stream()
						.map(assunto -> teseRepository.findOneAssunto(new AssuntoId(assunto)))
						.collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
		Set<Tese> teses = Optional.ofNullable(command.getTeses())
				.map(tesesCmd -> tesesCmd.stream()
						.map(tese -> teseRepository.findOne(new TeseId(tese))).collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
        
        processo.analisarRepercussaoGeral(command.getObservacao(), teses, assuntos, status);
        processoRepository.save(processo);
    }
    
    @Command(description = "Revisão da Repercussão Geral")
    public void handle(RevisarRepercussaoGeralCommand command) {
    	ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
    	Status status = statusRecursalAdapter.nextStatus(processo.identity());
    	Set<Assunto> assuntos = Optional.ofNullable(command.getAssuntos())
				.map(assuntosCmd -> assuntosCmd.stream()
						.map(assunto -> teseRepository.findOneAssunto(new AssuntoId(assunto)))
						.collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
		Set<Tese> teses = Optional.ofNullable(command.getTeses())
				.map(tesesCmd -> tesesCmd.stream()
						.map(tese -> teseRepository.findOne(new TeseId(tese))).collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
		
        processo.analisarRepercussaoGeral(command.getObservacao(), teses, assuntos, status);
        processoRepository.save(processo);
    }
    
    @Command(description = "Rejeitar Processo")
    public void handle(RejeitarProcessoCommand command) {
        ProcessoOriginario processo = (ProcessoOriginario) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
        Status status = statusAdapter.nextStatus(processo.identity(), "REJEITAR");

        processo.rejeitar(command.getMotivo(), status);
        processoRepository.save(processo);
    }

}
