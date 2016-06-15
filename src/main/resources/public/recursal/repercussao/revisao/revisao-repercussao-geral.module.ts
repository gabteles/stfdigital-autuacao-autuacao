import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;

/** @ngInject **/
function config($stateProvider: IStateProvider, properties: any) {

    $stateProvider.state("app.novo-processo.revisao-repercussao-geral", {
        url : "/recursal/repercussao/revisao",
        views : {
            "content@app.autenticado" : {
                templateUrl : properties.apiUrl + "/autuacao/recursal/repercussao/revisao/revisao-repercussao-geral.tpl.html",
                controller : "app.novo-processo.revisao-repercussao-geral.RevisaoRepercussaoGeralController",
                controllerAs: "revisao"
            }
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderProvider,
			 properties: any) {
	
	$translatePartialLoader.addPart(properties.apiUrl + "/autuacao/recursal/repercussao/revisao");
}

let revisaoAnaliseRepercussaoGeral: IModule = angular.module("revisao-repercussao-geral", 
    ["app.novo-processo.autuacao-services", "app.novo-processo", "app.support"]);

revisaoAnaliseRepercussaoGeral.config(config).run(run);
export default revisaoAnaliseRepercussaoGeral;