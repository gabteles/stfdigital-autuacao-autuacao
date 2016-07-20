import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import IStateParamsService = angular.ui.IStateParamsService;
import {AutuacaoRecursalSharedService} from "../shared/recursal.service";
import autuacaoRecursal from '../shared/recursal.module';

/** @ngInject **/
function config($stateProvider: IStateProvider) {

    $stateProvider.state('app.tarefas.analise-repercussao-geral', {
        url : '/autuacao/recursal/:informationId/analise-repercussao-geral',
        views : {
            'content@app.autenticado' : {
                templateUrl : './analise-repercussao-geral.tpl.html',
                controller : 'app.autuacao.recursal.AnaliseRepercussaoGeralController',
                controllerAs: 'analise'
            }
        },
        resolve : {
            tiposTese : ['app.autuacao.recursal.AutuacaoRecursalSharedService', (autuacaoRecursalService: AutuacaoRecursalSharedService) => {
                return autuacaoRecursalService.listarTiposTese();
            }],
            processo: ['app.autuacao.recursal.AutuacaoRecursalSharedService', '$stateParams', (autuacaoRecursalService: AutuacaoRecursalSharedService, $stateParams : IStateParamsService) => {
                let protocoloId = $stateParams['informationId'];
                return autuacaoRecursalService.consultarProcesso(protocoloId);
            }]
        },
        params : {
            informationId : undefined
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
	
    $translatePartialLoader.addPart(properties.apiUrl + '/autuacao/recursal/analise-repercussao-geral');
}

autuacaoRecursal.config(config).run(run);

export default autuacaoRecursal;