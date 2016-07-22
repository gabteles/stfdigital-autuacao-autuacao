import IStateService = angular.ui.IStateService;
import IScope = angular.IScope
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import {Assunto, TipoTese, Tese, RevisarRepercussaoGeralCommand} from "../shared/recursal.model";
import {AssuntoService} from "../shared/assunto.service";
import {AutuacaoRecursalSharedService} from "../shared/recursal.service";
import {RevisaoRepercussaoGeralService} from "./revisao-repercussao-geral.service";
import autuacaoRecursal from "../shared/recursal.module";

export class RevisaoRepercussaoGeralController {
    
	public basicForm: Object = {};
	public assuntosSelecionados : Array<Assunto> = [];
	public assunto : Assunto;
	public assuntos : Array<Assunto> = [];
	public tipoTese : string;
	public numeroTese : string;
	public teses : Array<Tese> = [];

	public cmd: RevisarRepercussaoGeralCommand = new RevisarRepercussaoGeralCommand();
	
	static $inject = ['$state', 'app.autuacao.recursal.RevisaoRepercussaoGeralService', 
	                  'app.autuacao.recursal.AutuacaoRecursalSharedService',
	                  'app.autuacao.recursal.AssuntoService', 'analise', 'tiposTese', 'messagesService'];
	
	constructor(private $state: IStateService, private revisaoService: RevisaoRepercussaoGeralService,
	   private autuacaoService: AutuacaoRecursalSharedService, private assuntoService : AssuntoService,
	   private analise, public tiposTese, private messagesService: app.support.messaging.MessagesService) {
	
	   this.cmd.processoId = analise.processoId;
	   if (analise.teses.length > 0){
		   analise.teses.forEach(tese => {
			   this.adicionarTese(<Tese> tese);
			   if (tese.assuntos.length > 0){
				   tese.assuntos.forEach(assunto =>{
		               this.adicionarAssuntoNaLista(<Assunto> assunto);
		           });
			   }
		   });
	   }
	}
	
	public pesquisaAssuntos(assunto : string) : void{
		this.assuntoService.listarAssuntos(assunto).then((assuntos : Assunto[])=> {
			this.assuntos = assuntos;
		});
	};
	
	public adicionarAssuntoNaLista (assunto : Assunto): void {
		let verificaSeAssuntoExiste : boolean = false;
		for (let i  of this.assuntosSelecionados){
			if (i.codigo == assunto.codigo){
				verificaSeAssuntoExiste = true;
				this.assunto = null;
			}
		}
		if (!verificaSeAssuntoExiste){
			this.assuntosSelecionados.push(assunto);
			this.cmd.assuntos.push(assunto.codigo);
			this.assunto = null;
		}
	}
	
	public removerAssuntosSelecionados ($index) : void{
		this.assuntosSelecionados.splice($index, 1);
		this.cmd.assuntos.splice($index, 1);
	}
	
	public consultarTese () : boolean {
		let soNumeros = /^[0-9]+$/g;
		
		if (!soNumeros.test(this.numeroTese)) {
			return false;
		}
		
		this.autuacaoService.consultarTeses(this.tipoTese, this.numeroTese).then((teses : Tese[]) => {
			 if (teses && teses.length > 0){
				let teseConsultada : Tese = teses[0];
				let assuntosConsultados : Array<Assunto> = teseConsultada.assuntos;  
				this.adicionarTese(teseConsultada);
				
				for (let i = 0; i < assuntosConsultados.length; i++) {
					this.adicionarAssuntoNaLista({codigo : assuntosConsultados[i].codigo, descricao : assuntosConsultados[i].descricao, nivel: 0});
				}
				
				this.numeroTese = null;
			 }
		});
		return true;
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
			this.cmd.teses.push(tese.codigo);
		}
	};
	
	public removerTese($index) : void{
		for (let i = 0; i < $index.assuntos.length; i++){
			let indice = this.assuntos.indexOf($index.assuntos[i]);
			this.assuntos.splice(indice, 1);
			this.cmd.assuntos.splice(indice, 1);
		}
		
		this.teses.splice($index,1);
		this.cmd.teses.splice($index,1);
	};
	
	
	public analisarRepercussaoGeral(): void {
	    this.revisaoService.revisar(this.cmd)
	         .then(() => {
            this.$state.go('app.tarefas.minhas-tarefas');
            this.messagesService.success('Revisão registrada com sucesso!');
         });
	};
}

autuacaoRecursal.controller("app.autuacao.recursal.RevisaoRepercussaoGeralController", RevisaoRepercussaoGeralController);
export default autuacaoRecursal;
