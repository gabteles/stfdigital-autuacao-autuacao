/**
 * @author Viniciusk
 */
import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import MessagesService = app.support.messaging.MessagesService;
import {AutuacaoOriginarioCommand, AutuacaoOriginarioService} from "./autuacao-originario.service";
import {Parte} from "../shared/autuacao.model";
import autuacaoOriginario from "./autuacao-originario.module";


export class AutuacaoOriginarioController {
	
	public partePoloAtivo: string;
	public partePoloPassivo: string;
	public processo : Object = {};
	public valida : boolean;
	public processoId : number;

	public cmdAutuar : AutuacaoOriginarioCommand = <AutuacaoOriginarioCommand>{};
	
	static $inject = ['$state', 'app.autuacao.originario.AutuacaoOriginarioService', 'classes',  'messagesService'];
	
    constructor(private $state: IStateService, private autuacaoOriginarioService: AutuacaoOriginarioService,
            public classes, private messagesService: MessagesService ) {
    		this.valida = true;
    		
    		//consulta o processo aqui!!!
    }
    
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.cmdAutuar.poloAtivo.length; i++){
            if (this.cmdAutuar.poloAtivo[i].apresentacao == this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                return;
            }
        }
        let parte = new Parte(this.partePoloAtivo.toUpperCase());
        this.cmdAutuar.poloAtivo.push(parte);
        this.partePoloAtivo = "";
    }
    
    public removerPartePoloAtivo(indice: number): void {
    	this.cmdAutuar.poloAtivo.splice(indice);
    }
    
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.cmdAutuar.poloPassivo.length; i++){
            if (this.cmdAutuar.poloPassivo[i].apresentacao == this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                return;
            }
        }
        
        let parte = new Parte(this.partePoloPassivo.toUpperCase())
        this.cmdAutuar.poloPassivo.push(parte);
        this.partePoloPassivo = "";
    }
    
    public removerPartePoloPassivo(indice: number): void {
    	this.cmdAutuar.poloPassivo.splice(indice);
    }

	public registrarAutuacao(): void {
		
	    this.autuacaoOriginarioService.autuar(this.cmdAutuar)
	    .then(() => {
            this.$state.go('app.tarefas.minhas-tarefas');
			this.messagesService.success('Processo autuado com sucesso.');
    	}, () => {
			this.messagesService.error('Erro ao autuar o processo.');
		});
	};
	
}

autuacaoOriginario.controller('app.autuacao.originario.AutuacaoOriginarioController', AutuacaoOriginarioController);
export default autuacaoOriginario;