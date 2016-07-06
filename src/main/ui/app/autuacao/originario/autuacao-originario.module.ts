import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import {AutuacaoService} from "../services/autuacao.service";
import Properties = app.support.constants.Properties;
import cmd = app.support.command;

/** @ngInject **/
function config($stateProvider: IStateProvider, properties: Properties) {

    $stateProvider.state('app.novo-processo.autuacao-originario', {
        url : '/autuacao/originario/autuacao',
        views : {
            'content@app.autenticado' : {
                templateUrl : './autuacao-originario.tpl.html',
                controller : 'app.autuacao.originario.AutuacaoOriginarioController',
                controllerAs: 'autuacao'
            }
        },
        resolve : {
            classes : ['app.autuacao.services.AutuacaoService', (autuacaoService: AutuacaoService) => {
                return autuacaoService.listarClasses();
            }]
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
	$translatePartialLoader.addPart(properties.apiUrl + '/autuacao/originario');
}

let autuacaoOriginario: IModule = angular.module('app.autuacao.originario', 
    ['app.autuacao.services', 'app.novo-processo', 'app.support']);
autuacaoOriginario.config(config).run(run);
export default autuacaoOriginario;