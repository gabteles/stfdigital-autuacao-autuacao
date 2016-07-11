import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import {Assunto, API_AUTUACAO_RECURSAL} from './recursal.model';
import autuacaoRecursal from "../shared/recursal.module";

export class AssuntoService {

    private api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties: Properties) {
    	this.api = properties.apiUrl.concat(API_AUTUACAO_RECURSAL); 
    }

    public listarAssuntos(assunto: string): IPromise<Assunto[]> {
    	return this.$http.get(this.api + '/assuntos', {params: {'termo' : assunto}})
			.then((response : ng.IHttpPromiseCallbackArg<Assunto[]>) => {
				return response.data;
			});
    }
}

autuacaoRecursal.service('app.autuacao.recursal.AssuntoService', AssuntoService);

export default autuacaoRecursal;