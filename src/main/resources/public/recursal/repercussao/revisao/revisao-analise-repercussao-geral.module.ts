import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;

/** @ngInject **/
function config($stateProvider: IStateProvider, properties: any) {

    $stateProvider.state("app.novo-processo.revisao-analise-repercussao-geral", {
        url : "/recursal/repercussao/revisao",
        views : {
            "content@app.autenticado" : {
                templateUrl : properties.apiUrl + "/autuacao/recursal/repercussao/revisao/revisao-analise-repercussao-geral.tpl.html",
                controller : "app.novo-processo.revisao-analise-repercussao-geral.RevisaoAnaliseRepercussaoGeralController",
                controllerAs: "vm"
            }
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderProvider,
			 properties: any) {
	
	$translatePartialLoader.addPart(properties.apiUrl + "/autuacao/recursal/repercussao/revisao");
}

let revisaoAnaliseRepercussaoGeral: IModule = angular.module("revisao-analise-repercussao-geral", 
    ["app.novo-processo.autuacao-services", "app.novo-processo", "app.constants"]);

revisaoAnaliseRepercussaoGeral.config(config).run(run);
export default revisaoAnaliseRepercussaoGeral;