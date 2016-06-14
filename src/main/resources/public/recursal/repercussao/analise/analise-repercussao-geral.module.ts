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
                templateUrl : properties.apiUrl + '/autuacao/recursal/repercussao/analise/analise-repercussao-geral.tpl.html',
                controller : 'app.novo-processo.analise.AnaliseRepercussaoGeralController',
                controllerAs: 'analise'
            }
        },
        resolve : {
            tiposTese : ['app.novo-processo.autuacao-services.AutuacaoService', (autuacaoService: AutuacaoService) => {
                return autuacaoService.listarTiposTese();
            }]
        }
    });
}

let analise: IModule = angular.module('app.novo-processo.analise', ['app.novo-processo.autuacao-services', 'app.novo-processo', 'app.constants']);
analise.config(config);
export default analise;