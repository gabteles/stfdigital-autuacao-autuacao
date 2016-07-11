import IStateService = angular.ui.IStateService;
import IScope = angular.IScope
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import {Parte} from "../../shared/autuacao.model";
import {Assunto, AutuarProcessoRecursalCommand} from "../shared/recursal.model";
import {AssuntoService} from "../shared/assunto.service";
import {AutuacaoCriminalEleitoralService} from "./autuacao-criminal-eleitoral.service";
import autuacaoRecursal from "../shared/recursal.module";

export class AutuacaoCriminalEleitoralController {
	
	public basicForm: Object = {};
	public partePoloAtivo: string;
	public partePoloPassivo: string;
	public partesPoloAtivo: Array<Parte> = new Array<Parte>();
	public partesPoloPassivo: Array<Parte> = new Array<Parte>();
	public apto : boolean = false;
	public assuntosSelecionados : Array<Assunto> = [];
	public observacao : string;
	public peticao : Object = {};
	public assunto : Assunto;
    public assuntos: Array<Assunto> = [];
	
	static $inject = ['$state', 'app.autuacao.recursal.AutuacaoCriminalEleitoralService', 'app.autuacao.recursal.AssuntoService'];
	
    constructor(private $state: IStateService, private autuacaoCriminalEleitoralService: AutuacaoCriminalEleitoralService,
    		private assuntoService : AssuntoService) {
    }
    
    public pesquisaAssuntos(assunto : string) : void{
    	this.assuntoService.listarAssuntos(assunto).then(assuntos => {
    		this.assuntos = assuntos;
    	});
    };
    
    public adicionarAssuntoNaLista (assunto : Assunto): void {
    	let verificaSeAssuntoExiste : boolean = false;
    	for (let i  of this.assuntosSelecionados){
    		if (i.codigo == assunto.codigo){
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
        for (let i = 0; i < this.partesPoloAtivo.length; i++){
            if (this.partesPoloAtivo[i].apresentacao == this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                return;
            }
        }
        let parte = new Parte(this.partePoloAtivo.toUpperCase());
        this.partesPoloAtivo.push(parte);
        this.partePoloAtivo = "";
    }
    
    public removerPartePoloAtivo(indice: number): void {
        this.partesPoloAtivo.splice(indice);
    }
    
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.partesPoloPassivo.length; i++){
            if (this.partesPoloPassivo[i].apresentacao == this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                return;
            }
        }
        
        let parte = new Parte(this.partePoloPassivo.toUpperCase())
        this.partesPoloPassivo.push(parte);
        this.partePoloPassivo = "";
    }
    
    public removerPartePoloPassivo(indice: number): void {
        this.partesPoloPassivo.splice(indice);
    }
    
    
	public autuarCriminalEleitoral(): void {
	    this.autuacaoCriminalEleitoralService.autuar(this.commandAutuacaoCriminal(1, this.partesPoloAtivo, this.partesPoloPassivo, this.assuntos))
	        .then(() => {
	            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
	    });
	};

	private commandAutuacaoCriminal(processoId : number, partesAtivo : Array<Parte>, partesPassivo : Array<Parte>, assuntosC : Array<Assunto>): AutuarProcessoRecursalCommand {
		let assuntosCommand : string[];
		for(let assunto of assuntosC){
			assuntosCommand.push(assunto.codigo);
		}
		
		let partesAtivaCommand : string[];
		for(let parte of partesAtivo){
			partesAtivaCommand.push(parte.apresentacao);
		}
		
		let partesPassivaCommand : string[];
		for(let parte of partesPassivo){
			partesPassivaCommand.push(parte.apresentacao);
		}
		
	    return //new AutuarProcessoCriminalCommand(1, partesAtivaCommand, partesPassivaCommand, assuntosCommand);
	};
	
}

autuacaoRecursal.controller('app.autuacao.recursal.AutuacaoCriminalEleitoralController', AutuacaoCriminalEleitoralController);
export default autuacaoRecursal;