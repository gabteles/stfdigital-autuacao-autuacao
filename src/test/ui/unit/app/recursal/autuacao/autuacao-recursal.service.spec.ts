import {AutuacaoRecursalService} from "autuacao/recursal/autuacao-recursal/autuacao-recursal.service";
import {AutuarProcessoRecursalCommand} from "autuacao/recursal/shared/recursal.model";
import "autuacao/recursal/autuacao-recursal/autuacao-recursal.service";
import 'autuacao/recursal/shared/recursal.module';

describe("Teste do serviço de autuacao recursal", () => {

    let $httpBackend : ng.IHttpBackendService;
    let autuacaoService : AutuacaoRecursalService;
    let properties;
    let handler;

    beforeEach(angular.mock.module('app.autuacao.recursal'));

    beforeEach(inject(['$httpBackend', 'properties', 'app.autuacao.recursal.AutuacaoRecursalService','commandService', (_$httpBackend_ : ng.IHttpBackendService, _properties_, _autuacaoRecursalService_ : AutuacaoRecursalService, _commandService_: app.support.command.CommandService ) => {
        $httpBackend = _$httpBackend_;
        autuacaoService =  _autuacaoRecursalService_;
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

    it("deveria chamar o método de autuar o processo recursal", () => {
        let cmdAutuar : AutuarProcessoRecursalCommand = new AutuarProcessoRecursalCommand();
        cmdAutuar.poloAtivo = ['LUCAS', 'TOMAS'];
        cmdAutuar.poloPassivo = ['PAULA','MARIA']
        cmdAutuar.processoId = 1;
        cmdAutuar.assuntos = ['4291', '10912'];
        $httpBackend.expectPOST(properties.apiUrl + '/autuacao/api/processos/recursal/autuacao', cmdAutuar).respond(200,"");
        autuacaoService.autuar(cmdAutuar).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
});