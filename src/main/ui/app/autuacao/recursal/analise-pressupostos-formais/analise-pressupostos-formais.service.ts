import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import analise from './analise-pressupostos-formais.module';

export class AnalisePressupostosFormaisCommand {
    
    constructor(public processoId: number, 
                public classificacao: boolean,
                public motivos: Array<number>,
                public observacao: string) { }    
}

export class AnalisePressupostosFormaisService {

    private static apiProcesso: string = '/autuacao/api/processos';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties: Properties) { }

    public analisar(command: AnalisePressupostosFormaisCommand): IPromise<any> {
        return this.$http.post(this.properties.apiUrl + AnalisePressupostosFormaisService.apiProcesso + '/analise-pressupostos', command);
    }
    
    
}

analise.service('app.autuacao.analise.AnalisePressupostosService', AnalisePressupostosService);

export default analise;