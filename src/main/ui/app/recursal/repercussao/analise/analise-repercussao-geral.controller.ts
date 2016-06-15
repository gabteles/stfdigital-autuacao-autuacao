import {AnalisarRepercussaoGeralCommand, AutuacaoService} from "../../../services/autuacao.service";
import IStateService = angular.ui.IStateService;
import IScope = angular.IScope
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import {Assunto, TipoTese, Tese} from "../../../services/model";
import {AssuntoDto, AssuntoService} from "../../../services/assunto.service";
import "../../../services/assunto.service";
import analise from "./analise-repercussao-geral.module";

/**
 * @author viniciusk
 * 08/06/2016
 */

export class AnaliseRepercussaoGeralController {
	
	public basicForm: Object = {};
	public assuntosSelecionados : Array<AssuntoDto> = [];
	public observacao : string;
	public assunto : Assunto;
	public assuntos : Array<AssuntoDto> = [];
	public tipoTese : string;
	public numeroTese : string;
	public teses : Array<Tese> = [];
	public numeroProcesso : number;
	public classeProcesso : string;

	static $inject = ['$state', 'app.novo-processo.autuacao-services.AutuacaoService', 
	                  'app.novo-processo.autuacao-services.AssuntoService', 'tiposTese', '$stateParams', 'properties', '$scope', '$http'];
	
    constructor(private $state: IStateService, private autuacaoService: AutuacaoService, private assuntoService : AssuntoService, public tiposTese,
    		private $stateParams : IStateParamService, private properties, private $scope : IScope, private $http : IHttpService ) {
    	this.numeroProcesso = 100;
    	this.classeProcesso = 'ADI';
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
	    this.autuacaoService.analisarRepercussaoGeral(this.commandAnaliseRepercussao(1, this.assuntosSelecionados, this.teses, this.observacao))
	        .then(() => {
	            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
	    });
	};

	private commandAnaliseRepercussao(processoId : number, assuntosC : Array<AssuntoDto>, teses : Array<Tese>, observacao : string ): AnalisarRepercussaoGeralCommand {
		let assuntosCommand : string[];
		for(let assunto of assuntosC){
			assuntosCommand.push(assunto.codigo);
		}
		
		let tesesCommand : number[];
		for(let tese of teses){
			tesesCommand.push(tese.codigo);
		}
		
	    return new AnalisarRepercussaoGeralCommand(1, assuntosCommand, tesesCommand, observacao);
	};
	
}

analise.controller('app.novo-processo.analise.AnaliseRepercussaoGeralController', AnaliseRepercussaoGeralController);
export default analise;