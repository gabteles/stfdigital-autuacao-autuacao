import {AnaliseRepercussaoGeralController} from "autuacao/recursal/analise-repercussao-geral/analise-repercussao-geral.controller";
import {AnalisarRepercussaoGeralCommand, TipoTese, Tese, Assunto} from "autuacao/recursal/shared/recursal.model";
import {Processo} from "autuacao/shared/autuacao.model";

describe('Teste do controlador analise-repercussao-geral.controller', () => {
    
    let controller : AnaliseRepercussaoGeralController;
    let $q : ng.IQService;
    let $rootScope : ng.IRootScopeService;
    let mockState;
    let mockAnaliseRepercussaoGeralService;
    let mockAutuacaoRecursalService;
    let mockAssuntos;
    let mockTipoTeses;
    let mockProcesso;
    let mockMessagesService;
    let mockStateParams;
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
        mockAnaliseRepercussaoGeralService = {
            analisar : () =>{}
        };
        mockAssuntos = {
                assuntos : [new Assunto('4291', 'Jurisdição e Competência'), new Assunto('10912', 'Medidas Assecuratórias')]
            };
        mockTipoTeses = {
               
            };
        mockProcesso = {
        		numero: 1,
        		classe : {id:'HC'}
        }
        mockMessagesService = {
            success: () => {}
        };
        mockStateParams = {
                informationId: 1
        };
        
        mockTeses  = [{
        		codigo: 1,
        		descricao: 'teste descricao',
        		numero: 170, 
        		assuntos: [{codigo: '4291', descricao: 'Jurisdição e Competência', nivel: 0}, {codigo: '10912', descricao:'Medidas Assecutórias', nivel: 0}],
        		tipo: 'REPERCUSSAO_GERAL'
        }];
       
        controller = new AnaliseRepercussaoGeralController(mockState, mockAutuacaoRecursalService, mockAssuntos, mockAnaliseRepercussaoGeralService, mockTipoTeses, mockProcesso, mockStateParams, mockMessagesService);
    });

    it('Deveria verificar se o número possui somente digítos', () => {
    	spyOn(mockAutuacaoRecursalService, 'consultarTeses').and.callFake(() => $q.when());
        controller.numeroTese = '170AN';
        let retornaDigito = controller.consultarTese();
        expect(retornaDigito).toEqual(false, 'DEveria retornar somente números');
    });  
    
    it('Deveria verificar o metodo de consultar tese', () =>{
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
        spyOn(mockAnaliseRepercussaoGeralService, 'analisar').and.callFake(() => $q.when());
        spyOn(mockState, 'go').and.callThrough();
        controller.analisarRepercussaoGeral();
        $rootScope.$apply();
        expect(mockAnaliseRepercussaoGeralService.analisar).toHaveBeenCalledWith(controller.cmd);
        expect(mockState.go).toHaveBeenCalledWith("app.tarefas.minhas-tarefas");
    });

});
