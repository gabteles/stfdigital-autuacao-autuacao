import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import cmd = app.support.command;
import Properties = app.support.constants.Properties;
import {AutuarProcessoRecursalCommand, API_AUTUACAO_RECURSAL} from '../shared/recursal.model';
import autuacaoRecursal from '../shared/recursal.module';

export class AutuacaoCriminalEleitoralService {

    private api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties : Properties, commandService: cmd.CommandService) { 
    	this.api = properties.apiUrl.concat(API_AUTUACAO_RECURSAL);
    	commandService.addValidator('autuar-recursal-criminal-eleitoral', new ValidadorAutuacao());
    }

    /**
     * Realiza a atuação de um processo recursal criminal / eleitoral.
     * @param command
     */
    public autuar(command : AutuarProcessoRecursalCommand): IPromise<any> {
        return this.$http.post(this.api + '/autuacao-criminal-eleitoral', command);
    }   
    
}
class ValidadorAutuacao implements cmd.CommandValidator {
    
    constructor() {}
    
    public isValid(command: AutuarProcessoRecursalCommand): boolean {
        if (command.poloPassivo.length > 0 && command.poloAtivo.length > 0) {
            return true;
        }
        return false;
    }
}

autuacaoRecursal.service('app.autuacao.recursal.AutuacaoCriminalEleitoralService', AutuacaoCriminalEleitoralService);

export default autuacaoRecursal;