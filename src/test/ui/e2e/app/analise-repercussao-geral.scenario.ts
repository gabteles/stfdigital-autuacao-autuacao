import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {AnaliseRevisaoRepercussaoPage} from "./pages/analise-revisao-rg.page";


describe('Análise da da Repercussao Geral', () => {    
    
    let loginPage: LoginPage = new LoginPage();
    let principalPage: PrincipalPage = new PrincipalPage();
    let analiseRepercussaoGeralPage : AnaliseRevisaoRepercussaoPage = new AnaliseRevisaoRepercussaoPage();
            
    it ('Deveria logar no sistema', () => {
        loginPage.open();
        loginPage.login('analista-repercussao-g', '123');
    });
    
    it ('Deveria acessar a tarefa de analisar repercussão geral', () => {
        principalPage.acessarTarefa('Analisar Assunto/RG', 9007);
    });
    
    it('Deveria preencher as informações da tese', () => {
    	analiseRepercussaoGeralPage.selecionarComboTipoTese('Repercussão Geral')
    	analiseRepercussaoGeralPage.inserirNumeroTese('170');
    });
    
    it('Deveria adicionar um assunto', () => {
    	analiseRepercussaoGeralPage.selecionarAssuntos('Direito');
    });
    
    it('Deveria removeer um assunto', () => {
        analiseRepercussaoGeralPage.removerAssuntos('analise', 2)
    });
    
    it('Deveria analisar', () => {
    	analiseRepercussaoGeralPage.acionarBotao('analisar-repercussao-geral');
        expect(principalPage.exibiuMensagemSucesso()).toBeTruthy();
    });
    
    it('Deveria fazer o logout do sistema', () => {
        principalPage.logout();
    });
    
});
