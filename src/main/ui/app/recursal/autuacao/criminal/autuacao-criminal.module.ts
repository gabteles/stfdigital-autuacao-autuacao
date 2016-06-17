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
                templateUrl : './autuacao-criminal.tpl.html',
                controller : 'app.autuacao.criminal.AutuacaoCriminalController',
                controllerAs: 'autuacao'
            }
        },
    });
}

let autuacao: IModule = angular.module('app.autuacao.autuacao-criminal', ['app.autuacao.autuacao-services', 'app.novo-processo', 'app.support']);
autuacao.config(config);
export default autuacao;