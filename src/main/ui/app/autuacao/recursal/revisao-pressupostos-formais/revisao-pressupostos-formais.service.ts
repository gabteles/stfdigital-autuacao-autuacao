import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import {AnalisePressupostosFormais, RevisarPressupostosFormaisCommand, API_AUTUACAO_RECURSAL} from '../shared/recursal.model';
import autuacaorecursal from '../shared/recursal.module';

export class RevisaoPressupostosFormaisService {

    private api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties: Properties,  commandService: cmd.CommandService) {
        this.api = properties.apiUrl.concat(API_AUTUACAO_RECURSAL);
        commandService.setValidator('revisar-pressupostos-formais', new ValidadorRevisao());
    }
    
    /**
     * Recupera a análise dos pressupostosFormais
     * @param processoId
     */
    public consultarAnaliseProcesso(processoId: number): IPromise<AnalisePressupostosFormais> {
        return this.$http.get(this.api + "/" + processoId + '/analise-pressupostos-formais')
                    .then(response => response.data);
    }

    public revisar(command: RevisarPressupostosFormaisCommand): IPromise<any> {
        return this.$http.post(this.api + '/revisao-pressupostos-formais', command);
    }
        
}

class ValidadorRevisao implements cmd.CommandValidator {
    
    public isValid(command: RevisarPressupostosFormaisCommand): boolean {
        if (!command.processoApto && command.motivosInaptidao.length == 0) return false;
        return true;
    }
    
} 

autuacaorecursal.service('app.autuacao.recursal.RevisaoPressupostosFormaisService', RevisaoPressupostosFormaisService);

export default autuacaorecursal;