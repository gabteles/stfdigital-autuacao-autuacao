import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import IHttpService = angular.IHttpService;
import {Assunto, AutuarProcessoRecursalCommand, ProcessoRecursal} from "../shared/recursal.model";
import {AssuntoService} from "../shared/assunto.service";
import {AutuacaoCriminalEleitoralService} from "./autuacao-criminal-eleitoral.service";
import autuacaoRecursal from "../shared/recursal.module";

export class AutuacaoCriminalEleitoralController {
	
	public partePoloAtivo: string;
	public partePoloPassivo: string;
	public assunto : Assunto;
    public assuntos: Array<Assunto> = [];
    public assuntosSelecionados : Array<Assunto> = [];
    
    public cmdAutuar : AutuarProcessoRecursalCommand = new AutuarProcessoRecursalCommand();
	
	static $inject = ['$state', '$stateParams', 'messagesService','app.autuacao.recursal.AutuacaoCriminalEleitoralService', 'app.autuacao.recursal.AssuntoService', 'processo'];
	
    constructor(private $state: IStateService, private $stateParams: IStateParamsService, private messagesService: app.support.messaging.MessagesService,
    		private autuacaoCriminalEleitoralService: AutuacaoCriminalEleitoralService,
    		private assuntoService : AssuntoService, public processo : ProcessoRecursal) {
    	
    	this.cmdAutuar.processoId = $stateParams['informationId'];
        if (processo.partes.length > 0){
            processo.partes.forEach(parte =>{
                if (parte.polo === 'ATIVO'){
                    this.cmdAutuar.poloAtivo.push(parte.apresentacao);
                }else{
                    this.cmdAutuar.poloPassivo.push(parte.apresentacao);
                }
            });
        }
        if (processo.assuntos.length > 0){
        	this.assuntosSelecionados = processo.assuntos;
        }
    }
    
    public pesquisaAssuntos(assunto : string) : void{
    	this.assuntoService.listarAssuntos(assunto).then(assuntos => {
    		this.assuntos = assuntos;
    	});
    };
    
    public adicionarAssuntoNaLista (assunto : Assunto): void {
    	let verificaSeAssuntoExiste : boolean = false;
    	for (let i  of this.assuntosSelecionados){
    		if (i.codigo === assunto.codigo){
    			verificaSeAssuntoExiste = true;
    			this.assunto = null;
    		}
    	}
    	if (!verificaSeAssuntoExiste){
    		this.assuntosSelecionados.push(assunto);
    		this.assunto = null
    	}
    }
    
    public removerAssuntosSelecionados ($index) : void{
    	this.assuntosSelecionados.splice($index, 1);
    }
    
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.cmdAutuar.poloAtivo.length; i++){
            if (this.cmdAutuar.poloAtivo[i] === this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                return;
            }
        }
        this.cmdAutuar.poloAtivo.push(this.partePoloAtivo.toUpperCase());
        this.partePoloAtivo = "";
    }
    
    public removerPartePoloAtivo(indice: number): void {
        this.cmdAutuar.poloAtivo.splice(indice, 1);
    }
    
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.cmdAutuar.poloPassivo.length; i++){
            if (this.cmdAutuar.poloPassivo[i] === this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                return;
            }
        }
        
        this.cmdAutuar.poloPassivo.push(this.partePoloPassivo.toUpperCase());
        this.partePoloPassivo = "";
    }
    
    public removerPartePoloPassivo(indice: number): void {
        this.cmdAutuar.poloPassivo.splice(indice, 1);
    }
    
    
	public autuarCriminalEleitoral(): void {
	    this.autuacaoCriminalEleitoralService.autuar(this.construirCommand(this.assuntosSelecionados))
	    .then(() => {
            this.$state.go('app.tarefas.minhas-tarefas');
            this.messagesService.success('Processo autuado com sucesso.');
        }, () => {
            this.messagesService.error('Erro ao autuar o processo.');
        });
	};
	
	private construirCommand(assuntos : Array<Assunto>) : AutuarProcessoRecursalCommand{
	    for (let i of assuntos){
	    	this.cmdAutuar.assuntos.push(i.codigo);
	    }
	    return this.cmdAutuar;
	}
	
	
}

autuacaoRecursal.controller('app.autuacao.recursal.AutuacaoCriminalEleitoralController', AutuacaoCriminalEleitoralController);
export default autuacaoRecursal;