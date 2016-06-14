import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;


/** @ngInject **/
function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                $stateProvider: IStateProvider,
                properties: any) {

    $translatePartialLoaderProvider.addPart(properties.apiUrl + '/autuacao/recursal/autuacao/criminal');

    $stateProvider.state('app.novo-processo.autuacao-criminal', {
        url : '/autuacao/recursal/autuacao/criminal',
        views : {
            'content@app.autenticado' : {
                templateUrl : properties.apiUrl + '/autuacao/recursal/autuacao/criminal/autuacao-criminal.tpl.html',
                controller : 'app.novo-processo.criminal.AutuacaoCriminalController',
                controllerAs: 'autuacao'
            }
        },
    });
}

let autuacao: IModule = angular.module('app.novo-processo.criminal', ['app.novo-processo.autuacao-services', 'app.novo-processo', 'app.constants']);
autuacao.config(config);
export default autuacao;