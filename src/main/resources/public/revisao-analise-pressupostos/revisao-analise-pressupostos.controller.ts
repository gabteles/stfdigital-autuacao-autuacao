import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import revisaoAnalisePressupostos from "./revisao-analise-pressupostos.module";
import {Tese, Assunto, Processo, MotivoInaptidao} from "../services/model";
import {AutuacaoService} from "../services/autuacao.service";

export class RevisaoAnalisePressupostosController {
    
    public basicForm: Object = {};
	public processoId: number;
    public apto: boolean = false;
	public classificacao: string;
	public motivosInaptidaoSelecionados: Array<number>;
	public motivosInaptidao: Array<MotivoInaptidao>;
	public observacao: string;
    
    /** @ngInject **/
    static $inject = ["$state", "app.novo-processo.autuacao-services.AutuacaoService"];
    
    constructor(private $state: IStateService, private autuacaoService: AutuacaoService){
        //Mock
		this.autuacaoService.listarMotivosInaptidao().then((motivos) => {
			this.motivosInaptidao = motivos;
			this.processoId = 1; //Alterar para o valor recebido como parâmetro.
			this.motivosInaptidaoSelecionados = [2, 3];
			this.apto = true;
			this.classificacao = "inapto";
			this.observacao = "Bla bla bla bla";
		});		
    }
    
	/**
	 * Realiza a revisão da análise de pressupostos formais realizada. 
	 */
    public revisarAnalisePressupostos(): void {
		
	    this.autuacaoService.revisarAnalisePressupostos(this.processoId, this.apto, this.motivosInaptidaoSelecionados, 
			this.observacao).then(() => { 
				this.$state.go("app.tarefas.minhas-tarefas", {}, { reload: true });
	    });
	};
}

revisaoAnalisePressupostos.controller("app.novo-processo.revisao-analise-pressupostos.RevisaoAnalisePressupostosController", 
    RevisaoAnalisePressupostosController);
export default revisaoAnalisePressupostos;