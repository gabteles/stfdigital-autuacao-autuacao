import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import {Tese, Assunto, MotivoInaptidao, TipoTese, API_AUTUACAO_RECURSAL, ProcessoRecursal} from "./recursal.model";
import {Processo, Classe, Parte} from "../../shared/autuacao.model";
import autuacaoRecursal from "../shared/recursal.module";

/**
 * Serviço de autuação de processos.
 * @author anderson.araujo
 * @since 31/05/2016
 */
export class AutuacaoRecursalSharedService {

	public api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties: Properties) {
    	this.api = properties.apiUrl + API_AUTUACAO_RECURSAL;
    }
    
    /**
     * Retorna a lista de Motivos de inaptidão de processos.
     * @return Lista de motivos de inaptidão de processos.
     */
	public listarMotivosInaptidao() : IPromise<MotivoInaptidao[]> {
		return this.$http.get(this.api + "/motivos-inaptidao")
        .then((response: IHttpPromiseCallbackArg<MotivoInaptidao[]>) => { 
            return response.data; 
        });
	}
    
    /**
     * Retorna os tipos de tese.
     * @return Array de tipo de teses.
     */
    public listarTiposTese() : IPromise<Array<TipoTese>> {
        return this.$http.get(this.api + "/tipos-tese")
            .then((response: IHttpPromiseCallbackArg<Array<TipoTese>>) => { 
                    return response.data; 
            });
    }
    
    /**
     * Retorna as teses.
     * @return Array de teses.
     */
    public consultarTeses(tipoTese: string, numeroTese : string) : IPromise<Array<Tese>> {
        return this.$http.get(this.api + "/teses", {params: {'tipo' : tipoTese, 'numero': numeroTese}})
            .then((response: IHttpPromiseCallbackArg<Array<Tese>>) => { 
                    return response.data; 
            });
    }
    
    public consultarProcesso(idProcesso : number) : IPromise<Array<Processo>> {
    	return this.$http.get(this.api + "/" + idProcesso)
    	   .then((response: IHttpPromiseCallbackArg<Array<Processo>>) => {
    		   return response.data;
    	   });
    }
    
    public consultarProcessoRecursal(idProcesso : number) : IPromise<Array<ProcessoRecursal>> {
        return this.$http.get(this.api + "/" + idProcesso)
           .then((response: IHttpPromiseCallbackArg<Array<ProcessoRecursal>>) => {
               return response.data;
           });
    }
    
}

autuacaoRecursal.service("app.autuacao.recursal.AutuacaoRecursalSharedService", AutuacaoRecursalSharedService);

export default autuacaoRecursal;