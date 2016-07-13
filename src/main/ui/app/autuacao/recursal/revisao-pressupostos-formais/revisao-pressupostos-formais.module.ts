import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IStateParamsService = angular.ui.IStateParamsService;
import Properties = app.support.constants.Properties;
import {AutuacaoRecursalSharedService} from "../shared/recursal.service";
import {RevisaoPressupostosFormaisService} from "./revisao-pressupostos-formais.service";
import autuacaoRecursal from '../shared/recursal.module';

/** @ngInject **/
function config($stateProvider: IStateProvider) {

    $stateProvider.state("app.tarefas.revisao-pressupostos-formais", {
        url : "/autuacao/recursal/revisao-pressupostos-formais/:informationId",
        views : {
            "content@app.autenticado" : {
                templateUrl : "./revisao-pressupostos-formais.tpl.html",
                controller : "app.autuacao.recursal.RevisaoPressupostosFormaisController",
                controllerAs: "revisao"
            }
        },
        resolve : {
            analise : ['app.autuacao.recursal.RevisaoPressupostosFormaisService', (revisaoService: RevisaoPressupostosFormaisService, $stateParams: IStateParamsService) => {
                let id = $stateParams['informationId'];
                return revisaoService.consultarAnaliseProcesso(id);
            }],
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
	
	$translatePartialLoader.addPart(properties.apiUrl + "/autuacao/recursal/revisao-pressupostos-formais");
}

autuacaoRecursal.config(config).run(run);
export default autuacaoRecursal;