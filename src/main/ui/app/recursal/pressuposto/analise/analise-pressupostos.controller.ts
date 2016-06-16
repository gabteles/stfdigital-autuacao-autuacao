import {AnaliseCommand, AnalisePressupostosService} from "./analise-pressupostos.service";
import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import analise from "./analise-pressupostos.module";

/**
 * @author Viniciusk
 */

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
	
}

analise.controller('app.novo-processo.analise.AnalisePressupostosController', AnalisePressupostosController);
export default analise;