import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import IStateParamsService = angular.ui.IStateParamsService;
import autuacaoRecursal from '../shared/recursal.module';
import {AutuacaoRecursalSharedService} from "../shared/recursal.service";

/** @ngInject **/
function config($stateProvider: IStateProvider) {

    $stateProvider.state('app.tarefas.autuacao-criminal-eleitoral', {
        url : '/autuacao/recursal/:informationId/autuacao-criminal-eleitoral',
        views : {
            'content@app.autenticado' : {
                templateUrl : './autuacao-criminal-eleitoral.tpl.html',
                controller : 'app.autuacao.recursal.AutuacaoCriminalEleitoralController',
                controllerAs: 'autuacao'
            }
        },
        resolve : { 
            processo: ['app.autuacao.recursal.AutuacaoRecursalSharedService', '$stateParams', (autuacaoRecursalService: AutuacaoRecursalSharedService, $stateParams : IStateParamsService) => {
                let protocoloId = $stateParams['informationId'];
                return autuacaoRecursalService.consultarProcessoRecursal(protocoloId);
            }]
        },
        params : {
            informationId : undefined
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {

    $translatePartialLoader.addPart(properties.apiUrl + '/autuacao/recursal/autuacao-criminal-eleitoral');
}

autuacaoRecursal.config(config).run(run);
export default autuacaoRecursal;