import {AutuacaoRecursalController} from "autuacao/recursal/autuacao-recursal/autuacao-recursal.controller";
import {AutuacaoRecursalService} from "autuacao/recursal/autuacao-recursal/autuacao-recursal.service";
import {AutuarProcessoRecursalCommand, Assunto} from "autuacao/recursal/shared/recursal.model";
import {Classe, Preferencia} from "autuacao/shared/autuacao.model";

describe('Teste do controlador autuacao-recursal.controller', () => {
	
    let controller : AutuacaoRecursalController;
	let $q : ng.IQService;
	let $rootScope : ng.IRootScopeService;
	let mockState;
	let mockAutuacaoRecursalService;
	let mockAssuntos;
	let mockTeses;
	let mockMessagesService;
	let mockProcesso;
	let mockStateParams;
	
	beforeEach(inject((_$q_, _$rootScope_) => {
	    $q = _$q_;
	    $rootScope = _$rootScope_;
	}));
	
	beforeEach(() => {
	    mockState = {
	        go : () => {}
	    };
	    mockAutuacaoRecursalService = {
	        autuar : () =>{}
	    };
	    mockAssuntos = {
	            assuntos : [new Assunto('4291', 'Jurisdição e Competência'), new Assunto('10912', 'Medidas Assecuratórias')]
	        };
	    mockProcesso = {
	            partes: [{apresentacao: 'FULANO DA SILVA', pessoa: 1, polo:'ATIVO'}, {apresentacao: 'SICRANO DA SILVA', pessoa: 1, polo:'PASSIVO'}, 
	                     {apresentacao: 'XPTO', pessoa: 2, polo:'ATIVO'}],
	            teses: [{
	                codigo: 1,
	                descricao: 'teste descricao',
	                numero: 170, 
	                assuntos: [{codigo: '4291', descricao: 'Jurisdição e Competência', nivel: 0}, {codigo: '10912', descricao:'Medidas Assecutórias', nivel: 0}],
	                tipo: 'REPERCUSSAO_GERAL'
	            }],
	            assuntos: [{codigo: '4291', descricao: 'Jurisdição e Competência', nivel: 0}, {codigo: '10912', descricao:'Medidas Assecutórias', nivel: 0}]
	    };
	    mockMessagesService = {
	        success: () => {}
	    };
        mockStateParams = {
                informationId: 1
        };
	   
	    controller = new AutuacaoRecursalController(mockState, mockStateParams , mockMessagesService, mockAutuacaoRecursalService, mockProcesso);
	});
	
	it('Deveria verificar a adição e remoção de partes do polo ativo', () => {
	    controller.partePoloAtivo = 'Tomas de Godoi';
	    controller.adicionarPartePoloAtivo()
		let adicionadosAtivo = controller.cmdAutuar.poloAtivo.length;
	    expect(adicionadosAtivo).toBeGreaterThan(0, 'Deveria adicionar uma nova parte do polo ativo');
		controller.removerPartePoloAtivo(0);
		expect(controller.cmdAutuar.poloAtivo.length).toEqual(adicionadosAtivo - 1, 'Deveria remover uma parte do polo ativo');
	});
	
   it('Deveria verificar a adição e removação de partes do polo passivo', () => {
        controller.partePoloPassivo = 'Paulo Moreira';
        controller.adicionarPartePoloPassivo();
        expect(controller.cmdAutuar.poloPassivo.length).toBeGreaterThan(0, 'Deveria adicionar uma nova parte do polo passivo');
       let adicionadosPassivo = controller.cmdAutuar.poloPassivo.length;
       controller.removerPartePoloPassivo(0);
       expect(controller.cmdAutuar.poloPassivo.length).toEqual(adicionadosPassivo - 1, 'Deveria remover uma parte do polo passivo');
    });
   
	it('Deveria realizar a ação de autuar o processo recursal', () => {
	    //controller.cmdAutuar = mockProcesso;
	    spyOn(mockAutuacaoRecursalService, 'autuar').and.callFake(() => $q.when());
	    spyOn(mockState, 'go').and.callThrough();
	    controller.autuarProcessoRecursal();
	    $rootScope.$apply();
	    expect(mockAutuacaoRecursalService.autuar).toHaveBeenCalledWith(controller.cmdAutuar);
	    expect(mockState.go).toHaveBeenCalledWith("app.tarefas.minhas-tarefas");
	});
	
});
