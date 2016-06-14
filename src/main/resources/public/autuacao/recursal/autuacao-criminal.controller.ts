import {AutuarProcessoCriminalCommand, AutuacaoService} from "../../services/autuacao.service";
import IStateService = angular.ui.IStateService;
import IScope = angular.IScope
import IStateParamService = angular.ui.IStateParamsService;
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import {Assunto} from "../../services/model";
import {AssuntoDto, AssuntoService} from "../../services/assunto.service";
import "../../services/assunto.service";
import analise from "./autuacao-criminal.module";

/**
 * @author viniciusk
 */

export class ParteDto{
	
	public apresentacao : string;
	public pessoa : number;

	constructor (apresentacao : string , pessoa? : number) {
		this.apresentacao = apresentacao;
		this.pessoa = pessoa;
	}
}


export class AutuacaoCriminalController {
	
	public basicForm: Object = {};
	public partePoloAtivo: string;
	public partePoloPassivo: string;
	public partesPoloAtivo: Array<ParteDto> = new Array<ParteDto>();
	public partesPoloPassivo: Array<ParteDto> = new Array<ParteDto>();
	public apto : boolean = false;
	public assuntosSelecionados : Array<AssuntoDto> = [];
	public observacao : string;
	public peticao : Object = {};
	public assunto : Assunto;
	public assuntos : Array<AssuntoDto> = [];

	static $inject = ['$state', 'app.novo-processo.autuacao-services.AutuacaoService', 
	                  'app.novo-processo.autuacao-services.AssuntoService', '$stateParams', 'properties', '$scope', '$http'];
	
    constructor(private $state: IStateService, private autuacaoService: AutuacaoService, private assuntoService : AssuntoService,
    		private $stateParams : IStateParamService, private properties, private $scope : IScope, private $http : IHttpService ) {
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
    		this.assunto = null
    	}
    }
    
    public removerAssuntosSelecionados ($index) : void{
    	this.assuntosSelecionados.splice($index, 1);
    }
    
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.partesPoloAtivo.length; i++){
            if (this.partesPoloAtivo[i].apresentacao == this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                return;
            }
        }
        let parte = new ParteDto(this.partePoloAtivo.toUpperCase());
        this.partesPoloAtivo.push(parte);
        this.partePoloAtivo = "";
    }
    
    public removerPartePoloAtivo(indice: number): void {
        this.partesPoloAtivo.splice(indice);
    }
    
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.partesPoloPassivo.length; i++){
            if (this.partesPoloPassivo[i].apresentacao == this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                return;
            }
        }
        
        let parte = new ParteDto(this.partePoloPassivo.toUpperCase())
        this.partesPoloPassivo.push(parte);
        this.partePoloPassivo = "";
    }
    
    public removerPartePoloPassivo(indice: number): void {
        this.partesPoloPassivo.splice(indice);
    }
    
    
	public autuarCriminalEleitoral(): void {
	    this.autuacaoService.autuarProcessoCriminalEleitoral(this.commandAutuacaoCriminal(1, this.partesPoloAtivo, this.partesPoloPassivo, this.assuntos))
	        .then(() => {
	            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
	    });
	};

	private commandAutuacaoCriminal(processoId : number, partesAtivo : Array<ParteDto>, partesPassivo : Array<ParteDto>, assuntosC : Array<AssuntoDto>): AutuarProcessoCriminalCommand {
		let assuntosCommand : string[];
		for(let assunto of assuntosC){
			assuntosCommand.push(assunto.codigo);
		}
		
		let partesAtivaCommand : string[];
		for(let parte of partesAtivo){
			partesAtivaCommand.push(parte.apresentacao);
		}
		
		let partesPassivaCommand : string[];
		for(let parte of partesPassivo){
			partesPassivaCommand.push(parte.apresentacao);
		}
		
	    return new AutuarProcessoCriminalCommand(1, partesAtivaCommand, partesPassivaCommand, assuntosCommand);
	};
	
}

analise.controller('app.novo-processo.criminal.AutuacaoCriminalController', AutuacaoCriminalController);
export default analise;