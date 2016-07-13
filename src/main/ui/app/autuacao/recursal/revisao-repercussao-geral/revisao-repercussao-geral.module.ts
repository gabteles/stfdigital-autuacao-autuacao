import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IStateParamsService = angular.ui.IStateParamsService;
import Properties = app.support.constants.Properties;
import {AutuacaoRecursalSharedService} from "../shared/recursal.service";
import {RevisaoRepercussaoGeralService} from "./revisao-repercussao-geral.service";
import autuacaoRecursal from '../shared/recursal.module';

/** @ngInject **/
function config($stateProvider: IStateProvider) {

    $stateProvider.state("app.tarefas.revisao-repercussao-geral", {
        url : "/autuacao/recursal/:informationId/revisao-repercussao-geral",
        views : {
            "content@app.autenticado" : {
                templateUrl : "./revisao-repercussao-geral.tpl.html",
                controller : "app.autuacao.recursal.RevisaoRepercussaoGeralController",
                controllerAs: "revisao"
            }
        },
        resolve : {
            analise : ['app.autuacao.recursal.RevisaoPressupostosFormaisService', (revisaoService: RevisaoRepercussaoGeralService, $stateParams: IStateParamsService) => {
                let id = $stateParams['informationId'];
                return revisaoService.consultarAnaliseProcesso(id);
            }],
            tiposTese : ['app.autuacao.recursal.AutuacaoRecursalSharedService', (autuacaoRecursalService: AutuacaoRecursalSharedService) => {
                return autuacaoRecursalService.listarTiposTese();
            }]
        },
        params : {
            informationId : undefined
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
	
	$translatePartialLoader.addPart(properties.apiUrl + "/autuacao/recursal/revisao-repercussao-geral");
}

autuacaoRecursal.config(config).run(run);
export default autuacaoRecursal;