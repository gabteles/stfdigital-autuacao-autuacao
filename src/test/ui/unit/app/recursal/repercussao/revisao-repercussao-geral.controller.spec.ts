import {RevisaoRepercussaoGeralController} from "autuacao/recursal/revisao-repercussao-geral/revisao-repercussao-geral.controller";
import {AnalisarRepercussaoGeralCommand, TipoTese, Tese, Assunto} from "autuacao/recursal/shared/recursal.model";
import {Processo} from "autuacao/shared/autuacao.model";

describe('Teste do controlador revisao-repercussao-geral.controller', () => {
    
    let controller : RevisaoRepercussaoGeralController;
    let $q : ng.IQService;
    let $rootScope : ng.IRootScopeService;
    let mockState;
    let mockRevisaoRepercussaoGeralService;
    let mockAutuacaoRecursalService;
    let mockAssuntos;
    let mockTipoTeses;
    let mockMessagesService;
    let mockAnalise;
    let mockTeses;
    
    beforeEach(inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
    }));
    
    beforeEach(() => {
        mockState = {
            go : () => {}
        };
        mockAutuacaoRecursalService = {
            consultarTeses : () =>{}
        };
        mockRevisaoRepercussaoGeralService = {
            revisar : () =>{}
        };
        mockAssuntos = {
                assuntos : [new Assunto('4291', 'Jurisdição e Competência'), new Assunto('10912', 'Medidas Assecuratórias')]
            };
        mockTipoTeses = {
               
            };
        mockAnalise = {
                observacao: 'teste',
                teses: [{
                    codigo: 1,
                    descricao: 'teste descricao',
                    numero: 170, 
                    assuntos: [{codigo: '4291', descricao: 'Jurisdição e Competência', nivel: 0}, {codigo: '10912', descricao:'Medidas Assecutórias', nivel: 0}],
                    tipo: 'REPERCUSSAO_GERAL'
            }]
        };
        mockMessagesService = {
            success: () => {}
        };
        mockTeses  = [{
        		codigo: 1,
        		descricao: 'teste descricao',
        		numero: 170, 
        		assuntos: [{codigo: '4291', descricao: 'Jurisdição e Competência', nivel: 0}, {codigo: '10912', descricao:'Medidas Assecutórias', nivel: 0}],
        		tipo: 'REPERCUSSAO_GERAL'
        }];
       
        controller = new RevisaoRepercussaoGeralController(mockState, mockRevisaoRepercussaoGeralService, mockAutuacaoRecursalService, mockAssuntos, mockAnalise,
        		 mockTipoTeses, mockMessagesService);
    });

    it('Deveria verificar se o número possui somente digítos', () => {
        controller.numeroTese = '170AN';
    	spyOn(mockAutuacaoRecursalService, 'consultarTeses').and.callFake(() => $q.when());
        let retornaDigito = controller.consultarTese();
        $rootScope.$apply();
        expect(retornaDigito).toEqual(false, 'DEveria retornar somente números');
    });  
    
    it('Deveria verificar o metodo de consultar tese e se o numero da tese é válido', () =>{
        let tipoTese = 'REPERCUSSAO_GERAL';
    	let numeroTese = '170';
    	controller.tipoTese = tipoTese;
    	controller.numeroTese = numeroTese;
    	spyOn(mockAutuacaoRecursalService, 'consultarTeses').and.callFake(() => $q.when());
    	let retornaDigito = controller.consultarTese();
    	$rootScope.$apply();
        expect(retornaDigito).toEqual(true, 'Deveria validar com sucesso');
        expect(mockAutuacaoRecursalService.consultarTeses).toHaveBeenCalledWith(tipoTese, numeroTese)
    });
    
    it('Deveria verificar o retorno  da tese e de assuntos', () =>{
        let tipoTese = 'REPERCUSSAO_GERAL';
        let numeroTese = '170';
        controller.tipoTese = tipoTese;
        controller.numeroTese = numeroTese;
        spyOn(mockAutuacaoRecursalService, 'consultarTeses').and.callFake(() => $q.when(mockTeses));
        controller.consultarTese();
        $rootScope.$apply();
        expect(controller.teses).toEqual(mockTeses, 'Deveria retornar testes');
        expect(controller.assuntosSelecionados).toEqual(mockTeses[0].assuntos, 'Deveria retornar os assuntos');
    });
    
    it('Deveria realizar a ação de analisar a repercussão geral', () => {
    	let observacao = 'Teste da observação';
        controller.cmd.observacao = observacao;
        spyOn(mockRevisaoRepercussaoGeralService, 'revisar').and.callFake(() => $q.when());
        spyOn(mockState, 'go').and.callThrough();
        controller.analisarRepercussaoGeral();
        $rootScope.$apply();
        expect(mockRevisaoRepercussaoGeralService.revisar).toHaveBeenCalledWith(controller.cmd);
        expect(mockState.go).toHaveBeenCalledWith("app.tarefas.minhas-tarefas");
    });

});
