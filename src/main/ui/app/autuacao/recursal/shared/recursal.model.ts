import Command = app.support.command.Command;
import {Processo, Remessa, Parte, Preferencia} from '../../shared/autuacao.model';

export const API_AUTUACAO_RECURSAL = '/autuacao/api/processos/recursal';

export class TipoTese {
    constructor(public id: string, public nome : string) {}
}

export class Tese {
	constructor(public codigo: number, public descricao: string,
			    public numero: number, public assuntos : Array<Assunto>,
			    public tipo: string) {}
}

export class Assunto {
    constructor(public codigo: string, public descricao: string, public nivel?: number) {}
}

export class MotivoInaptidao {
    constructor(public codigo: number, public descricao: string) {}
}

export interface ProcessoRecursal extends Processo {
	
    assuntos: Array<Assunto>;
    teses : Array<Tese>;
}

export interface AnalisePressupostosFormais extends ProcessoRecursal {
    
    processoApto: boolean;
    observacao: string;
    motivosInaptidao: Array<MotivoInaptidao>;
}

export interface AnaliseRepercussaoGeral extends ProcessoRecursal {
    
    temRepercussaoGeral: boolean;
    observacao: string;
    teses: Array<Tese>;
}

/**
 * Comando usado para autuar um processo recursal e o processo recursal criminal/eleitoral.
 */
export class AutuarProcessoRecursalCommand implements Command {
    
    public processoId: number;
    public assuntos: Array<string> = [];
    public poloAtivo: Array<String> = [];
    public poloPassivo: Array<String> = [];
    
    constructor() {}
}

/**
* Comando usado para analisar os pressupostos formais.
*/
export class AnalisarPressupostosFormaisCommand implements Command {
    
    public processoId: number;
    public processoApto: boolean = false;
    public motivosInaptidao: Array<number> = [];
    public observacao: string;
    
    constructor() {}
}

/**
* Comando usado para revisar os pressupostos formais.
*/
export class RevisarPressupostosFormaisCommand extends AnalisarPressupostosFormaisCommand {
	
    constructor() {
    	super();
    }
}

/**
* Comando usado para analisar a repercussão geral.
*/
export class AnalisarRepercussaoGeralCommand implements Command {
   
   public processoId: number;
   public assuntos: Array<string> = [];
   public teses: Array<number> = [];
   public observacao: string;
   public repercussaoGeral : boolean = false;
   
   constructor() {}
}

/**
* Comando usado para revisar a repercussão geral.
*/
export class RevisarRepercussaoGeralCommand extends AnalisarRepercussaoGeralCommand {

   constructor() {
	   super();
   }
}
