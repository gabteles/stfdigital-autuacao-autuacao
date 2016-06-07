import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import {AutuacaoService} from "../services/autuacao.service";

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
            motivos : ['app.novo-processo.autuacao-services.AutuacaoService', (autuacaoService: AutuacaoService) => {
                return autuacaoService.listarMotivosInaptidao();
            }]
        }
    });
}

let analisePressupostos: IModule = angular.module('app.novo-processo.analise', 
    ['app.novo-processo.autuacao-services', 'app.novo-processo', 'app.constants']);
analisePressupostos.config(config);
export default analisePressupostos;