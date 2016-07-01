import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import {AutuacaoService} from "../services/autuacao.service";
import Properties = app.support.constants.Properties;
import cmd = app.support.command;

/** @ngInject **/
function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                $stateProvider: IStateProvider,
                properties: Properties) {

    $translatePartialLoaderProvider.addPart(properties.apiUrl + '/autuacao/autuacao');

    $stateProvider.state('app.novo-processo.autuacao', {
        url : '/autuacao/autuar',
        views : {
            'content@app.autenticado' : {
                templateUrl : './autuacao.tpl.html',
                controller : 'app.autuacao.autuacao.AutuacaoController',
                controllerAs: 'autuacao'
            }
        },
        resolve : {
            classes : ['app.autuacao.autuacao-services.AutuacaoService', (autuacaoService: AutuacaoService) => {
                return autuacaoService.listarClasses();
            }]
        }
    });
}

let autuacao: IModule = angular.module('app.autuacao.autuacao', 
    ['app.autuacao.autuacao-services', 'app.novo-processo', 'app.support']);
autuacao.config(config);
export default autuacao;