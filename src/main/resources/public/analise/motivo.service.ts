import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import analise from './analise-pressupostos.module';

export class Motivo {
	constructor(public id : number , public descricao : string) {}
}

export class MotivoService {
	
	private static apiProcessos : string = '/autuacao/api/processos';
	
	 /** @ngInject **/
	constructor(private $http, private properties) {}
	
	public listar() : IPromise<Motivo[]> {
		return this.$http.get(this.properties.url + ":" + this.properties.port + MotivoService.apiProcessos + '/motivoinaptidao')
        .then((response: IHttpPromiseCallbackArg<Motivo[]>) => { 
            return response.data; 
        });
	}
}

analise.service('app.novo-processo.analise.MotivoService', MotivoService);
export default analise;