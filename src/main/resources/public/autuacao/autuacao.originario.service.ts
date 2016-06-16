import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacao from "./autuacao.module";

export class AutuacaoOriginarioCommand {
    
    constructor(public processoId: number, 
                public classeId: String,
                public poloAtivo: Array<Object>,
                public poloPassivo: Array<Object>) { }    
}

export class Processo{
	
	constructor (public processoId : number, public remessa : Object){}
}

export class AutuacaoOriginarioService {

    private static apiProcesso: string = '/autuacao/api/processos';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public autuar(processo: AutuacaoOriginarioCommand): IPromise<any> {
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoOriginarioService.apiProcesso + '/autuacao', processo);
    }
    
    public consultar(processoId : number) : IPromise<Processo> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + AutuacaoOriginarioService.apiProcesso + '/processo/' + processoId)
                .then((response: IHttpPromiseCallbackArg<Processo>) => { 
                    return response.data; 
                });
    }
    
}

autuacao.service('app.autuacao.autuacao.AutuacaoOriginarioService', AutuacaoOriginarioService);

export default autuacao;