import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {AnaliseRevisaoPressupostosPage} from "./pages/pressupostos-formais.page";


describe('Análise de pressupostos formais', () => {    
	

    let loginPage: LoginPage = new LoginPage();
    let principalPage: PrincipalPage = new PrincipalPage();
    let pressupostosFormaisPage : AnaliseRevisaoPressupostosPage = new AnaliseRevisaoPressupostosPage();
            
	it ('Deveria logar no sistema', () => {
	    loginPage.open();
	    loginPage.login('analista-pressupostos', '123');
	});
	
	it ('Deveria acessar a tarefa de analisar pressupostos formais', () => {
	    principalPage.acessarTarefa('Analisar Pressupostos Formais', 9006);
	});
	
	it('Deveria preencher as informações dos pressupostos inválidos', () => {
		pressupostosFormaisPage.classificar();
		pressupostosFormaisPage.selecionarMotivoInaptidao('Outro', 'Intempestividade do recurso extraordinário');
		pressupostosFormaisPage.preencherObservacao();
	});
	
    it('Deveria analisar', () => {
    	pressupostosFormaisPage.analisar();
        expect(principalPage.exibiuMensagemSucesso()).toBeTruthy();
    });
    
    it('Deveria fazer o logout do sistema', () => {
        principalPage.logout();
    });
	
});
