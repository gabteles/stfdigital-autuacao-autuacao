import {AnalisePressupostosFormaisController} from "autuacao/recursal/analise-pressupostos-formais/analise-pressupostos-formais.controller";
import {AutuarProcessoRecursalCommand, MotivoInaptidao} from "autuacao/recursal/shared/recursal.model";
import {Classe, Preferencia} from "autuacao/shared/autuacao.model";

describe('Teste do controlador analise-pressuposto.controller', () => {
    
    let controller : AnalisePressupostosFormaisController;
    let $q : ng.IQService;
    let $rootScope : ng.IRootScopeService;
    let mockState;
    let mockAnalisePressupostosService;
    let mockMotivosAptidao;
    let mockMessagesService;
    let mockStateParams;
    
    beforeEach(inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
    }));
    
    beforeEach(() => {
        mockState = {
            go : () => {}
        };
        mockAnalisePressupostosService = {
            analisar : () =>{}
        };
        mockMotivosAptidao = {
        		motivosAptidao : [new MotivoInaptidao(1, 'Teste'), new MotivoInaptidao(2, 'Teste2')]
            };
        mockMessagesService = {
            success: () => {}
        };
        mockStateParams = {
        		informationId: 1
        }
       
        controller = new AnalisePressupostosFormaisController(mockState, mockStateParams, mockAnalisePressupostosService, mockMotivosAptidao, mockMessagesService);
    });

    
    it('Deveria analisar o processo como inapto', () => {
        let processoApto = false;
        let observacao = 'Teste da observação';
        controller.cmd.processoApto = processoApto;
        controller.cmd.motivosInaptidao.push(1);
        controller.cmd.motivosInaptidao.push(2);
        controller.cmd.observacao = observacao;
        
        spyOn(mockAnalisePressupostosService, 'analisar').and.callFake(() => $q.when());
        
        spyOn(mockState, 'go').and.callThrough();
        
        controller.registrarAnalise();
        
        $rootScope.$apply();
        
        expect(mockAnalisePressupostosService.analisar).toHaveBeenCalledWith(controller.cmd);
        
        expect(mockState.go).toHaveBeenCalledWith("app.tarefas.minhas-tarefas");
    });

});
