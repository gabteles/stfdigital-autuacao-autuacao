import {AutuacaoCriminalEleitoralService} from "autuacao/recursal/autuacao-criminal-eleitoral/autuacao-criminal-eleitoral.service";
import {Assunto, AutuarProcessoRecursalCommand, ProcessoRecursal} from "autuacao/recursal/shared/recursal.model";
import "autuacao/recursal/autuacao-criminal-eleitoral/autuacao-criminal-eleitoral.service";
import 'autuacao/recursal/shared/recursal.module';

describe("Teste do serviço de autuacao criminal eleitoral", () => {

    let $httpBackend : ng.IHttpBackendService;
    let autuacaoService : AutuacaoCriminalEleitoralService;
    let properties;
    let handler;

    beforeEach(angular.mock.module('app.autuacao.recursal'));

    beforeEach(inject(['$httpBackend', 'properties', 'app.autuacao.recursal.AutuacaoCriminalEleitoralService','commandService', (_$httpBackend_ : ng.IHttpBackendService, _properties_, _autuacaoCriminalEleitoralService_ : AutuacaoCriminalEleitoralService, _commandService_: app.support.command.CommandService ) => {
        $httpBackend = _$httpBackend_;
        autuacaoService =  _autuacaoCriminalEleitoralService_;
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

    it("deveria chamar o método de autuar o processo criminal eleitoral", () => {
        let cmdAutuar : AutuarProcessoRecursalCommand = new AutuarProcessoRecursalCommand();
        cmdAutuar.poloAtivo = ['LUCAS', 'TOMAS'];
        cmdAutuar.poloPassivo = ['PAULA','MARIA']
        cmdAutuar.processoId = 1;
        cmdAutuar.assuntos = ['4291', '10912'];
        $httpBackend.expectPOST(properties.apiUrl + '/autuacao/api/processos/recursal/autuacao-criminal-eleitoral', cmdAutuar).respond(200,"");
        autuacaoService.autuar(cmdAutuar).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
});