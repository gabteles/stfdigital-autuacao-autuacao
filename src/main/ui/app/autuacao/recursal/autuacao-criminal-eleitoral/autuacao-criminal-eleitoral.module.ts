import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import autuacaoRecursal from '../shared/recursal.module';

/** @ngInject **/
function config($stateProvider: IStateProvider) {

    $stateProvider.state('app.tarefas.autuacao-criminal-eleitoral', {
        url : '/autuacao/recursal/autuacao-criminal-eleitoral/:informationId',
        views : {
            'content@app.autenticado' : {
                templateUrl : './autuacao-criminal-eleitoral.tpl.html',
                controller : 'app.autuacao.recursal.AutuacaoCriminalEleitoralController',
                controllerAs: 'autuacao'
            }
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