import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacaoServices from "./services.module";
import {Tese, Assunto, Processo, MotivoInaptidao, Classe, TipoTese} from "./model";



/*
 * Comando usado para autuar um processo recursal criminal e eleitoral.
 * @author viniciusk
 * @since 07/06/2016 
 */
export class AutuarProcessoCriminalCommand {
    public processoId: number;
    public poloAtivo: Array<string>;
    public poloPassivo: Array<string>;
    public assuntos: Array<string>;
    
    constructor(processoId: number, poloAtivo: Array<string>, poloPassivo: Array<string>, assuntos: Array<string>) {
        this.processoId = processoId;
        this.poloAtivo = poloAtivo;
        this.poloPassivo = poloPassivo;
        this.assuntos = assuntos;
    }
}

/* Comando usado para analisar a repercussão geral.
* @author viniciusk
* @since 14/06/2016 
*/
export class AnalisarRepercussaoGeralCommand {
   public processoId: number;
   public assuntos: Array<string>;
   public teses: Array<number>;
   public observacao: string;
   
   constructor(processoId: number, assuntos: Array<string>, teses: Array<number>, observacao: string) {
       this.processoId = processoId;
       this.assuntos = assuntos;
       this.teses = teses;
       this.observacao = observacao;
   }
}

/* Comando usado para revisar a repercussão geral.
* @author viniciusk
* @since 15/06/2016 
*/
export class RevisarRepercussaoGeralCommand {
   public processoId: number;
   public assuntos: Array<string>;
   public teses: Array<number>;
   public observacao: string;
   public motivo : string;
   
   constructor(processoId: number, assuntos: Array<string>, teses: Array<number>, observacao: string) {
       this.processoId = processoId;
       this.assuntos = assuntos;
       this.teses = teses;
       this.observacao = observacao;
   }
}

/*
 * Comando usado para revisar uma análise de pressupostos formais.
 * @author anderson.araujo
 * @since 33/05/2016 
 */
export class RevisarAnalisePressupostosCommand {
    public processoId: number; 
    public classificacao: boolean;
    public motivos: Array<number>;
    public observacao: string
    
    constructor(processoId: number, classificacao: boolean, motivos: Array<number>, observacao: string) { 
        this.processoId = processoId;
        this.classificacao = classificacao;
        this.motivos = motivos;
        this.observacao = observacao;
    }
}

/*
 * Serviço de autuação de processos.
 * @author anderson.araujo
 * @since 31/05/2016
 */
export class AutuacaoService {

    private static url: string = "/autuacao/api/processos";

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }
    
    public consultarProcesso(processoId : number) : IPromise<Processo> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + AutuacaoService.url + "/processo/" + processoId)
                .then((response: IHttpPromiseCallbackArg<Processo>) => { 
                    return response.data; 
                });
    }
    
    
    /*
     * Envia para o back-end um comando para a realização de uma revisão de uma dada análise de pressupostos formais.
     * @param processoId Id do processo.
     * @param apto Se true indica que o processo está ápto.
     * @param motivos Motivos da análise.
     * @param observacao Observação feita pelo revisor da análise.
     */
    public revisarAnalisePressupostos(processoId: number, apto: boolean, motivos: Array<number>, observacao: string): IPromise<any> {
        let cmd = new RevisarAnalisePressupostosCommand(processoId, apto, motivos, observacao);
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoService.url + "/revisao-analise-pressupostos", cmd);
    }
    
    /*
     * Realiza a atuação de um processo recursal criminal / eleitoral.
     * @param processoId Id do processo.
     * @param poloAtivo Partes do polo ativo do processo.
     * @param poloPassivo Partes do polo passivo do processo.
     * @param assuntos Assuntos relacionados ao processo.
     */
    public autuarProcessoCriminalEleitoral(autuarProcessoCriminalCommand : AutuarProcessoCriminalCommand): IPromise<any> {
        let cmd = autuarProcessoCriminalCommand;
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoService.url + '/autuacao/criminal', cmd);
    }
    
    /*
     * Realiza a análise da repercussão geral
     * @param processoId Id do processo.
     * @param assuntos Assuntos relacionados ao processo.
     * @param teses lista dos códigos da teses selecionadas.
     * @param observacao Observação da análise da repercussão geral.
     */
    public analisarRepercussaoGeral(analisarRepercussaoGeralCommand : AnalisarRepercussaoGeralCommand): IPromise<any> {
        let cmd = analisarRepercussaoGeralCommand;
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoService.url + '/autuacao/recursal/repercussao/analise', cmd);
    }  
    
    /*
     * Realiza a revisão da repercussão geral.
     * @param processoId Id do processo.
     * @param assuntos Assuntos relacionados ao processo.
     * @param teses lista dos códigos da teses selecionadas.
     * @param motivo Observação do motivo da repercussão geral.
     * @param observacao Observação da análise da repercussão geral.
     */
    public revisarRepercussaoGeral(revisarRepercussaoGeralCommand : RevisarRepercussaoGeralCommand): IPromise<any> {
        let cmd = revisarRepercussaoGeralCommand;
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoService.url + '/autuacao/recursal/repercussao/revisao', cmd);
    }  
    
    /**
     * Retorna a lista de Motivos de inaptidão de processos.
     * @return Lista de motivos de inaptidão de processos.
     */
	public listarMotivosInaptidao() : IPromise<MotivoInaptidao[]> {
		return this.$http.get(this.properties.url + ":" + this.properties.port + AutuacaoService.url + "/motivoinaptidao")
        .then((response: IHttpPromiseCallbackArg<MotivoInaptidao[]>) => { 
            return response.data; 
        });
	}
    
    /**
     * Retorna as classes processuais.
     * @return Array de classes.
     */
    public listarClasses() : IPromise<Classe[]> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + "/autuacao/api/processos/classe")
            .then((response: IHttpPromiseCallbackArg<Classe[]>) => { 
                    return response.data; 
            });
    }
    
    /**
     * Retorna os tipos de tese.
     * @return Array de tipo de teses.
     */
    public listarTiposTese() : IPromise<TipoTese[]> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + "/autuacao/api/processos/tipotese")
            .then((response: IHttpPromiseCallbackArg<TipoTese[]>) => { 
                    return response.data; 
            });
    }
    
    /**
     * Retorna as teses.
     * @return Array de teses.
     */
    public consultarTeses(tipoTese: string, numeroTese : string) : IPromise<Tese[]> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + "/autuacao/api/processos/teses", {params: {'tipo' : tipoTese, 'numero': numeroTese}})
            .then((response: IHttpPromiseCallbackArg<Tese[]>) => { 
                    return response.data; 
            });
    }
        

    /**
     * Retorna os tipos de tese.
     * @return Array de tipos de tese.
     */
    public listarTiposTeses() : Array<TipoTese> {
        let tiposTese = new Array<TipoTese>();
        tiposTese.push(new TipoTese("CONTROVERSIA", "Controvérsia"));
        tiposTese.push(new TipoTese("PRE_TEMA", "Pré-tema"));
        tiposTese.push(new TipoTese("REPERCUSSAO_GERAL", "Repercussão Geral"));

        return tiposTese;
    }    
}

autuacaoServices.service("app.autuacao.autuacao-services.AutuacaoService", AutuacaoService);

export default autuacaoServices;