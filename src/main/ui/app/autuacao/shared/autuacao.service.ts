import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import {Processo, Classe, Preferencia} from "./autuacao.model";
import autuacao from "./autuacao.module";

/*
 * Serviço de autuação de processos.
 * @author anderson.araujo
 * @since 31/05/2016
 */
export class AutuacaoSharedService {

    private api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties: Properties) {
    	this.api = properties.apiUrl + "/autuacao/api/processos";
    }
    
    /**
     * Retorna um processo
     */
    public consultarProcesso(processoId : number) : IPromise<Processo> {
        return this.$http.get(this.api + "/" + processoId)
                .then((response: IHttpPromiseCallbackArg<Processo>) => { 
                    return response.data; 
                });
    }
    
    /**
     * Retorna as classes processuais.
     */
    public listarClasses() : IPromise<Classe[]> {
        return this.$http.get(this.api + "/classes")
            .then((response: IHttpPromiseCallbackArg<Classe[]>) => { 
                    return response.data; 
            });
    }
    
    /**
     * Retorna as preferencias
     */
    public listarPreferencias() : IPromise<Preferencia[]> {
        return this.$http.get(this.api + "/preferencias")
            .then((response: IHttpPromiseCallbackArg<Preferencia[]>) => { 
                    return response.data; 
            });
    }
}

autuacao.service("app.autuacao.AutuacaoSharedService", AutuacaoSharedService);
export default autuacao;