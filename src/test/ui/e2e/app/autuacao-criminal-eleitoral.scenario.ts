import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {AutuacaoRecursalPage} from "./pages/autuacao-recursal.page";

describe('Autuação de Processos Recursais criminal e eleitoral', () => {	
	
    let loginPage: LoginPage = new LoginPage();
	let principalPage: PrincipalPage = new PrincipalPage();
	let autuacaoRe : AutuacaoRecursalPage = new AutuacaoRecursalPage();
                
    it ('Deveria logar no sistema', () => {
        loginPage.open();
        loginPage.login('autuador-recursal-ce', '123');
    });
    
    it ('Deveria acessar a tarefa de autuar processo recursal criminal/eleitoral', () => {
        principalPage.acessarTarefa('Autuar Processo Recursal Criminal/Eleitoral', 9013);
    });
    
    it('Deveria preencher as partes e selecionar o assunto ', () => {
    	autuacaoRe.cadastrarPoloAtivo('Maria');
    	autuacaoRe.cadastrarPoloPassivo('Telmo');
    	autuacaoRe.selecionarAssuntos('DIREITO');
    });
    
    it('Deveria autuar ', () => {
    	autuacaoRe.autuar('autuar-recursal-criminal-eleitoral');
    });
    
    it('Deveria fazer o logout do sistema', () => {
        principalPage.logout();
    });
});