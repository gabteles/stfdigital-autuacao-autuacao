/**
 * @author Viniciusk
 */
import {AutuacaoOriginarioCommand, AutuacaoOriginarioService, ParteDto} from "./autuacao.originario.service";
import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import autuacao from "./autuacao.module";


export class AutuacaoController {
	
	public basicForm: Object = {};
	public partePoloAtivo: string;
	public partePoloPassivo: string;
	public processo : Object = {};
	public valida : boolean;
	public processoId : number;

	public cmdAutuar : AutuacaoOriginarioCommand = new AutuacaoOriginarioCommand();
	
	static $inject = ['$state', 'app.autuacao.autuacao.AutuacaoOriginarioService', 'classes',  'messagesService'];
	
    constructor(private $state: IStateService, private autuacaoOriginarioService: AutuacaoOriginarioService,
            public classes, private messagesService: app.support.messaging.MessagesService ) {
    		
    		let parteAtiva = new ParteDto('JOSÉ DE SOUZA', 2);
    		this.cmdAutuar.poloAtivo.push(parteAtiva);
    		let partePassiva = new ParteDto('ALINE PEREIRA', 3);
    		this.cmdAutuar.poloPassivo.push(partePassiva);
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
        let parte = new ParteDto(this.partePoloAtivo.toUpperCase());
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
        
        let parte = new ParteDto(this.partePoloPassivo.toUpperCase())
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
	
/*	public mockProcessoAutuacao () : Object {
		let processoMock : Object = {processoId : 1, remessa : {classeSugerida : 'RE',  qtdVolumes : 2, qtdApensos : 3, formaRecebimento : 'SEDEX', numeroSedex : '2000'}} ;
		return processoMock;
	} 
*/	
}

autuacao.controller('app.autuacao.autuacao.AutuacaoController', AutuacaoController);
export default autuacao;