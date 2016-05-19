/**
 * @author Viniciusk
 */
import {AutuacaoCommand, AutuacaoService} from "./autuacao.service";
import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import autuacao from "./autuacao.module";

export class ParteDto{
	public aprensenta : String;
	public pessoa : number;
}

export class AutuacaoController {
	
	public basicForm: Object = {};
	public partePoloAtivo: string;
	public partePoloPassivo: string;
	public partesPoloAtivo: Array<string> = new Array<string>();
	public partesPoloPassivo: Array<string> = new Array<string>();
	public processo : Object = {};
	public classe : String = "";
	public valida : boolean;
	public processoId : number;

	static $inject = ['$state', 'app.novo-processo.autuacao.AutuacaoService', 'classes', '$stateParams'];
	
    constructor(private $state: IStateService,
            private autuacaoService: AutuacaoService,
            public classes, private $stateParams : IStateParamService ) {
    		
    		//let resource = $stateParams['resources']
    		//this.processo = autuacaoService.consultar(angular.isObject(resource) ? resource.processoId : resource);
    	
    		this.processo = this.mockProcessoAutuacao()
    }
    
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.partesPoloAtivo.length; i++){
            if (this.partesPoloAtivo[i] == this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                return;
            }
        }
        
        this.partesPoloAtivo.push(this.partePoloAtivo.toUpperCase());
        this.partePoloAtivo = "";
    }
    
    public removerPartePoloAtivo(indice: number): void {
        this.partesPoloAtivo.splice(indice);
    }
    
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.partesPoloPassivo.length; i++){
            if (this.partesPoloPassivo[i] == this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                return;
            }
        }
        
        this.partesPoloPassivo.push(this.partePoloPassivo.toUpperCase());
        this.partePoloPassivo = "";
    }
    
    public removerPartePoloPassivo(indice: number): void {
        this.partesPoloPassivo.splice(indice);
    }
    

	public registrarAutuacao(): void {
	    this.autuacaoService.autuar(this.commandAutuacao())
	        .then(() => {
	            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
	    });
	};

	private commandAutuacao(): AutuacaoCommand {
		let partesAtivo : Array<Object> = new Array<Object>();
		let partesPassivo : Array<Object> = new Array<Object>();
		
		if (this.partesPoloAtivo.length > 0){
			for (let parte of this.partesPoloAtivo){
				partesAtivo.push({apresentacao : parte, pessoa : 1});
			}
		}
		
		if (this.partesPoloPassivo.length > 0){
			for (let parte of this.partesPoloPassivo){
				partesPassivo.push({apresentacao : parte, pessoa : 1});
			}
		}
		
	    return new AutuacaoCommand(1, this.classe, partesAtivo, partesPassivo);
	};
	
	public mockProcessoAutuacao () : Object {
		let processoMock : Object = {processoId : 1, remessa : {classeSugerida : 'RE',  qtdVolumes : 2, qtdApensos : 3, formaRecebimento : 'SEDEX', numeroSedex : '2000'}} ;
		return processoMock;
	} 
	
}

autuacao.controller('app.novo-processo.autuacao.AutuacaoController', AutuacaoController);
export default autuacao;