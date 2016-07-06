import IHttpService = angular.IHttpService;
import IPromise = angular.IPromise;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import autuacao from "./autuacao-recursal.module";
import cmd = app.support.command;
import Properties = app.support.constants.Properties;

export class ParteDto {
	public apresentacao : string;
	public pessoa : number;

	constructor (apresentacao : string , pessoa? : number) {
		this.apresentacao = apresentacao;
		this.pessoa = pessoa;
	}
}

/*
 * Comando usado para autuar um processo recursal.
 * @author anderson.araujo
 * @since 31/05/2016 
 */
export class AutuarProcessoRecursalCommand implements cmd.Command {
    constructor() {};
    public processoId: number;
    public assuntos: Array<string> = [];
    public poloAtivo: Array<ParteDto> = [];
    public poloPassivo: Array<ParteDto> = [];
}


export class AutuacaoRecursalService {

    private static api: string = '/autuacao/api/processos/recursal';

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties : Properties, commandService: cmd.CommandService) {
    	commandService.setValidator('autuar-processo-recursal', new ValidadorAutuacaoRecursal());
    }

    public autuarProcessoRecursal(command: AutuarProcessoRecursalCommand): IPromise<any> {
        return this.$http.post(this.properties.apiUrl + AutuacaoRecursalService.api + '/autuacao', command);
    }
    
}

class ValidadorAutuacaoRecursal implements cmd.CommandValidator {
	
	constructor() {}
	
	public isValid(command: AutuarProcessoRecursalCommand): boolean {
		if (command.poloPassivo.length > 0 && command.poloPassivo.length > 0) {
			return true;
		}
		return false;
	}
}

autuacao.service('app.autuacao.recursal.AutuacaoRecursalService', AutuacaoRecursalService);

export default autuacao;