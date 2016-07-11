import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import {Tese, Assunto, MotivoInaptidao, TipoTese, API_AUTUACAO_RECURSAL} from "./recursal.model";
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
		return this.$http.get(this.api + "/motivoinaptidao")
        .then((response: IHttpPromiseCallbackArg<MotivoInaptidao[]>) => { 
            return response.data; 
        });
	}
    
    /**
     * Retorna as classes processuais.
     * @return Array de classes.
     */
    public listarClasses() : IPromise<Classe[]> {
        return this.$http.get(this.api + "/classes")
            .then((response: IHttpPromiseCallbackArg<Classe[]>) => { 
                    return response.data; 
            });
    }
    
    /**
     * Retorna os tipos de tese.
     * @return Array de tipo de teses.
     */
    public listarTiposTese() : IPromise<TipoTese[]> {
        return this.$http.get(this.api + "/tipostese")
            .then((response: IHttpPromiseCallbackArg<TipoTese[]>) => { 
                    return response.data; 
            });
    }
    
    /**
     * Retorna as teses.
     * @return Array de teses.
     */
    public consultarTeses(tipoTese: string, numeroTese : string) : IPromise<Tese[]> {
        return this.$http.get(this.api + "/teses", {params: {'tipo' : tipoTese, 'numero': numeroTese}})
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

autuacaoRecursal.service("app.autuacao.recursal.AutuacaoRecursalSharedService", AutuacaoRecursalSharedService);

export default autuacaoRecursal;