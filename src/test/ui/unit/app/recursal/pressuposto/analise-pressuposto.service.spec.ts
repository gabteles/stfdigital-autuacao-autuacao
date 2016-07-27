import {AnalisePressupostosFormaisService} from "autuacao/recursal/analise-pressupostos-formais/analise-pressupostos-formais.service";
import {AnalisarPressupostosFormaisCommand} from "autuacao/recursal/shared/recursal.model";
import "autuacao/recursal/analise-pressupostos-formais/analise-pressupostos-formais.service";
import 'autuacao/recursal/shared/recursal.module';

describe("Teste do serviço de análise de pressupostos formais", () => {

    let $httpBackend : ng.IHttpBackendService;
    let analiseService : AnalisePressupostosFormaisService;
    let properties;
    let handler;

    beforeEach(angular.mock.module('app.autuacao.recursal'));

    beforeEach(inject(['$httpBackend', 'properties', 'app.autuacao.recursal.AnalisePressupostosFormaisService','commandService', (_$httpBackend_ : ng.IHttpBackendService, _properties_, _analisePressupostosFormaisService_ : AnalisePressupostosFormaisService,
    		_commandService_: app.support.command.CommandService ) => {
        $httpBackend = _$httpBackend_;
        analiseService =  _analisePressupostosFormaisService_;
        properties = _properties_;
    }]));
    
    beforeEach(() => {
        handler = {
            success: () => {},
            error: () => {}
        }; 
        spyOn(handler, 'success').and.callThrough();
        spyOn(handler, 'error').and.callThrough();
    });

    it('deveria chamar o método de analisar os pressupostos formais', () => {
        let cmdAnalisar : AnalisarPressupostosFormaisCommand = new AnalisarPressupostosFormaisCommand();
        cmdAnalisar.processoApto = true;
        cmdAnalisar.observacao = 'TESTE'
        cmdAnalisar.processoId = 1;
        cmdAnalisar.motivosInaptidao = [1,2];
        $httpBackend.expectPOST(properties.apiUrl + '/autuacao/api/processos/recursal/analise-pressupostos-formais', cmdAnalisar).respond(200,"");
        analiseService.analisar(cmdAnalisar).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
});