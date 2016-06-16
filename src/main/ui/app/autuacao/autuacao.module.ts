import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import {AutuacaoService} from "../services/autuacao.service";

/** @ngInject **/
function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                $stateProvider: IStateProvider,
                properties: any) {

    $translatePartialLoaderProvider.addPart(properties.apiUrl + '/autuacao/autuacao');

    $stateProvider.state('app.novo-processo.autuacao', {
        url : '/autuacao/autuar',
        views : {
            'content@app.autenticado' : {
                templateUrl : './autuacao.tpl.html',
                controller : 'app.novo-processo.autuacao.AutuacaoController',
                controllerAs: 'autuacao'
            }
        },
        resolve : {
            classes : ['app.novo-processo.autuacao-services.AutuacaoService', (autuacaoService: AutuacaoService) => {
                return autuacaoService.listarClasses();
            }]
        }
    });
}

let autuacao: IModule = angular.module('app.novo-processo.autuacao', 
    ['app.novo-processo.autuacao-services', 'app.novo-processo', 'app.support']);
autuacao.config(config);
export default autuacao;