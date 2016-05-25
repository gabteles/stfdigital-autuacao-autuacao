/**
 * @author Viniciusk
 */
import {AnaliseCommand, AnalisePressupostosService} from "./analise-pressupostos.service";
import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import analise from "./analise-pressupostos.module";

export class AnalisePressupostosController {
	
	public basicForm: Object = {};
	public apto : boolean = false;
	public classificacao : string;
	public motivosSelecionados : Array<number>;
	public observacao : string;
	public peticao : Object = {};

	static $inject = ['$state', 'app.novo-processo.analise.AnalisePressupostosService', 'motivos', '$stateParams'];
	
    constructor(private $state: IStateService,
            private analisePressupostosService: AnalisePressupostosService,
            public motivos, private $stateParams : IStateParamService ) {
    		
    		//let resource = $stateParams['resources']
    		//this.processo = autuacaoService.consultar(angular.isObject(resource) ? resource.processoId : resource);
    }
    
	public registrarAnalise(): void {
	    this.analisePressupostosService.analisar(this.commandAnalise())
	        .then(() => {
	            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
	    });
	};

	private commandAnalise(): AnaliseCommand {
	    return new AnaliseCommand(1, this.apto, this.motivosSelecionados, this.observacao);
	};
	
/*	public mockProcessoAutuacao () : Object {
		let processoMock : Object = {processoId : 1, remessa : {classeSugerida : 'RE',  qtdVolumes : 2, qtdApensos : 3, formaRecebimento : 'SEDEX', numeroSedex : '2000'}} ;
		return processoMock;
	} 
*/	
}

analise.controller('app.novo-processo.analise.AnalisePressupostosController', AnalisePressupostosController);
export default analise;