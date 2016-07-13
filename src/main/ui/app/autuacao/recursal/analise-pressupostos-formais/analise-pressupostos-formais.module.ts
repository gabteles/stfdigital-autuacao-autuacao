import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IStateParamsService = angular.ui.IStateParamsService;
import Properties = app.support.constants.Properties;
import {AutuacaoSharedService} from '../../shared/autuacao.service';
import {AutuacaoRecursalSharedService} from '../shared/recursal.service';
import autuacaoRecursal from '../shared/recursal.module';

/** @ngInject **/
function config($stateProvider: IStateProvider) {

    $stateProvider.state('app.tarefas.analise-pressupostos-formais', {
        url : '/autuacao/recursal/:informationId/analise-pressupostos-formais',
        views : {
            'content@app.autenticado' : {
                templateUrl : './analise-pressupostos-formais.tpl.html',
                controller : 'app.autuacao.recursal.AnalisePressupostosFormaisController',
                controllerAs: 'analise'
            }
        },
        resolve : {
            motivosInaptidao : ['app.autuacao.recursal.AutuacaoRecursalSharedService', (autuacaoRecursalService: AutuacaoRecursalSharedService) => {
                return autuacaoRecursalService.listarMotivosInaptidao();
            }]
        },
        params : {
        	informationId : undefined
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {

    $translatePartialLoader.addPart(properties.apiUrl + '/autuacao/recursal/analise-pressupostos-formais');
}

autuacaoRecursal.config(config).run(run);
export default autuacaoRecursal;