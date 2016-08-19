import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {AnaliseRevisaoRepercussaoPage} from "./pages/analise-revisao-rg.page";


describe('Revisão da Repercussao Geral', () => {    
    
    let loginPage: LoginPage = new LoginPage();
    let principalPage: PrincipalPage = new PrincipalPage();
    let revisaoRepercussaoGeralPage : AnaliseRevisaoRepercussaoPage = new AnaliseRevisaoRepercussaoPage();
            
    it ('Deveria logar no sistema', () => {
        loginPage.open();
        loginPage.login('revisor-repercussao-g', '123');
    });
    
    it ('Deveria acessar a tarefa de revisar repercussão geral', () => {
        principalPage.acessarTarefa('Revisar Repercussão Geral', 9007);
    });
    
    it('Deveria remover um tese', () => {
        revisaoRepercussaoGeralPage.removerTese('revisao', 0)
    });
    
    it('Deveria preencher as informações da tese', () => {
    	revisaoRepercussaoGeralPage.selecionarComboTipoTese('Repercussão Geral');
        revisaoRepercussaoGeralPage.inserirNumeroTese('170');
    });
    
    it('Deveria adicionar um assunto', () => {
    	revisaoRepercussaoGeralPage.selecionarAssuntos('Direi');
    });
    
    it('Deveria remover um assunto', () => {
    	revisaoRepercussaoGeralPage.removerAssuntos('revisao', 2);
    });
    
    it('Deveria escrever uma observação', () => {
        revisaoRepercussaoGeralPage.inserirObservacao();
    });
    
    it('Deveria revisar', () => {
    	revisaoRepercussaoGeralPage.acionarBotao('revisar-repercussao-geral');
        expect(principalPage.exibiuMensagemSucesso()).toBeTruthy();
    });
    
    it('Deveria fazer o logout do sistema', () => {
        principalPage.logout();
    });
    
});
