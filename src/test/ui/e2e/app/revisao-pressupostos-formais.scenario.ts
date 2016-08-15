import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {AnaliseRevisaoPressupostosPage} from "./pages/pressupostos-formais.page";


describe('Revisão de pressupostos formais', () => {    
    

    let loginPage: LoginPage = new LoginPage();
    let principalPage: PrincipalPage = new PrincipalPage();
    let pressupostosFormaisPage : AnaliseRevisaoPressupostosPage = new AnaliseRevisaoPressupostosPage();
            
    it ('Deveria logar no sistema', () => {
        loginPage.open();
        loginPage.login('revisor-processo-ri', '123');
    });
    
    it ('Deveria acessar a tarefa de revisar pressupostos formais', () => {
        principalPage.acessarTarefa('Revisar Pressupostos Formais', 9011);
    });
    
    it('Deveria classificar o processo como apto', () => {
        pressupostosFormaisPage.classificar();
    });
    
    it('Deveria revisar', () => {
        pressupostosFormaisPage.revisar();
        expect(principalPage.exibiuMensagemSucesso()).toBeTruthy();
    });
    
    it('Deveria fazer o logout do sistema', () => {
        principalPage.logout();
    });
    
});
