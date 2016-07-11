import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import {AnalisePressupostosFormaisService} from "./analise-pressupostos-formais.service";
import {AnalisarPressupostosFormaisCommand, MotivoInaptidao} from "../shared/recursal.model";
import autuacaoRecursal from '../shared/recursal.module';

/**
 * @author Viniciusk
 */

export class AnalisePressupostosFormaisController {
	
	public cmd : AnalisarPressupostosFormaisCommand = new AnalisarPressupostosFormaisCommand();

	static $inject = ['$state', '$stateParams', 'app.autuacao.recursal.AnalisePressupostosFormaisService', 'motivosInaptidao'];
	
    constructor(private $state: IStateService,
    	        private $stateParams: IStateParamsService,
    		    private analiseService: AnalisePressupostosFormaisService,
    		    public motivosInaptidao: Array<MotivoInaptidao>) {
    	this.cmd.processoId = $stateParams['informationId'];
    }
    
	public registrarAnalise(): void {
	    this.analiseService.analisar(this.cmd).then(() => {
	    	this.$state.go('app.tarefas.minhas-tarefas');
	    });
	};
	
}

autuacaoRecursal.controller('app.autuacao.recursal.AnalisePressupostosFormaisController', AnalisePressupostosFormaisController);
export default autuacaoRecursal;