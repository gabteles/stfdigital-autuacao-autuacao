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
	let mockAutuacaoService;
	let mockMessagesService;
	
/*	beforeEach(inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
	}));
	
	beforeEach(() => {
		mockState = {
			go : () => {}
		};
		mockAutuacaoRecursalService = {
			autuarProcessoRecursal : () =>{}
		};
		mockAutuacaoService = {
				consultarProcesso : () =>{}
			};
		mockMessagesService = {
			success: () => {}
		};
		let protocoloId = 123;
		
	    controller = new AutuacaoRecursalController(mockState, mockMessagesService, mockAutuacaoService, mockAutuacaoRecursalService);
	});
	
	it("Deveria verificar se o método está adicionando o mesmo nome na parte", () => {
		controller.partePoloAtivo = 'MARIA';
		controller.adicionarPartePoloAtivo();
		expect(controller.cmdAutuar.poloAtivo.length).toEqual(2);
	});
	
	
	it('Deveria autuar processo recursal', () => {
		let protocoloId = 123;
		let classe = 'HC';
		let sigiloProcesso = 'PUBLICO';
		let motivo = 'Teste do motivo';
		let partePoloAtivo = 'Maria';
		let partePoloPassivo = 'Pedro';
		
		controller.partePoloAtivo = partePoloAtivo;
		controller.adicionarPartePoloAtivo();
		controller.partePoloPassivo = partePoloPassivo;
		controller.adicionarPartePoloPassivo();
		controller.assuntos.push(new Assunto ('4291', 'Jurisdição e Competência', null));
		controller.assuntos.push(new Assunto('10912', 'Medidas Assecuratórias', null));
		
		spyOn(mockAutuacaoRecursalService, 'autuarProcessoRecursal').and.callFake(() => $q.when());
		
		spyOn(mockState, 'go').and.callThrough();
		
		controller.autuarProcessoRecursal();
		
		$rootScope.$apply();
		
		expect(mockAutuacaoRecursalService.autuarProcessoRecursal).toHaveBeenCalledWith(controller.cmdAutuar);
		
		expect(mockState.go).toHaveBeenCalledWith("app.tarefas.minhas-tarefas");
	});
*/
});
