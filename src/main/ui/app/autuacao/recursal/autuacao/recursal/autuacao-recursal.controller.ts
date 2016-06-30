import IStateService = angular.ui.IStateService;
import IStateParamService = angular.ui.IStateParamsService;
import IDialogService = angular.material.IDialogService;
import autuacaoRecursal from "./autuacao-recursal.module";
import {Tese, Assunto, Processo} from "../../../services/model";
import {AutuacaoService} from "../../../services/autuacao.service";

export class AutuarProcessoRecursalCommand {
    public processoId: number;
	public poloAtivo: Array<string>;
	public poloPassivo: Array<string>;
	public assuntos: Array<string>;
}

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
    public poloAtivo: Array<string> = [];
    public poloPassivo: Array<string> = [];
    public partePoloAtivo: string;
    public partePoloPassivo: string;
    
    /** @ngInject **/
    static $inject = ["$state", "$mdDialog", "app.autuacao.autuacao-services.AutuacaoService"];
    
    constructor(private $state: IStateService, private $mdDialog: IDialogService, private autuacaoService: AutuacaoService){
        
    	
		this.assuntos.push(new Assunto('4291', 'Jurisdição e Competência', null));
		this.assuntos.push(new Assunto('10912', 'Medidas Assecuratórias', null));
		this.teses.push(new Tese(170, 'Recurso extraordinário em que se discute', 1, null, 'REPERCUSSAO_GERAL'));
    	
        autuacaoService.consultarProcesso(1).then((processo: Processo) => {
			this.numeroProcesso = processo.numero;
            this.teses = processo.teses;
            this.assuntos = processo.assuntos;
            this.poloAtivo = new Array<string>();
            this.poloPassivo = new Array<string>();
            this.processoId = 1;
		});
        
        /*
        let processo = this.autuacaoService.consultarProcesso(1);
        this.numeroProcesso = processo.numero;
        this.teses = processo.teses;
        this.assuntos = processo.assuntos;
        this.poloAtivo = new Array<string>();
        this.poloPassivo = new Array<string>();
        this.processoId = 1;*/
    }
    
    /**
     * Exibe mensagens de alerta.
     * @param mensagem 
     */
    private exibirMensagem(mensagem: string, titulo: string){
        let alert = this.$mdDialog.alert().title(titulo).textContent(mensagem).ok("Fechar");
        this.$mdDialog.show(alert).finally(function() {
            alert = undefined;
        });
    }
    
    /**
     * Adiciona uma parte ao polo ativo.
     */
    public adicionarPartePoloAtivo(): void {
        for (let i = 0; i < this.poloAtivo.length; i++){
            if (this.poloAtivo[i] == this.partePoloAtivo.toUpperCase()){
                this.partePoloAtivo = "";
                this.exibirMensagem("A parte informada já foi adicionada ao polo ativo.", "Adicionar Parte");
                return;
            }
        }
        
        this.poloAtivo.push(this.partePoloAtivo.toUpperCase());
        this.partePoloAtivo = "";
    }
    
    /**
     * Remove uma parte do polo ativo.
     */
    public removerPartePoloAtivo(indice: number): void {
        this.poloAtivo.splice(indice);
    }
    
    /**
     * Adiciona uma parte ao polo passivo.
     */
    public adicionarPartePoloPassivo(): void {
        for (let i = 0; i < this.poloPassivo.length; i++){
            if (this.poloPassivo[i] == this.partePoloPassivo.toUpperCase()){
                this.partePoloPassivo = "";        
                this.exibirMensagem("A parte informada já foi adicionada ao polo passivo.", "Adicionar Parte");
                return;
            }
        }
        
        this.poloPassivo.push(this.partePoloPassivo.toUpperCase());
        this.partePoloPassivo = "";
    }
    
    /**
     * Remove uma parte do polo passivo.
     */
    public removerPartePoloPassivo(indice: number): void {
        this.poloPassivo.splice(indice);
    }
    
    /**
     * Realiza a autuação de um processo recursal.
     */
    public autuarProcessoRecursal(){
        this.autuacaoService.autuarProcessoRecursal(this.processoId, this.poloAtivo, this.poloPassivo, this.assuntos);
    }
}

autuacaoRecursal.controller("app.autuacao.autuacao-recursal.AutuacaoRecursalController", AutuacaoRecursalController);
export default autuacaoRecursal;