import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import cmd = app.support.command;
import Properties = app.support.constants.Properties;
import {Parte} from "../shared/autuacao.model";
import autuacaoOriginario from "./autuacao-originario.module";

export class AutuacaoOriginarioCommand implements cmd.Command {
    
    public processoId: number;
    public classeId: String;
	public poloAtivo: Array<Parte> = new Array();
    public poloPassivo: Array<Parte> = new Array();
	public motivo : string;
    public valida : boolean = true;
    
    constructor() {}
}

export class AutuacaoOriginarioService {

    private api: string;

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties : Properties, commandService: cmd.CommandService) {
    	this.api = properties.apiUrl.concat('/autuacao/api/processos/originario');
    	commandService.addValidator("autuar-originario", new ValidadorAutuacao());
    }

    public autuar(cmd: AutuacaoOriginarioCommand): IPromise<any> {
        return this.$http.post(this.api + '/autuacao', cmd);
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

autuacaoOriginario.service('app.autuacao.originario.AutuacaoOriginarioService', AutuacaoOriginarioService);

export default autuacaoOriginario;