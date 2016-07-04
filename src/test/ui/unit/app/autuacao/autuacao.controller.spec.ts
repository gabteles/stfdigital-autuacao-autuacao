import {AutuacaoController} from "autuacao/autuacao/autuacao.controller";
import {AutuacaoOriginarioService, AutuacaoOriginarioCommand, Processo} from "autuacao/autuacao/autuacao.originario.service";
import {Classe, Preferencia} from "autuacao/services/model";

describe('Teste do controlador autuacao.controller', () => {
	
	let controller : AutuacaoController;
	let $q : ng.IQService;
	let $rootScope : ng.IRootScopeService;
	let mockState;
	let mockAutuacaoOriginarioService;
	let mockMessagesService;
	
	beforeEach(inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
	}));
	
	beforeEach(() => {
		mockState = {
			go : () => {}
		};
		mockAutuacaoOriginarioService = {
			autuar : () =>{}
		};
		mockMessagesService = {
			success: () => {}
		};
		let protocoloId = 123;
		
	    controller = new AutuacaoController(mockState, mockAutuacaoOriginarioService, [new Classe('HC', 'Habeas Corpus', [new Preferencia(1,'Criminal')])], mockMessagesService);
	});
	
	
	
	it("Deveria verificar se o método está adicionando o mesmo nome na parte", () => {
		controller.partePoloAtivo = 'MARIA';
		controller.adicionarPartePoloAtivo();
		controller.partePoloAtivo = 'MARIA';
		expect(controller.cmdAutuar.poloAtivo.length).toEqual(2);
	});
	
	
	it('Deveria autuar processo originário como válido', () => {
		let protocoloId = 123;
		let classe = 'HC';
		let sigiloProcesso = 'PUBLICO';
		let motivo = 'Teste do motivo';
		let partePoloAtivo = 'Maria';
		let partePoloPassivo = 'Pedro';
		
		controller.cmdAutuar.classeId = controller.classes[0].id;
		controller.partePoloAtivo = partePoloAtivo;
		controller.adicionarPartePoloAtivo();
		controller.partePoloPassivo = partePoloPassivo;
		controller.adicionarPartePoloPassivo();
		controller.cmdAutuar.motivo = motivo;
		
		spyOn(mockAutuacaoOriginarioService, 'autuar').and.callFake(() => $q.when());
		
		spyOn(mockState, 'go').and.callThrough();
		
		controller.registrarAutuacao();
		
		$rootScope.$apply();
		
		expect(mockAutuacaoOriginarioService.autuar).toHaveBeenCalledWith(controller.cmdAutuar);
		
		expect(mockState.go).toHaveBeenCalledWith("app.tarefas.minhas-tarefas");
	});
	
});
