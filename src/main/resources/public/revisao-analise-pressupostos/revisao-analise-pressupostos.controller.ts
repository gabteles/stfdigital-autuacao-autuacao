import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import revisaoAnalisePressupostos from "./revisao-analise-pressupostos.module";
import {Tese, Assunto, Processo} from "../services/model";
import {AutuacaoService} from "../services/autuacao.service";

export class RevisaoAnalisePressupostosController {
    
    public basicForm: Object = {};
	public processoId: number;
    public apto: boolean = false;
	public classificacao: string;
	public motivosSelecionados: Array<number>;
	public observacao: string;
	public peticao: Object = {};
    
    /** @ngInject **/
    static $inject = ["$state", "app.novo-processo.autuacao-services.AutuacaoService"];
    
    constructor(private $state: IStateService, private autuacaoService: AutuacaoService){
        this.processoId = 1; //Alterar para o valor recebido como parâmetro.
    }
    
    public registrarAnalise(): void {
	    this.autuacaoService.revisarAnalisePressupostos(this.processoId, this.apto, this.motivosSelecionados, this.observacao) 
	        .then(() => {
	            this.$state.go("app.tarefas.minhas-tarefas", {}, { reload: true });
	    });
	};
}

revisaoAnalisePressupostos.controller("app.novo-processo.revisao-analise-pressupostos.RevisaoAnalisePressupostosController", 
    RevisaoAnalisePressupostosController);
export default revisaoAnalisePressupostos;