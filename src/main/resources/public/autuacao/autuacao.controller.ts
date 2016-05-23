/**
 * @author Viniciusk
 */
import {AutuacaoCommand, AutuacaoService} from "./autuacao.service";
import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import autuacao from "./autuacao.module";

export class ParteDto{
	
	public apresentacao : string;
	public pessoa : number;

	constructor (apresentacao : string , pessoa? : number) {
		this.apresentacao = apresentacao;
		this.pessoa = pessoa;
	}

}

export class AutuacaoController {
	
	public basicForm: Object = {};
	public partePoloAtivo: string;
	public partePoloPassivo: string;
	public partesPoloAtivo: Array<ParteDto> = new Array<ParteDto>();
	public partesPoloPassivo: Array<ParteDto> = new Array<ParteDto>();
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
    		this.processo = this.mockProcessoAutuacao();
    		
    		let parteAtiva = new ParteDto('JOSÉ DE SOUZA', 2);
    		this.partesPoloAtivo.push(parteAtiva);
    		
    		let partePassiva = new ParteDto('ALINE PEREIRA', 3);
    		this.partesPoloPassivo.push(partePassiva);
    }
    
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.partesPoloAtivo.length; i++){
            if (this.partesPoloAtivo[i].apresentacao == this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                return;
            }
        }
        let parte = new ParteDto(this.partePoloAtivo.toUpperCase());
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
        
        let parte = new ParteDto(this.partePoloPassivo.toUpperCase())
        this.partesPoloPassivo.push(parte);
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
	    return new AutuacaoCommand(1, this.classe, this.partesPoloPassivo, this.partesPoloPassivo);
	};
	
	public mockProcessoAutuacao () : Object {
		let processoMock : Object = {processoId : 1, remessa : {classeSugerida : 'RE',  qtdVolumes : 2, qtdApensos : 3, formaRecebimento : 'SEDEX', numeroSedex : '2000'}} ;
		return processoMock;
	} 
	
}

autuacao.controller('app.novo-processo.autuacao.AutuacaoController', AutuacaoController);
export default autuacao;