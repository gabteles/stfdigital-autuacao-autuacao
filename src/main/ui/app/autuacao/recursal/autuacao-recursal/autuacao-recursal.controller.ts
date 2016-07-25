import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import IDialogService = angular.material.IDialogService;
import {Parte} from "../../shared/autuacao.model";
import {Tese, Assunto, ProcessoRecursal, AutuarProcessoRecursalCommand} from "../shared/recursal.model";
import {AutuacaoRecursalService} from "./autuacao-recursal.service";
import {AutuacaoSharedService} from "../../shared/autuacao.service";
import autuacaoRecursal from "../shared/recursal.module";


/*
 * Comando usado para enviar os dados do processo recursal para a autuação.
 * 
 * @author anderson.araujo
 * @since 31/05/2016
 */
export class AutuacaoRecursalController {
    
    public processoId: number;
    public numeroProcesso: string;
    public teses: Array<Tese> = [];
    public partePoloAtivo: string;
    public partePoloPassivo: string;
    
    public cmdAutuar : AutuarProcessoRecursalCommand = new AutuarProcessoRecursalCommand();
    
    /** @ngInject **/
    static $inject = ['$state', '$stateParams', 'messagesService', 'app.autuacao.AutuacaoSharedService', 'app.autuacao.recursal.AutuacaoRecursalService', 'processo'];
    
    constructor(private $state: IStateService, private $stateParams: IStateParamsService, private messagesService: app.support.messaging.MessagesService, private autuacaoService: AutuacaoSharedService,
    		private autuacaoRecursalService : AutuacaoRecursalService, public processo){
    	
    	this.cmdAutuar.processoId = $stateParams['informationId'];
    	if (processo.partes.length > 0){
    		processo.partes.forEach(parte =>{
    			if (parte.polo === 'ATIVO'){
    				this.cmdAutuar.poloAtivo.push(parte);
    			}else{
    				this.cmdAutuar.poloPassivo.push(parte);
    			}
    		});
    	}
    	
    }
   
    
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.cmdAutuar.poloAtivo.length; i++){
            if (this.cmdAutuar.poloAtivo[i] === this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                return;
            }
        }
        this.cmdAutuar.poloAtivo.push(this.partePoloAtivo.toUpperCase());
        this.partePoloAtivo = "";
    }
    
    public removerPartePoloAtivo(indice: number): void {
    	this.cmdAutuar.poloAtivo.splice(indice);
    }
    
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.cmdAutuar.poloPassivo.length; i++){
            if (this.cmdAutuar.poloPassivo[i] === this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                return;
            }
        }
        
        this.cmdAutuar.poloPassivo.push(this.partePoloPassivo.toUpperCase());
        this.partePoloPassivo = "";
    }
    
    public removerPartePoloPassivo(indice: number): void {
    	this.cmdAutuar.poloPassivo.splice(indice);
    }
    
    /**
     * Realiza a autuação de um processo recursal.
     */
    public autuarProcessoRecursal(){
    	
    	for (let i  of this.processo.assuntos){
    		this.cmdAutuar.assuntos.push(i.codigo);
    	}
    	
        this.autuacaoRecursalService.autuar(this.cmdAutuar).then(() => {
            this.$state.go('app.tarefas.minhas-tarefas');
			this.messagesService.success('Processo autuado com sucesso.');
    	}, () => {
			this.messagesService.error('Erro ao autuar o processo.');
		});
    }
}

autuacaoRecursal.controller("app.autuacao.recursal.AutuacaoRecursalController", AutuacaoRecursalController);
export default autuacaoRecursal;