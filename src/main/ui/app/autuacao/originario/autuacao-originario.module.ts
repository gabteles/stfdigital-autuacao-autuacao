import "../shared";
import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import {AutuacaoSharedService} from "../shared/autuacao.service";

/** @ngInject **/
function config($stateProvider: IStateProvider, properties: Properties) {

    $stateProvider.state('app.tarefas.autuacao-originario', {
        url : '/autuacao/originario/:informationId/autuacao',
        views : {
            'content@app.autenticado' : {
                templateUrl : './autuacao-originario.tpl.html',
                controller : 'app.autuacao.originario.AutuacaoOriginarioController',
                controllerAs: 'autuacao'
            }
        },
        resolve : {
            classes : ['app.autuacao.AutuacaoSharedService', (autuacaoService: AutuacaoSharedService) => {
                return autuacaoService.listarClasses();
            }]
        },
        params : {
            informationId : undefined
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
	$translatePartialLoader.addPart(properties.apiUrl + '/autuacao/originario');
}

let autuacaoOriginario: IModule = angular.module('app.autuacao.originario', ['app.autuacao', 'app.support']);
autuacaoOriginario.config(config).run(run);
export default autuacaoOriginario;