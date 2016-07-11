import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import {AnalisarRepercussaoGeralCommand, API_AUTUACAO_RECURSAL} from '../shared/recursal.model';
import autuacaoRecursal from '../shared/recursal.module';

export class AnaliseRepercussaoGeralService {

    private api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties: Properties) {
        this.api = properties.apiUrl.concat(API_AUTUACAO_RECURSAL);
    }

    /*
     * Realiza a análise da repercussão geral
     * @param command
     */
    public analisar(command : AnalisarRepercussaoGeralCommand): IPromise<any> {
        return this.$http.post(this.api + '/analise-repercussao-geral', command);
    }  
        
}

autuacaoRecursal.service('app.autuacao.recursal.AnaliseRepercussaoGeralService', AnaliseRepercussaoGeralService);
export default autuacaoRecursal;