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

import br.jus.stf.autuacao.application.commands.AnalisarPressupostosFormaisCommand;
import br.jus.stf.autuacao.application.commands.AnalisarRepercussaoGeralCommand;
import br.jus.stf.autuacao.application.commands.AutuarOriginarioCommand;
import br.jus.stf.autuacao.application.commands.AutuarRecursalCommand;
import br.jus.stf.autuacao.application.commands.AutuarRecursalCriminalEleitoralCommand;
import br.jus.stf.autuacao.application.commands.EnviarProcessoRecursalCommand;
import br.jus.stf.autuacao.application.commands.IniciarAutuacaoCommand;
import br.jus.stf.autuacao.application.commands.RevisarPressupostosFormaisCommand;
import br.jus.stf.autuacao.application.commands.RevisarRepercussaoGeralCommand;
import br.jus.stf.autuacao.domain.AutuadorAdapter;
import br.jus.stf.autuacao.domain.NumeroProcessoAdapter;
import br.jus.stf.autuacao.domain.PessoaAdapter;
import br.jus.stf.autuacao.domain.ProcessoFactory;
import br.jus.stf.autuacao.domain.StatusOriginarioAdapter;
import br.jus.stf.autuacao.domain.StatusRecursalAdapter;
import br.jus.stf.autuacao.domain.model.Autuador;
import br.jus.stf.autuacao.domain.model.MotivoInaptidao;
import br.jus.stf.autuacao.domain.model.Origem;
import br.jus.stf.autuacao.domain.model.Parte;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoOriginario;
import br.jus.stf.autuacao.domain.model.ProcessoRecursal;
import br.jus.stf.autuacao.domain.model.ProcessoRepository;
import br.jus.stf.autuacao.domain.model.Status;
import br.jus.stf.autuacao.domain.model.controletese.Assunto;
import br.jus.stf.autuacao.domain.model.controletese.Tese;
import br.jus.stf.autuacao.domain.model.controletese.TeseRepository;
import br.jus.stf.autuacao.domain.model.identidade.TribunalJuizo;
import br.jus.stf.autuacao.domain.model.identidade.TribunalJuizoRepository;
import br.jus.stf.autuacao.domain.model.procedenciageografica.UnidadeFederacao;
import br.jus.stf.autuacao.domain.model.procedenciageografica.UnidadeFederacaoRepository;
import br.jus.stf.autuacao.domain.model.suportejudicial.Classe;
import br.jus.stf.autuacao.domain.model.suportejudicial.ClasseRepository;
import br.jus.stf.autuacao.domain.model.suportejudicial.Preferencia;
import br.jus.stf.autuacao.domain.model.suportejudicial.PreferenciaRepository;
import br.jus.stf.autuacao.infra.RabbitConfiguration;
import br.jus.stf.core.framework.component.command.Command;
import br.jus.stf.core.framework.domaindrivendesign.ApplicationService;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.controletese.AssuntoId;
import br.jus.stf.core.shared.controletese.TeseId;
import br.jus.stf.core.shared.eventos.ProcessoAutuado;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.preferencia.PreferenciaId;
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
    private ProcessoRepository processoRepository;
    
    @Autowired
    private ClasseRepository classeRepository;
    
    @Autowired
    private NumeroProcessoAdapter numeroProcessoAdapter;
    
    @Autowired
    private ProcessoFactory processoFactory;
    
    @Autowired
    @Qualifier("statusProcessoOriginarioAdapter")
    private StatusOriginarioAdapter statusOriginarioAdapter;
    
    @Autowired
    @Qualifier("statusProcessoRecursalAdapter")
    private StatusRecursalAdapter statusRecursalAdapter;

    @Autowired
	private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private PessoaAdapter pessoaAdapter;
    
    @Autowired
    private TeseRepository teseRepository;
    
    @Autowired
    private PreferenciaRepository preferenciaRepository;
    
    @Autowired
    private UnidadeFederacaoRepository unidadeFederacaoRepository;
    
    @Autowired
    private TribunalJuizoRepository tribunalJuizoRepository;
    
    @Autowired
    private AutuadorAdapter autuadorAdapter;
    
    /**
     * @param command
     */
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(IniciarAutuacaoCommand command) {
        ProcessoId processoId = processoRepository.nextProcessoId();
        Classe classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
        TipoProcesso tipoProcesso = TipoProcesso.valueOf(command.getTipoProcesso());
		Status status = TipoProcesso.ORIGINARIO.equals(tipoProcesso) ? statusOriginarioAdapter.nextStatus(processoId)
				: command.isCriminalEleitoral() ? statusRecursalAdapter.nextStatus(processoId, "CRIMINAL_ELEITORAL")
						: statusRecursalAdapter.nextStatus(processoId);
        MeioTramitacao meioTramitacao = MeioTramitacao.valueOf(command.getMeioTramitacao());
        Sigilo sigilo = Sigilo.valueOf(command.getSigilo());
        Long numero = TipoProcesso.ORIGINARIO.equals(tipoProcesso) ? null : numeroProcessoAdapter.novoNumeroProcesso(command.getClasseId()).numero();
		Processo processo = processoFactory.novoProcesso(processoId, new ProtocoloId(command.getProtocoloId()), classe,
				numero, tipoProcesso, meioTramitacao, sigilo, status);
        
        processoRepository.save(processo);
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_REGISTRADO_QUEUE, new ProcessoRegistrado(command.getProtocoloId(), processoId.toString()));
    }

    /**
     * @param command
     */
    @Command(description = "Autuação de Originários")
    public void handle(AutuarOriginarioCommand command) {
        ProcessoOriginario processo = (ProcessoOriginario) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
        Identificacao numero = null;
        
        if (command.isValida()) {
        	numero = numeroProcessoAdapter.novoNumeroProcesso(command.getClasseId());
        	Autuador autuador = autuadorAdapter.autuador();
        	Classe classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
        	Status status = statusOriginarioAdapter.nextStatus(processo.identity(), "DISTRIBUIR");
        	Set<Parte> partes = new HashSet<>(0);
        	
        	command.getPoloAtivo().forEach(parteDto -> partes.add(new Parte(parteDto.getApresentacao(), Polo.ATIVO, new PessoaId(parteDto.getPessoa()))));
            command.getPoloPassivo().forEach(parteDto -> partes.add(new Parte(parteDto.getApresentacao(), Polo.PASSIVO, new PessoaId(parteDto.getPessoa()))));
            processo.autuar(classe, numero.numero(), partes, autuador, status);
        } else {
        	Status status = statusOriginarioAdapter.nextStatus(processo.identity(), "REJEITAR");
        	processo.rejeitar(command.getMotivo(), status);       	
        }

        processoRepository.save(processo);
        
        if (command.isValida()) {
        	// TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        	rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_AUTUADO_QUEUE, new ProcessoAutuado(processo.identity().toString(), numero.toString()));
        }
    }
    
    /**
     * @param command
     */
    @Command(description = "Autuação de Recursais")
    public void handle(AutuarRecursalCommand command) {
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
        Status status = statusRecursalAdapter.nextStatus(processo.identity());
        Autuador autuador = autuadorAdapter.autuador();
        Set<Parte> partes = new HashSet<>(0);
        pessoaAdapter.criarPessoas(command.getPoloAtivo()).forEach(p1 -> partes.add(new Parte(p1.getApresentacao(), Polo.ATIVO, new PessoaId(p1.getPessoaId()))));
        pessoaAdapter.criarPessoas(command.getPoloPassivo()).forEach(p2 -> partes.add(new Parte(p2.getApresentacao(), Polo.PASSIVO, new PessoaId(p2.getPessoaId()))));

		Set<Assunto> assuntos = command.getAssuntos().stream()
				.map(assunto -> teseRepository.findOneAssunto(new AssuntoId(assunto))).collect(Collectors.toSet());
        
        processo.autuar(assuntos, partes, autuador, status);
        processoRepository.save(processo);
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_AUTUADO_QUEUE, new ProcessoAutuado(processo.identity().toString(), processo.toString()));
    }
    
    /**
     * @param command
     */
    @Command(description = "Autuação de Processos Criminais")
    public void handle(AutuarRecursalCriminalEleitoralCommand command) {
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
        Status status = statusRecursalAdapter.nextStatus(processo.identity());
        Autuador autuador = autuadorAdapter.autuador();
        Set<Parte> partes = new HashSet<>(0);
        pessoaAdapter.criarPessoas(command.getPoloAtivo()).forEach(p1 -> partes.add(new Parte(p1.getApresentacao(), Polo.ATIVO, new PessoaId(p1.getPessoaId()))));
        pessoaAdapter.criarPessoas(command.getPoloPassivo()).forEach(p2 -> partes.add(new Parte(p2.getApresentacao(), Polo.PASSIVO, new PessoaId(p2.getPessoaId()))));

		Set<Assunto> assuntos = command.getAssuntos().stream()
				.map(assunto -> teseRepository.findOneAssunto(new AssuntoId(assunto))).collect(Collectors.toSet());
        
        processo.autuar(assuntos, partes, autuador, status);
        processoRepository.save(processo);
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_AUTUADO_QUEUE, new ProcessoAutuado(processo.identity().toString(), processo.toString()));
    }
    
    /**
     * @param command
     */
    @Command(description = "Análise de Pressupostos Formais")
    public void handle(AnalisarPressupostosFormaisCommand command) {
		ProcessoRecursal processoRecursal = (ProcessoRecursal) processoRepository
				.findOne(new ProcessoId(command.getProcessoId()));
		Status status = command.isProcessoApto() ? statusRecursalAdapter.nextStatus(processoRecursal.identity())
				: statusRecursalAdapter.nextStatus(processoRecursal.identity(), "REVISAR_ANALISE");
		Set<MotivoInaptidao> motivos = Optional.ofNullable(command.getMotivosInaptidao())
				.map(motvs -> motvs.stream().map(processoRepository::findOneMotivoInaptidao)
						.collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
        
		processoRecursal.analisarPressupostosFormais(command.isProcessoApto(), command.getObservacao(), motivos, status);
        processoRepository.save(processoRecursal);
    }
    
    /**
     * @param command
     */
    @Command(description = "Revisão de Análise de Pressupostos")
	public void handle(RevisarPressupostosFormaisCommand command) {
		ProcessoRecursal processoRecursal = (ProcessoRecursal) processoRepository
				.findOne(new ProcessoId(command.getProcessoId()));
		Status status = command.isProcessoApto() ? statusRecursalAdapter.nextStatus(processoRecursal.identity())
				: statusRecursalAdapter.nextStatus(processoRecursal.identity(), "INAPTO");
		Set<MotivoInaptidao> motivos = Optional.ofNullable(command.getMotivosInaptidao())
				.map(motvs -> motvs.stream().map(processoRepository::findOneMotivoInaptidao)
						.collect(Collectors.toSet()))
				.orElse(new HashSet<>(0));
		
		processoRecursal.analisarPressupostosFormais(command.isProcessoApto(), command.getObservacao(), motivos, status);
		processoRepository.save(processoRecursal);
	}
    
    /**
     * @param command
     */
    @Command(description = "Análise da Repercussão Geral")
    public void handle(AnalisarRepercussaoGeralCommand command) {
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
		Status status = command.isRepercussaoGeral() ? statusRecursalAdapter.nextStatus(processo.identity(), "REVISAR_RG") : statusRecursalAdapter.nextStatus(processo.identity());
		Set<Assunto> assuntos = command.getAssuntos().stream()
				.map(assunto -> teseRepository.findOneAssunto(new AssuntoId(assunto))).collect(Collectors.toSet());
		Set<Tese> teses = command.getTeses().stream().map(tese -> teseRepository.findOne(new TeseId(tese)))
				.collect(Collectors.toSet());
        
        processo.analisarRepercussaoGeral(command.getObservacao(), teses, assuntos, status);
        processoRepository.save(processo);
    }
    
    /**
     * @param command
     */
    @Command(description = "Revisão da Repercussão Geral")
    public void handle(RevisarRepercussaoGeralCommand command) {
    	ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(new ProcessoId(command.getProcessoId()));
    	Status status = statusRecursalAdapter.nextStatus(processo.identity());
		Set<Assunto> assuntos = command.getAssuntos().stream()
				.map(assunto -> teseRepository.findOneAssunto(new AssuntoId(assunto))).collect(Collectors.toSet());
		Set<Tese> teses = command.getTeses().stream().map(tese -> teseRepository.findOne(new TeseId(tese)))
				.collect(Collectors.toSet());
		
        processo.analisarRepercussaoGeral(command.getObservacao(), teses, assuntos, status);
        processoRepository.save(processo);
    }
    
    /**
     * @param command
     */
    @Command(description = "Enviar um Processo Recursal")
	public void handle(EnviarProcessoRecursalCommand command){
    	ProcessoId processoId = processoRepository.nextProcessoId();
    	Classe classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
    	Set<Preferencia> preferencias = Optional.ofNullable(command.getPreferencias()).isPresent()
				? command.getPreferencias().stream().map(pref -> preferenciaRepository.findOne(new PreferenciaId(pref)))
						.collect(Collectors.toSet()) : null;
		Sigilo sigilo = Sigilo.valueOf(command.getSigilo());
		Status status = command.isCriminalEleitoral() ? statusRecursalAdapter.nextStatus(processoId, "CRIMINAL_ELEITORAL")
						: statusRecursalAdapter.nextStatus(processoId);
		Set<Assunto> assuntos = command.getAssuntos().stream()
				.map(assunto -> teseRepository.findOneAssunto(new AssuntoId(assunto))).collect(Collectors.toSet());
		Set<Parte> partes = new HashSet<>(0);
        
		command.getPartesPoloAtivo().forEach(parteDto -> partes.add(new Parte(parteDto.getApresentacao(), Polo.ATIVO, new PessoaId(parteDto.getPessoa()))));
        command.getPartesPoloPassivo().forEach(parteDto -> partes.add(new Parte(parteDto.getApresentacao(), Polo.PASSIVO, new PessoaId(parteDto.getPessoa()))));
        
        Set<Origem> origens = command.getOrigens().stream().map(origem -> {
        	UnidadeFederacao uf = unidadeFederacaoRepository.findOne(origem.getUnidadeFederacaoId());
			TribunalJuizo tribunal = tribunalJuizoRepository.findOne(origem.getCodigoJuizoOrigem());
			
			return new Origem(uf, tribunal, origem.getNumeroProcesso(), origem.getIsPrincipal());
        }).collect(Collectors.toSet());
        Identificacao numero = numeroProcessoAdapter.novoNumeroProcesso(command.getClasseId());
		ProcessoRecursal processo = processoFactory.novoEnvio(processoId, classe, numero.numero(), preferencias, command.getNumeroRecursos(), sigilo, origens, assuntos, partes, status);
		
		processoRepository.save(processo);
	}

}
