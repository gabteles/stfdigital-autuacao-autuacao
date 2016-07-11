export class Preferencia {
    constructor(public id: number, public nome: string) {}
}

export class Classe {
	constructor(public id: string, public nome: string,
			    public preferencias: Array<Preferencia>) {}
}

export class Parte {
    constructor(public apresentacao: string, 
    		    public pessoa?: number,
    		    public polo?: string) {}
}

export class Pessoa {
    constructor(public pessoaId: string, public nome: string) {}
}

export class Remessa {
    constructor(public classeSugerida: string, public qtdVolumes: number,
    		    public qtdApensos: number, public formaRecebimento: string,
    		    public numeroSedex: string) {}
}

export interface Processo {
    
	processoId: number;
    remessa: Remessa;
    status: string;
    classe: Classe;
    numero: number;
    identificacao: string;
    autuador: string;
    meioTramitacao: string;
    sigilo: string;
    partes: Array<Parte>;
    preferencias: Array<Preferencia>;
}
