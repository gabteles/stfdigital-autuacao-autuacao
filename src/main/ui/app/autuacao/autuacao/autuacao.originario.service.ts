import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacao from "./autuacao.module";
import cmd = app.support.command;
import Properties = app.support.constants.Properties;

export class ParteDto{
	public apresentacao : string;
	public pessoa : number;

	constructor (apresentacao : string , pessoa? : number) {
		this.apresentacao = apresentacao;
		this.pessoa = pessoa;
	}
}

export class AutuacaoOriginarioCommand {
    
    public processoId: number;
    public classeId: String;
	public poloAtivo: Array<ParteDto> = [];
    public poloPassivo: Array<ParteDto> = []
	public motivo : string;
    public valida : boolean;
	
    constructor (){};
}


export class Processo{	constructor (public processoId : number, public remessa : Object){}
}

export class AutuacaoOriginarioService {

    private static apiProcesso: string = '/autuacao/api/processos';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties : Properties, commandService: cmd.CommandService) {
    	commandService.setValidator('preautuar-recursal', new ValidadorAutuacao());
    }

    public autuar(cmd: AutuacaoOriginarioCommand): IPromise<any> {
        return this.$http.post(this.properties.url + ":" + this.properties.port + AutuacaoOriginarioService.apiProcesso + '/autuacao', cmd);
    }
    
    public consultar(processoId : number) : IPromise<Processo> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + AutuacaoOriginarioService.apiProcesso + '/processo/' + processoId)
                .then((response: IHttpPromiseCallbackArg<Processo>) => { 
                    return response.data; 
                });
    }
}

class ValidadorAutuacao implements cmd.CommandValidator {
	
	constructor() {}
	
	public isValid(command: AutuacaoOriginarioCommand): boolean {
		if (angular.isString(command.classeId) &&
			command.poloAtivo.length > 0 && command.poloPassivo.length > 0) {
			return true;
		}
		return false;
	}
}

autuacao.service('app.autuacao.autuacao.AutuacaoOriginarioService', AutuacaoOriginarioService);

export default autuacao;