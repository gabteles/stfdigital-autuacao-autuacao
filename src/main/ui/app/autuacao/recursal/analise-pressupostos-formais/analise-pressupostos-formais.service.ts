import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import {AnalisarPressupostosFormaisCommand, API_AUTUACAO_RECURSAL} from '../shared/recursal.model';
import autuacaoRecursal from '../shared/recursal.module';

export class AnalisePressupostosFormaisService {

    private api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties: Properties, commandService: cmd.CommandService) {
    	this.api = properties.apiUrl.concat(API_AUTUACAO_RECURSAL);
    	commandService.addValidator("analisar-pressupostos-formais", new ValidadorAnalise());
    }

    public analisar(command: AnalisarPressupostosFormaisCommand): IPromise<any> {
        return this.$http.post(this.api + '/analise-pressupostos-formais', command);
    }
        
}

class ValidadorAnalise implements cmd.CommandValidator {
	
    constructor() {}
	
	public isValid(command: AnalisarPressupostosFormaisCommand): boolean {
		if (!command.processoApto && command.motivosInaptidao.length == 0) return false;
		return true;
	}
	
} 

autuacaoRecursal.service('app.autuacao.recursal.AnalisePressupostosFormaisService', AnalisePressupostosFormaisService);

export default autuacaoRecursal;