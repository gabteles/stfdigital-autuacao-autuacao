import {RevisaoPressupostosFormaisController} from "autuacao/recursal/revisao-pressupostos-formais/revisao-pressupostos-formais.controller";
import {AutuarProcessoRecursalCommand, MotivoInaptidao} from "autuacao/recursal/shared/recursal.model";
import {Classe, Preferencia} from "autuacao/shared/autuacao.model";

describe('Teste do controlador revisao-pressuposto.controller', () => {
    
    let controller : RevisaoPressupostosFormaisController;
    let $q : ng.IQService;
    let $rootScope : ng.IRootScopeService;
    let mockState;
    let mockRevisaoPressupostosService;
    let mockMotivosAptidao;
    let mockMessagesService;
    let mockStateParams;
    let mockAnalise;
    
    beforeEach(inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
    }));
    
    beforeEach(() => {
        mockState = {
            go : () => {}
        };
        mockRevisaoPressupostosService = {
            revisar : () =>{}
        };
        mockMotivosAptidao = {
                motivosAptidao : [new MotivoInaptidao(1, 'Teste'), new MotivoInaptidao(2, 'Teste2')]
            };
        mockMessagesService = {
            success: () => {}
        };
        mockStateParams = {
                informationId: 1
        };
        mockAnalise = {
        	    processoApto: false,
        	    observacao: 'teste',
        	    motivosInaptidao: mockMotivosAptidao.motivosAptidao
        }
       
        controller = new RevisaoPressupostosFormaisController(mockState, mockRevisaoPressupostosService, mockAnalise, mockMotivosAptidao, mockMessagesService);
    });

    it('Deveria revisar o processo como inapto', () => {
        let processoApto = false;
        let observacao = 'Teste da observação';
        controller.cmd.processoApto = processoApto;
        controller.cmd.motivosInaptidao.push(1);
        controller.cmd.motivosInaptidao.push(2);
        controller.cmd.observacao = observacao;
        
        spyOn(mockRevisaoPressupostosService, 'revisar').and.callFake(() => $q.when());
        
        spyOn(mockState, 'go').and.callThrough();
        
        controller.revisarAnalisePressupostos();
        
        $rootScope.$apply();
        
        expect(mockRevisaoPressupostosService.revisar).toHaveBeenCalledWith(controller.cmd);
        
        expect(mockState.go).toHaveBeenCalledWith("app.tarefas.minhas-tarefas");
    });

});
