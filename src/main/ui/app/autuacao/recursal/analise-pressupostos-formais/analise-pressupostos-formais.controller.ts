import {AnaliseCommand, AnalisePressupostosFormaisService} from "./analise-pressupostos-formais.service";
import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import analise from "./analise-pressupostos-formais.module";

/**
 * @author Viniciusk
 */

export class AnalisePressupostosFormaisController {
	
	public basicForm: Object = {};
	public apto : boolean = false;
	public classificacao : string;
	public motivosSelecionados : Array<number>;
	public observacao : string;
	public peticao : Object = {};

	static $inject = ['$state', 'app.autuacao.analise.AnalisePressupostosService', 'motivos', '$stateParams'];
	
    constructor(private $state: IStateService,
            private analisePressupostosService: AnalisePressupostosFormaisService,
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

analise.controller('app.autuacao.analise.AnalisePressupostosController', AnalisePressupostosController);
export default analise;