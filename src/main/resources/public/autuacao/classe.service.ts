import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacao from "./autuacao.module";

export class Classe {
	constructor(public id : string, public nome: string )	{}
}

export class ClasseService {

    private static apiProcessos: string = '/autuacao/api/processos';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public listar() : IPromise<Classe[]> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + ClasseService.apiProcessos + '/classe')
                .then((response: IHttpPromiseCallbackArg<Classe[]>) => { 
                    return response.data; 
                });
    }
}

autuacao.service('app.novo-processo.autuacao.ClasseService', ClasseService);
export default autuacao;