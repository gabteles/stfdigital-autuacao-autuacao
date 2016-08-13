import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {AutuacaoOriginarioPage} from "./pages/autuacao-originario.page";

describe('Autuação de Petições Físicas Originárias', () => {	
	
    let loginPage: LoginPage = new LoginPage();
    let principalPage: PrincipalPage = new PrincipalPage();
	let autuacaoPage : AutuacaoOriginarioPage = new AutuacaoOriginarioPage();
                
	it ('Deveria logar no sistema', () => {
	    loginPage.open();
	    loginPage.login('autuador', '123');
	});
	
	it ('Deveria acessar a tarefa de autuar processo originário', () => {
	    principalPage.acessarTarefa('Autuar Processo Originário', 9001);
	});
    
    it('Deveria preencher as informações da petição física', () => {
    	autuacaoPage.cadastrarPoloAtivo('Maria da Silva');
    	autuacaoPage.cadastrarPoloPassivo('João Carneiro');
    	autuacaoPage.selecionarClasse('MANDADO DE SEGURANÇA');
    	autuacaoPage.validarAutuacao('valida');
    });
    
    it('Deveria autuar', () => {
    	autuacaoPage.autuar();
        expect(principalPage.exibiuMensagemSucesso()).toBeTruthy();
    });
    
    it('Deveria fazer o logout do sistema', () => {
        principalPage.logout();
    });
});