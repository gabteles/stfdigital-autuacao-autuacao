import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import revisaoAnaliseRepercussaoGeral from "./revisao-analise-repercussao-geral.module";
//import {Tese, Assunto, Processo, MotivoInaptidao} from "../services/model";
import {AutuacaoService} from "../services/autuacao.service";

export class RevisaoAnaliseRepercussaoGeralController {
    
	public basicForm: Object = {};
    public processoId: number;
	public numeroProcesso: string;
	
    /** @ngInject **/
    static $inject = ["$state", "app.novo-processo.autuacao-services.AutuacaoService"];
    
    constructor(private $state: IStateService, private autuacaoService: AutuacaoService){
        //Mock
		this.processoId = 1;
		this.numeroProcesso = "RE 123/2016";		
    }
    
    /**
	 * Realiza a revisão da análise de pressupostos formais realizada. 
	 */
    public revisarAnaliseRepercussaoGeral(): void {
		/*
	    this.autuacaoService.revisarAnalisePressupostos(this.processoId, this.apto, this.motivosInaptidaoSelecionados, 
			this.observacao).then(() => { 
				this.$state.go("app.tarefas.minhas-tarefas", {}, { reload: true });
	    }); */
	};
    
}

revisaoAnaliseRepercussaoGeral.controller("app.novo-processo.revisao-analise-repercussao-geral.RevisaoAnaliseRepercussaoGeralController", 
    RevisaoAnaliseRepercussaoGeralController);
export default revisaoAnaliseRepercussaoGeral;

