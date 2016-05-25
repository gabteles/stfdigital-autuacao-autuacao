import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import analise from './analise-pressupostos.module';

export class AnaliseCommand {
    
    constructor(public processoId: number, 
                public classificacao: boolean,
                public motivos: Array<number>,
                public observacao: string) { }    
}

export class AnalisePressupostosService {

    private static apiProcesso: string = '/autuacao/api/processos';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public analisar(processo: AnaliseCommand): IPromise<any> {
        return this.$http.post(this.properties.url + ":" + this.properties.port + AnalisePressupostosService.apiProcesso + '/analise-pressupostos', processo);
    }
    
    
}

analise.service('app.novo-processo.analise.AnalisePressupostosService', AnalisePressupostosService);

export default analise;