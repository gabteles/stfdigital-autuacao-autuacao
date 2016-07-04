import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import {AutuacaoService} from "../../../services/autuacao.service";

/** @ngInject **/
function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                $stateProvider: IStateProvider,
                properties: any) {

    $translatePartialLoaderProvider.addPart(properties.apiUrl + '/autuacao/recursal/pressuposto/analise');

    $stateProvider.state('app.novo-processo.analise-pressupostos', {
        url : '/autuacao/recursal/pressuposto/analise',
        views : {
            'content@app.autenticado' : {
                templateUrl : './analise-pressupostos.tpl.html',
                controller : 'app.autuacao.analise.AnalisePressupostosController',
                controllerAs: 'analise'
            }
        },
        resolve : {
            motivos : ['app.autuacao.autuacao-services.AutuacaoService', (autuacaoService: AutuacaoService) => {
                return autuacaoService.listarMotivosInaptidao();
            }]
        }
    });
}

let analisePressupostos: IModule = angular.module('app.autuacao.analise', 
    ['app.autuacao.autuacao-services', 'app.novo-processo', 'app.support']);
analisePressupostos.config(config);
export default analisePressupostos;