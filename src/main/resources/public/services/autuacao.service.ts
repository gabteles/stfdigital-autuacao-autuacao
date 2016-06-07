import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacaoServices from "./services.module";
import {Tese, Assunto, Processo, MotivoInaptidao} from "./model";

/*
 * Comando usado para autuar um processo originário.
 * @author anderson.araujo
 * @since 31/05/2016 
 */
export class AutuarProcessoCommand {
    public processoId: number;
    public classeId: String;
    public poloAtivo: Array<string>;
    public poloPassivo: Array<string>;
    
    constructor(processoId: number, classeId: String, poloAtivo: Array<string>, poloPassivo: Array<string>) {
        this.processoId = processoId;
        this.classeId = classeId;
        this.poloAtivo = poloAtivo;
        this.poloPassivo = poloPassivo;
    }
}

/*
 * Comando usado para autuar um processo recursal.
 * @author anderson.araujo
 * @since 31/05/2016 
 */
export class AutuarProcessoRecursalCommand {
    public processoId: number;
    public assuntos: Array<string>;
    public poloAtivo: Array<string>;
    public poloPassivo: Array<string>;
    
    constructor(processoId: number, assuntos: Array<string>, poloAtivo: Array<string>, poloPassivo: Array<string>) {
        this.processoId = processoId;
        this.assuntos = assuntos;
        this.poloAtivo = poloAtivo;
        this.poloPassivo = poloPassivo;
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
    public consultarProcesso(processoId : number) : Processo {        
        let assuntos = new Array<Assunto>();
        assuntos.push(new Assunto("2620", "Rescisão de contrato de trabalho", null));
        assuntos.push(new Assunto("2622", "Aposentadoria e Pensão", null));
        
        let teses = new Array<Tese>();
        teses.push(new Tese(1781, "Agravo de instrumento contra decisão que não admitiu recurso extraordinário em que se discute...", 1, "xxx"));
        
        return new Processo(1, "RE 123/2016", teses, assuntos);
    }
    */
    public autuarProcessoOriginario(processoId: number, classeId: string, poloAtivo: Array<string>, 
        poloPassivo: Array<string>): IPromise<any>{
        let cmd = new AutuarProcessoCommand(processoId, classeId, poloAtivo, poloPassivo);
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoService.url + "/autuacao", cmd);
    }
    
    /*
     * Realiza a atuação de um processo recursal.
     * @param processoId Id do processo.
     * @param poloAtivo Partes do polo ativo do processo.
     * @param poloPassivo Partes do polo passivo do processo.
     * @param assuntos Assuntos relacionados ao processo.
     */
    public autuarProcessoRecursal(processoId: number, poloAtivo: Array<string>, poloPassivo: Array<string>, 
        assuntos: Array<Assunto>): IPromise<any> {
        let listaAssuntos = new Array<string>();    
        
        assuntos.forEach((assunto) => {
            listaAssuntos.push(assunto.codigo);
        });
        
        let cmd = new AutuarProcessoRecursalCommand(processoId, listaAssuntos, poloAtivo, poloPassivo);
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoService.url + 
            "/autuacao/recursal", cmd);
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
}

autuacaoServices.service("app.novo-processo.autuacao-services.AutuacaoService", AutuacaoService);

export default autuacaoServices;