import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
//import {AutuacaoService} from "./autuacao.service";
import {ClasseService} from "./classe.service";

/** @ngInject **/
function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                $stateProvider: IStateProvider,
                properties: any) {

    $translatePartialLoaderProvider.addPart(properties.apiUrl + '/autuacao/autuacao');

    $stateProvider.state('app.novo-processo.autuacao', {
        url : '/autuacao/autuar',
        views : {
            'content@app.autenticado' : {
                templateUrl : properties.apiUrl + '/autuacao/autuacao/autuacao.tpl.html',
                controller : 'app.novo-processo.autuacao.AutuacaoController',
                controllerAs: 'autuacao'
            }
        },
        resolve : {
            classes : ['app.novo-processo.autuacao.ClasseService', (classeService: ClasseService) => {
                return classeService.listar();
            }]
        }
    });
}

let autuacao: IModule = angular.module('app.novo-processo.autuacao', ['app.novo-processo', 'app.constants']);
autuacao.config(config);
export default autuacao;