import {LoginPage} from "./pages/login.page";
import {PrincipalPage}  from "./pages/principal.page";
import {AutuacaoRePage} from "./pages/autuacaoRe.page";

describe('Autuação de Processos Recursais criminal e eleitoral', () => {	
	
    var loginPage: LoginPage = new LoginPage();
    var principalPage: PrincipalPage = new PrincipalPage();
	var autuacaoRe : AutuacaoRePage = new AutuacaoRePage();
                
    it ('Deveria logar na tela', () => {
        loginPage.open();
        loginPage.login('aaa', '123');
    });
    
    it ('Deveria acessar a pagina de autuacao criminais e eleitorais', () => {
        principalPage.iniciarProcesso();
        principalPage.iniciarAutuacaoCriminalEleitoral();
    });
    
    it('Deveria analisar os pressupostos do processo recursal informando como inapto ', () => {
    	autuacaoRe.cadastrarPoloAtivo('Maria');
    	autuacaoRe.cadastrarPoloPassivo('Telmo');
    	autuacaoRe.selecionarAssuntos('DIREITO');
    	autuacaoRe.registrarAutuacaoCriminalEleitoral();
    });
    
});