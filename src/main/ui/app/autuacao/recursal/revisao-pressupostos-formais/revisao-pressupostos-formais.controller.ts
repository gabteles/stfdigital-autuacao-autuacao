import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import {AnalisePressupostosFormais, Tese, Assunto, MotivoInaptidao, RevisarPressupostosFormaisCommand} from "../shared/recursal.model";
import {AutuacaoRecursalSharedService} from "../shared/recursal.service";
import {RevisaoPressupostosFormaisService} from "./revisao-pressupostos-formais.service";
import autuacaoRecursal from "../shared/recursal.module";

export class RevisaoPressupostosFormaisController {
    
    public cmd: RevisarPressupostosFormaisCommand = new RevisarPressupostosFormaisCommand();
    
    /** @ngInject **/
    static $inject = ['$state', 'app.autuacao.recursal.RevisaoPressupostosFormaisService', 'analise', 'motivosInaptidao', 'messagesService'];
    
    constructor(private $state: IStateService, 
    		private revisaoPressupostosFormaisService: RevisaoPressupostosFormaisService,
    		private analise: AnalisePressupostosFormais,
    		public motivosInaptidao: Array<MotivoInaptidao>, private messagesService: app.support.messaging.MessagesService) { 
    	
    	this.cmd.processoId = analise.processoId;
    	analise.motivosInaptidao.forEach(motivoInaptidao => {
    		this.cmd.motivosInaptidao.push(motivoInaptidao.codigo);
    	});
    	this.cmd.observacao = analise.observacao;
    	this.cmd.processoApto = analise.processoApto;
    }
    
	/**
	 * Realiza a revisão da análise de pressupostos formais realizada. 
	 */
    public revisarAnalisePressupostos(): void {
	    this.revisaoPressupostosFormaisService.revisar(this.cmd).then(() => {
            this.$state.go('app.tarefas.minhas-tarefas');
            this.messagesService.success('Revisão registrada com sucesso!');
    });
	};
}

autuacaoRecursal.controller("app.autuacao.recursal.RevisaoPressupostosFormaisController", RevisaoPressupostosFormaisController);
export default autuacaoRecursal;