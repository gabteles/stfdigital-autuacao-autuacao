import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacaoServices from "./services.module";

export class AssuntoDto{
	public codigo : string;
	public descricao : string
	public nivel : number;
	
	constructor(codigo: string, descricao : string, nivel : number){
		this.codigo = codigo;
		this.descricao = descricao;
		this.nivel = nivel;
	}
}

export class AssuntoService {

    private static apiProcesso: string = '/autuacao/api/processos';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { }

    public listarAssuntos(assunto: string): IPromise<AssuntoDto[]> {
    	return this.$http.get(this.properties.url + ":" + this.properties.port + AssuntoService.apiProcesso + '/assuntos', {params: {'termo' : assunto}})
			.then((response : ng.IHttpPromiseCallbackArg<AssuntoDto[]>) => {
				return response.data;
			});
    }
}

autuacaoServices.service('app.novo-processo.autuacao-services.AssuntoService', AssuntoService);

export default autuacaoServices;