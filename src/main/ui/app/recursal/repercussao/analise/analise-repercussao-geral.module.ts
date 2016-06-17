import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import {AutuacaoService} from "../../../services/autuacao.service";
import "../../../services/autuacao.service";


/** @ngInject **/
function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                $stateProvider: IStateProvider,
                properties: any) {

    $translatePartialLoaderProvider.addPart(properties.apiUrl + '/autuacao/recursal/repercussao/analise');

    $stateProvider.state('app.novo-processo.analise', {
        url : '/autuacao/recursal/repercussao/analise',
        views : {
            'content@app.autenticado' : {
                templateUrl : './analise-repercussao-geral.tpl.html',
                controller : 'app.autuacao.analise.AnaliseRepercussaoGeralController',
                controllerAs: 'analise'
            }
        },
        resolve : {
            tiposTese : ['app.autuacao.autuacao-services.AutuacaoService', (autuacaoService: AutuacaoService) => {
                return autuacaoService.listarTiposTese();
            }]
        }
    });
}

let analise: IModule = angular.module('app.autuacao.analise', ['app.autuacao.autuacao-services', 'app.novo-processo', 'app.support']);
analise.config(config);
export default analise;