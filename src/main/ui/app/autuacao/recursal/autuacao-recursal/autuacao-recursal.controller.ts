import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
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
    public assuntos: Array<Assunto> = [];
    public partePoloAtivo: string;
    public partePoloPassivo: string;
    
    public cmdAutuar : AutuarProcessoRecursalCommand = new AutuarProcessoRecursalCommand();
    
    /** @ngInject **/
    static $inject = ["$state", "messagesService", "app.autuacao.AutuacaoSharedService", "app.autuacao.recursal.AutuacaoRecursalService"];
    
    constructor(private $state: IStateService, private messagesService: app.support.messaging.MessagesService, private autuacaoService: AutuacaoSharedService,
    		private autuacaoRecursalService : AutuacaoRecursalService){
    	
		this.assuntos.push(new Assunto('4291', 'Jurisdição e Competência', null));
		this.assuntos.push(new Assunto('10912', 'Medidas Assecuratórias', null));
		this.teses.push(new Tese(170, 'Recurso extraordinário em que se discute', 1, null, 'REPERCUSSAO_GERAL'));
    	
		let parteAtiva = new Parte('JOSÉ DE SOUZA', 2);
		this.cmdAutuar.poloAtivo.push(parteAtiva);
		let partePassiva = new Parte('ALINE PEREIRA', 3);
		this.cmdAutuar.poloPassivo.push(partePassiva);
		this.processoId = 1;
	
 /*       autuacaoService.consultarProcesso(1).then((processo: Processo) => {
			this.numeroProcesso = processo.numero;
            this.teses = processo.teses;
            this.assuntos = processo.assuntos;
            this.processoId = 1;
		}); */ 
    }
   
    
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.cmdAutuar.poloAtivo.length; i++){
            if (this.cmdAutuar.poloAtivo[i].apresentacao == this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                return;
            }
        }
        let parte = new Parte(this.partePoloAtivo.toUpperCase());
        this.cmdAutuar.poloAtivo.push(parte);
        this.partePoloAtivo = "";
    }
    
    public removerPartePoloAtivo(indice: number): void {
    	this.cmdAutuar.poloAtivo.splice(indice);
    }
    
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.cmdAutuar.poloPassivo.length; i++){
            if (this.cmdAutuar.poloPassivo[i].apresentacao == this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                return;
            }
        }
        
        let parte = new Parte(this.partePoloPassivo.toUpperCase())
        this.cmdAutuar.poloPassivo.push(parte);
        this.partePoloPassivo = "";
    }
    
    public removerPartePoloPassivo(indice: number): void {
    	this.cmdAutuar.poloPassivo.splice(indice);
    }
    
    /**
     * Realiza a autuação de um processo recursal.
     */
    public autuarProcessoRecursal(){
    	
    	for (let i  of this.assuntos){
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

autuacaoRecursal.controller("app.autuacao.autuacao-recursal.AutuacaoRecursalController", AutuacaoRecursalController);
export default autuacaoRecursal;