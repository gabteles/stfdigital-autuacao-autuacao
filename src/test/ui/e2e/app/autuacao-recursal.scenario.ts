import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {AutuacaoRecursalPage} from "./pages/autuacao-recursal.page";

xdescribe('Autuação de Processos Recursais sem ser criminal ou eleitoral', () => {	
	
    let loginPage: LoginPage = new LoginPage();
    let principalPage: PrincipalPage = new PrincipalPage();
    let autuacaoRecursalPage : AutuacaoRecursalPage = new AutuacaoRecursalPage();
                
	it ('Deveria logar no sistema', () => {
	    loginPage.open();
	    loginPage.login('autuador', '123');
	});
	
	it ('Deveria acessar a tarefa de autuar processo recural', () => {
	    principalPage.acessarTarefa('Autuar Processo Recursal', 9010);
	});

    it('Deveria preencher as partes ', () => {
    	autuacaoRecursalPage.cadastrarPoloAtivo('Maria da Silva');
    	autuacaoRecursalPage.cadastrarPoloPassivo('João Carneiro');
    });
    
    it ('Deveria autuar o processo recursal', () => {
    	autuacaoRecursalPage.autuar('autuar-recursal');
        expect(principalPage.exibiuMensagemSucesso()).toBeTruthy();
    });
    
    it('Deveria fazer o logout do sistema', () => {
        principalPage.logout();
    });
    
    
    
});