import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import {MotivoService} from "./motivo.service";

/** @ngInject **/
function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                $stateProvider: IStateProvider,
                properties: any) {

    $translatePartialLoaderProvider.addPart(properties.apiUrl + '/autuacao/analise');

    $stateProvider.state('app.novo-processo.analise-pressupostos', {
        url : '/processo/analise',
        views : {
            'content@app.autenticado' : {
                templateUrl : properties.apiUrl + '/autuacao/analise/analise-pressupostos.tpl.html',
                controller : 'app.novo-processo.analise.AnalisePressupostosController',
                controllerAs: 'analise'
            }
        },
        resolve : {
            motivos : ['app.novo-processo.analise.MotivoService', (motivoService: MotivoService) => {
                return motivoService.listar();
            }]
        }
    });
}

let analisePressupostos: IModule = angular.module('app.novo-processo.analise', ['app.novo-processo', 'app.constants']);
analisePressupostos.config(config);
export default analisePressupostos;