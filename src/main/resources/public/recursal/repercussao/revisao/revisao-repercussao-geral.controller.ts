import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import revisaoRepercussaoGeral from "./revisao-repercussao-geral.module";
import {TipoTese} from "../../../services/model";
import {AutuacaoService} from "../../../services/autuacao.service";

export class RevisaoRepercussaoGeralController {
    
	public basicForm: Object = {};
    public processoId: number;
	public numeroProcesso: string;
	public tiposTese: Array<TipoTese>;
	public tipoTese: TipoTese;
	
    /** @ngInject **/
    static $inject = ["$state", "app.novo-processo.autuacao-services.AutuacaoService"];
    
    constructor(private $state: IStateService, private autuacaoService: AutuacaoService){
        //Mock
		this.processoId = 1;
		this.numeroProcesso = "RE 123/2016";
		this.tiposTese = this.autuacaoService.listarTiposTeses();
		this.tipoTese = this.tiposTese[0];		
    }
    
    /**
	 * Realiza a revisão da análise de pressupostos formais realizada. 
	 */
    public revisarRepercussaoGeral(): void {
		/*
	    this.autuacaoService.revisarAnalisePressupostos(this.processoId, this.apto, this.motivosInaptidaoSelecionados, 
			this.observacao).then(() => { 
				this.$state.go("app.tarefas.minhas-tarefas", {}, { reload: true });
	    }); */
	};
    
}

revisaoRepercussaoGeral.controller("app.novo-processo.revisao-repercussao-geral.RevisaoRepercussaoGeralController", 
    RevisaoRepercussaoGeralController);
export default revisaoRepercussaoGeral;

