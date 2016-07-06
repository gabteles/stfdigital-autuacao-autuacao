import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;

/** @ngInject **/
function config($stateProvider: IStateProvider, properties: any) {

    $stateProvider.state("app.novo-processo.autuacao-recursal", {
        url : "/autuacao/recursal",
        views : {
            "content@app.autenticado" : {
                templateUrl : "./autuacao-recursal.tpl.html",
                controller : "app.autuacao.recursal.AutuacaoRecursalController",
                controllerAs: "vm"
            }
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderProvider,
			 properties: any) {
	
	$translatePartialLoader.addPart(properties.apiUrl + "/recursal/autuacao-recursal");
}

let autuacaoRecursal: IModule = angular.module("app.autuacao.recursal", 
    ["app.autuacao.services", "app.novo-processo", "app.support"]);
autuacaoRecursal.config(config).run(run);
export default autuacaoRecursal;