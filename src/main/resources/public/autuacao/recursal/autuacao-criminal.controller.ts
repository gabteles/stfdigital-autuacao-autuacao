/**
 * @author Viniciusk
 */
import {AutuarRecursalCommand, AutuacaoCriminalService} from "./autuacao-criminal.service";
import IStateService = angular.ui.IStateService;
import IScope = angular.IScope
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import analise from "./autuacao-criminal.module";

export class ParteDto{
	
	public apresentacao : string;
	public pessoa : number;

	constructor (apresentacao : string , pessoa? : number) {
		this.apresentacao = apresentacao;
		this.pessoa = pessoa;
	}

}

export class Assunto {
	public codigo : string;
	public descricao : string;
}

export class AutuacaoCriminalController {
	
	public basicForm: Object = {};
	public partePoloAtivo: string;
	public partePoloPassivo: string;
	public partesPoloAtivo: Array<ParteDto> = new Array<ParteDto>();
	public partesPoloPassivo: Array<ParteDto> = new Array<ParteDto>();
	public apto : boolean = false;
	public assuntosSelecionados : Array<Assunto> = [];
	public observacao : string;
	public peticao : Object = {};
	public assunto : Assunto;
	public assuntos : Array<Assunto> = [];

	static $inject = ['$state', 'app.novo-processo.criminal.AutuacaoCriminalService', '$stateParams', 'properties', '$scope', '$http'];
	
    constructor(private $state: IStateService, private autuacaoCriminalService: AutuacaoCriminalService,
    		private $stateParams : IStateParamService, private properties, private $scope : IScope, private $http : IHttpService ) {
    }
    
    public pesquisaAssuntos(assunto : string) : void{
    	this.$http.get(this.properties.apiUrl + '/autuacao/api/processos/assuntos', {params: {'termo' : assunto}})
    		.then((response : ng.IHttpPromiseCallbackArg<Assunto[]>) => {
    			this.assuntos = response.data;
    		});
    }
    
    public adicionarAssuntoNaLista (assunto : Assunto): void {
    	let verificaSeAssuntoExiste : boolean = false;
    	for (let i  of this.assuntosSelecionados){
    		if (i.codigo == assunto.codigo){
    			verificaSeAssuntoExiste = true;
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
    
	public autuarRecursal(): void {
	    this.autuacaoCriminalService.autuar(this.commandAutuacaoRecursal())
	        .then(() => {
	            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
	    });
	};

	private commandAutuacaoRecursal(): AutuarRecursalCommand {
	    return new AutuarRecursalCommand(1, this.apto, [], this.observacao);
	};
	
/*	public mockProcessoAutuacao () : Object {
		let processoMock : Object = {processoId : 1, remessa : {classeSugerida : 'RE',  qtdVolumes : 2, qtdApensos : 3, formaRecebimento : 'SEDEX', numeroSedex : '2000'}} ;
		return processoMock;
	} 
*/	
}

analise.controller('app.novo-processo.criminal.AutuacaoCriminalController', AutuacaoCriminalController);
export default analise;