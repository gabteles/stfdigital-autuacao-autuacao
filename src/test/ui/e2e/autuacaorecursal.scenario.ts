import {LoginPage} from "./pages/login.page";
import {PrincipalPage}  from "./pages/principal.page";
import {AnaliseRevisaoPrPage} from "./pages/analiseRevisaoPr.page";

describe('Autuação de Processos Recursais sem ser criminal ou eleitoral', () => {	
	
    var loginPage: LoginPage = new LoginPage();
    var principalPage: PrincipalPage = new PrincipalPage();
	var analiseRevisaoPrPage : AnaliseRevisaoPrPage = new AnaliseRevisaoPrPage();
                
    it ('Deveria logar analista de pressupostos na tela', () => {
        loginPage.open();
        loginPage.login('analista-pressupostos', '123');
    });
    
    it ('Deveria acessar a pagina de análise de pressupostos', () => {
    	//deverá clicar na tarefa de analisar o processo
    });
    
    it('Deveria analisar os pressupostos do processo recursal informando como inapto ', () => {
    	analiseRevisaoPrPage.classificar();
    	analiseRevisaoPrPage.selecionarMotivos();
    	analiseRevisaoPrPage.preencherObservacao();
    	analiseRevisaoPrPage.registrarAnalise();
    });
    
    it ('Deveria fazer logout na tela', () => {
        loginPage.close();
    });
    
    it('Deveria logar como revisor de pressupostos na tela ', () => {
        loginPage.open();
        loginPage.login('revisor-processo-ri', '123');
    });
    
    it ('Deveria acessar a pagina de análise de pressupostos', () => {
        //deverá clicar na tarefa de revisar o processo
    });
    
    it('Deveria analisar os pressupostos do processo recursal informando como inapto ', () => {
    	analiseRevisaoPrPage.classificar();
    	analiseRevisaoPrPage.selecionarMotivos();
    	analiseRevisaoPrPage.preencherObservacao();
    	analiseRevisaoPrPage.registrarAnalise();
    });
    
    
    
});