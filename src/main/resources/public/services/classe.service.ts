import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacaoServices from "./services.module";
import {Classe, Preferencia} from "./model";

export class ClasseService {

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public listar(tipoRemessa: string) : IPromise<Classe[]> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + "/autuacao/api/processos/classe")
            .then((response: IHttpPromiseCallbackArg<Classe[]>) => { 
                    return response.data; 
            });
    }
}

autuacaoServices.service("app.novo-processo.autuacao-services.ClasseService", ClasseService);
export default autuacaoServices;