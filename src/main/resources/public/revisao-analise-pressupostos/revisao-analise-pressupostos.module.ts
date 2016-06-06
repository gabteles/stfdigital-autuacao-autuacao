import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;

/** @ngInject **/
function config($stateProvider: IStateProvider, properties: any) {

    $stateProvider.state("app.novo-processo.revisao-analise-pressupostos", {
        url : "/recursal/revisao-analise-pressupostos",
        views : {
            "content@app.autenticado" : {
                templateUrl : properties.apiUrl + "/autuacao/revisao-analise-pressupostos/revisao-analise-pressupostos.tpl.html",
                controller : "app.novo-processo.revisao-analise-pressupostos.RevisaoAnalisePressupostosController",
                controllerAs: "vm"
            }
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderProvider,
			 properties: any) {
	
	$translatePartialLoader.addPart(properties.apiUrl + "/autuacao/autuacao/recursal");
}

let revisaoAnalisePressupostos: IModule = angular.module("revisao-analise-pressupostos", 
    ["app.novo-processo.autuacao-services", "app.novo-processo", "app.constants"]);
revisaoAnalisePressupostos.config(config).run(run);
export default revisaoAnalisePressupostos;