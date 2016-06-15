import IStateService = angular.ui.IStateService;
import IScope = angular.IScope
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import {RevisarRepercussaoGeralCommand, AutuacaoService} from "../../../services/autuacao.service";
import {Assunto, TipoTese, Tese} from "../../../services/model";
import {AssuntoDto, AssuntoService} from "../../../services/assunto.service";
import "../../../services/assunto.service";
import revisao from "./revisao-repercussao-geral.module";

export class RevisaoRepercussaoGeralController {
    
	public basicForm: Object = {};
	public numeroProcesso : number;
	public classeProcesso : string;
	public assuntosSelecionados : Array<AssuntoDto> = [];
	public assunto : Assunto;
	public assuntos : Array<AssuntoDto> = [];
	public tipoTese : string;
	public numeroTese : string;
	public teses : Array<Tese> = [];
	public observacao : string;
	
	static $inject = ['$state', 'app.novo-processo.autuacao-services.AutuacaoService', 
	                  'app.novo-processo.autuacao-services.AssuntoService', 'tiposTese', '$stateParams', 'properties', '$scope', '$http'];
	
	constructor(private $state: IStateService, private autuacaoService: AutuacaoService, private assuntoService : AssuntoService, public tiposTese,
			private $stateParams : IStateParamService, private properties, private $scope : IScope, private $http : IHttpService ) {
		
		//o sistema aqui irá recuperar o processo, a tese e os assuntos relacionados.
		this.numeroProcesso = 100;
		this.classeProcesso = 'ADI';
		this.assuntosSelecionados.push(new AssuntoDto('4291', 'Jurisdição e Competência', 0));
		this.assuntosSelecionados.push(new AssuntoDto('10912', 'Medidas Assecuratórias', 0));
		this.teses.push(new Tese(170, 'Recurso extraordinário em que se discute', 1, null, 'REPERCUSSAO_GERAL'));
		
	}
	
	public pesquisaAssuntos(assunto : string) : void{
		this.assuntoService.listarAssuntos(assunto).then((assuntos : AssuntoDto[])=> {
			this.assuntos = assuntos;
		});
	};
	
	public adicionarAssuntoNaLista (assunto : AssuntoDto): void {
		let verificaSeAssuntoExiste : boolean = false;
		for (let i  of this.assuntosSelecionados){
			if (i.codigo == assunto.codigo){
				verificaSeAssuntoExiste = true;
				this.assunto = null;
			}
		}
		if (!verificaSeAssuntoExiste){
			this.assuntosSelecionados.push(assunto);
			this.assunto = null;
		}
	}
	
	public removerAssuntosSelecionados ($index) : void{
		this.assuntosSelecionados.splice($index, 1);
	}
	
	public consultarTese () : boolean {
		let soNumeros = /^[0-9]+$/g;
		
		if (!soNumeros.test(this.numeroTese)) {
			return false;
		}
		
		this.autuacaoService.consultarTeses(this.tipoTese, this.numeroTese).then((teses : Tese[]) => {
			let teseConsultada : Tese = teses[0];
			
			if (teseConsultada !== undefined){
				let assuntosConsultados : Array<Assunto> = teseConsultada.assuntos;  
				
				this.adicionarTese(teseConsultada);
				
				for (let i = 0; i < assuntosConsultados.length; i++) {
					this.adicionarAssuntoNaLista({codigo : assuntosConsultados[i].codigo, descricao : assuntosConsultados[i].descricao, nivel: 0});
				}
				
				this.numeroTese = null;
			} 
		});
	};
	
	public adicionarTese (tese : Tese) : void{
		let teseAdicionada : boolean = false;
		
		for (let i of this.teses){
			if (i.codigo == tese.codigo) {
				teseAdicionada = true;
			}
		}
		
		if (!teseAdicionada){
			this.teses.push(tese);
		}
	};
	
	public removerTese($index) : void{
		
		for (let i = 0; i < $index.assuntos.length; i++){
			let indice = this.assuntos.indexOf($index.assuntos[i]);
			this.assuntos.splice(indice, 1);
		}
		
		this.teses.splice($index,1);
	};
	
	
	public analisarRepercussaoGeral(): void {
		//TODO mudar o valor "1" para o id do processo que será recuperado no momento de analisar a repercussao geral
	    this.autuacaoService.revisarRepercussaoGeral(this.commandRevisaoRepercussao(1, this.assuntosSelecionados, this.teses, this.observacao))
	        .then(() => {
	            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
	    });
	};
	
	private commandRevisaoRepercussao(processoId : number, assuntosC : Array<AssuntoDto>, teses : Array<Tese>, observacao : string ): RevisarRepercussaoGeralCommand {
		let assuntosCommand : string[];
		for(let assunto of assuntosC){
			assuntosCommand.push(assunto.codigo);
		}
		
		let tesesCommand : number[];
		for(let tese of teses){
			tesesCommand.push(tese.codigo);
		}
		
	    return new RevisarRepercussaoGeralCommand(1, assuntosCommand, tesesCommand, observacao);
	};
   
}

revisao.controller("app.novo-processo.revisao-repercussao-geral.RevisaoRepercussaoGeralController", 
    RevisaoRepercussaoGeralController);
export default revisao;

