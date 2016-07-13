import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import autuacaoRecursal from '../shared/recursal.module'

/** @ngInject **/
function config($stateProvider: IStateProvider) {

    $stateProvider.state("app.tarefas.autuacao-recursal", {
        url : "/autuacao/recursal/:informationId/autuacao",
        views : {
            "content@app.autenticado" : {
                templateUrl : "./autuacao-recursal.tpl.html",
                controller : "app.autuacao.recursal.AutuacaoRecursalController",
                controllerAs: "autuacao"
            }
        },
        params : {
            informationId : undefined
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
    
    $translatePartialLoader.addPart(properties.apiUrl + '/autuacao/recursal/autuacao-recursal');
}

autuacaoRecursal.config(config).run(run);
export default autuacaoRecursal;