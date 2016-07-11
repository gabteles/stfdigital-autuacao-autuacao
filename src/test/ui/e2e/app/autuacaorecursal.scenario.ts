import {LoginPage} from "./pages/login.page";
import {PrincipalPage}  from "./pages/principal.page";
import {AnaliseRevisaoPressupostosPage} from "./pages/analise-revisao-pr.page";
import {AnaliseRevisaoRepercussaoPage} from "./pages/analise-revisao-rg.page";
import {AutuacaoRePage} from "./pages/autuacaoRe.page";

xdescribe('Autuação de Processos Recursais sem ser criminal ou eleitoral', () => {	
	
    var loginPage: LoginPage = new LoginPage();
    var principalPage: PrincipalPage = new PrincipalPage();
	var analiseRevisaoPrPage : AnaliseRevisaoPressupostosPage = new AnaliseRevisaoPressupostosPage();
	var analiseRevisaoRepercussaoPage : AnaliseRevisaoRepercussaoPage = new AnaliseRevisaoRepercussaoPage();
	var autuacaoRePage : AutuacaoRePage = new AutuacaoRePage();
                
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
    
    it ('Deveria fazer logout na tela de análise de pressupostos', () => {
        loginPage.close();
    });
    
    it('Deveria logar como revisor de pressupostos na tela ', () => {
        loginPage.open();
        loginPage.login('revisor-processo-ri', '123');
    });
    
    it ('Deveria acessar a pagina de análise de pressupostos', () => {
        //deverá clicar na tarefa de revisar o processo
    });
    
    it('Deveria revisar o processo como apto', () => {
    	analiseRevisaoPrPage.classificar();
    	analiseRevisaoPrPage.registrarRevisao();
    });
    
    it ('Deveria fazer logout na tela de revisão de pressupostos', () => {
        loginPage.close();
    });
    
    it('Deveria logar como analista de repercussão geral na tela ', () => {
        loginPage.open();
        loginPage.login('analista-repercussao-g', '123');
    });
    
    it('Deveria analisar a repercussao geral', () => {
    	analiseRevisaoRepercussaoPage.selecionarTipoTese('analise', 2);
    	analiseRevisaoRepercussaoPage.inserirNumeroTese('170');
    	analiseRevisaoRepercussaoPage.selecionarAssuntos('DIREITO', 'analise');
    	analiseRevisaoRepercussaoPage.registrarRepercussao('btnRegistrarAnalise');
    });
    
    it ('Deveria fazer logout na tela de análise de repercussão', () => {
        loginPage.close();
    });
    
    it('Deveria logar como analista de repercussão geral na tela ', () => {
        loginPage.open();
        loginPage.login('revisor-repercussao-g', '123');
    });
    
    it('Deveria revisar a repercussao geral', () => {
    	analiseRevisaoRepercussaoPage.selecionarTipoTese('revisao', 0);
    	analiseRevisaoRepercussaoPage.inserirNumeroTese('783');
    	analiseRevisaoRepercussaoPage.removerAssuntos('revisao', 2);
    	analiseRevisaoRepercussaoPage.registrarRepercussao('btnRegistrarRevisao');
    });
    
    it ('Deveria fazer logout na tela de revisão de repercussão geral', () => {
        loginPage.close();
    });
    
    it('Deveria logar como autuador de recursal na tela ', () => {
        loginPage.open();
        loginPage.login('autuador-recursal', '123');
    });
    
    it('Deveria autuar processo recursal', () => {
    	autuacaoRePage.cadastrarPoloAtivo('José');
    	autuacaoRePage.cadastrarPoloPassivo('Helena');
    	autuacaoRePage.registrarAutuacao('btnautuarProcessoRecursal');
    });
    
    
});