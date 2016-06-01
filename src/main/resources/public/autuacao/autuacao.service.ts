import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacao from "./autuacao.module";

export class AutuacaoCommand {
    
    constructor(public processoId: number, 
                public classeId: String,
                public poloAtivo: Array<Object>,
                public poloPassivo: Array<Object>) { }    
}

export class Processo{
	
	constructor (public processoId : number, public remessa : Object){}
}

export class AutuacaoService {

    private static apiProcesso: string = '/autuacao/api/processos';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public autuar(processo: AutuacaoCommand): IPromise<any> {
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoService.apiProcesso + '/autuacao', processo);
    }
    
    public consultar(processoId : number) : IPromise<Processo> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + AutuacaoService.apiProcesso + '/processo/' + processoId)
                .then((response: IHttpPromiseCallbackArg<Processo>) => { 
                    return response.data; 
                });
    }
    
}

autuacao.service('app.novo-processo.autuacao.AutuacaoService', AutuacaoService);

export default autuacao;