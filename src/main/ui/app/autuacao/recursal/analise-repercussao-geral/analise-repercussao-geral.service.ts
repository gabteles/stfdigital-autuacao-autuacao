import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import {AnalisarRepercussaoGeralCommand, API_AUTUACAO_RECURSAL} from '../shared/recursal.model';
import autuacaoRecursal from '../shared/recursal.module';

export class AnaliseRepercussaoGeralService {

    private api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties: Properties, commandService: cmd.CommandService) {
        this.api = properties.apiUrl.concat(API_AUTUACAO_RECURSAL);
        commandService.addValidator('analisar-repercussao-geral', new ValidadorAnalise());
    }

    /*
     * Realiza a análise da repercussão geral
     * @param command
     */
    public analisar(command : AnalisarRepercussaoGeralCommand): IPromise<any> {
        return this.$http.post(this.api + '/analise-repercussao-geral', command);
    }  
        
}

class ValidadorAnalise implements cmd.CommandValidator {
	
    constructor() {}
	
    public isValid(command: AnalisarRepercussaoGeralCommand): boolean {
        if (command.teses.length === 0 && command.assuntos.length === 0) return false;
        return true;
    }
} 

autuacaoRecursal.service('app.autuacao.recursal.AnaliseRepercussaoGeralService', AnaliseRepercussaoGeralService);
export default autuacaoRecursal;