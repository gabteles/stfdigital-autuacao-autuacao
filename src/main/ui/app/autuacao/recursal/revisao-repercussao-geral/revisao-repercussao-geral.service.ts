import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import {AnaliseRepercussaoGeral, RevisarRepercussaoGeralCommand, API_AUTUACAO_RECURSAL} from '../shared/recursal.model';
import autuacaoRecursal from '../shared/recursal.module';

export class RevisaoRepercussaoGeralService {

    private api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties: Properties, commandService: cmd.CommandService) {
        this.api = properties.apiUrl.concat(API_AUTUACAO_RECURSAL);
        commandService.setValidator('revisar-repercussao-geral', new ValidadorRevisao());
    }
    
    /**
     * Recupera a análise da repercussão geral
     * @param processoId
     */
    public consultarAnaliseProcesso(processoId: number): IPromise<AnaliseRepercussaoGeral> {
        return this.$http.get(this.api + "/" + processoId + '/analise-repercussao-geral')
                    .then(response => response.data);
    }

    /**
     * Realiza a revisao da repercussão geral
     * @param command
     */
    public revisar(command : RevisarRepercussaoGeralCommand): IPromise<any> {
        return this.$http.post(this.api + '/revisao-repercussao-geral', command);
    }  
        
}

class ValidadorRevisao implements cmd.CommandValidator {
    
	public isValid(command: RevisarRepercussaoGeralCommand): boolean {
        if (command.teses.length === 0 && command.assuntos.length === 0) return false;
        return true;
    }
    
} 

autuacaoRecursal.service('app.autuacao.recursal.RevisaoRepercussaoGeralService', RevisaoRepercussaoGeralService);
export default autuacaoRecursal;