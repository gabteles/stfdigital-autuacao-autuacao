import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacao from './autuacao-criminal.module';

export class AutuarRecursalCommand {
    
    constructor(public processoId: number, 
                public classificacao: boolean,
                public assuntos: Array<string>,
                public observacao: string) { }    
}

export class AutuacaoCriminalService {

    private static apiProcesso: string = '/autuacao/api/processos';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public autuar(processo: AutuarRecursalCommand): IPromise<any> {
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoCriminalService.apiProcesso + '/analise-pressupostos', processo);
    }
    
    
}

autuacao.service('app.autuacao.criminal.AutuacaoCriminalService', AutuacaoCriminalService);

export default autuacao;