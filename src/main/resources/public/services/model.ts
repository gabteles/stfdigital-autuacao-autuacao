export class Preferencia {
    public id: number;
    public nome: string;
    
    constructor(id: number, nome: string){
        this.id = id;
        this.nome = nome;
    }
}

export class Classe {
	public id: string;
    public nome: string;
    public preferencias: Array<Preferencia>;
    
    constructor(id: string, nome: string, preferencias: Array<Preferencia>) {
        this.id = id;
        this.nome = nome;
        this.preferencias = preferencias;
    }
}

export class Tese {
    public codigo: number;
    public descricao: string;
    public numero: number;
    public tipo: string;
    
    constructor(codigo: number, descricao: string, numero: number, tipo: string){
        this.codigo = codigo;
        this.descricao = descricao;
        this.numero = numero;
        this. tipo = tipo;
    }
}

export class Assunto {
    public codigo: string;
    public descricao: string;
    public assuntoPai: Assunto;
    
    constructor(codigo: string, descricao: string, assuntoPai: Assunto){
        this.codigo = codigo;
        this.descricao = descricao;
        this.assuntoPai = assuntoPai;
    }
}

export class Parte {
    public apresentacao: string;
    
    constructor(apresentacao: string){
        this.apresentacao = apresentacao;
    }
}

export class Processo {
    public processoId: number;
    public numero: string;
    public teses: Array<Tese>;
    public assuntos: Array<Assunto>;
    
    public constructor( processoId: number,  numero: string, teses: Array<Tese>, assuntos: Array<Assunto>){
        this.processoId = processoId;
        this.numero = numero;
        this.teses = teses;
        this.assuntos = assuntos;
    }
}

export class MotivoInaptidao {
	public id: number;
    public descricao: string;
    
    constructor(id: number, descricao: string) {
        this.id = id;
        this.descricao = descricao;
    }
}

export class TipoTese {
    public id: string;
    public descricao: string;

    constructor(id: string, descricao: string){
        this.id = id;
        this.descricao = descricao;
    }
}