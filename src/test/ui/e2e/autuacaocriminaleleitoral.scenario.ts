import {LoginPage} from "./pages/login.page";
import {PrincipalPage}  from "./pages/principal.page";
import {AnalisePage} from "./pages/analise.page";

describe('Autuação de Processos Recursais criminal e eleitoral', () => {	
	
    var loginPage: LoginPage = new LoginPage();
    var principalPage: PrincipalPage = new PrincipalPage();
	var analisePage : AnalisePage = new AnalisePage();
                
    it ('Deveria logar na tela', () => {
        loginPage.open();
        loginPage.login('aaa', '123');
    });
    
    it ('Deveria acessar a pagina de análise de pressupostos', () => {
        principalPage.iniciarProcesso();
        principalPage.iniciarAutuacaoCriminalEleitoral();
    });
    
    it('Deveria analisar os pressupostos do processo recursal informando como inapto ', () => {
    	analisePage.classificarInaptidao();
    	analisePage.selecionarMotivos();
    	analisePage.preencherObservacao();
    	analisePage.registrarAnalise();
    });
    
});