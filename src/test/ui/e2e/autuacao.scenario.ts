import {LoginPage} from "./pages/login.page";
import {PrincipalPage}  from "./pages/principal.page";
import {AutuacaoPage} from "./pages/autuacao.page";

describe('Autuação de Petições Físicas Originárias', () => {	
	
    var loginPage: LoginPage = new LoginPage();
    var principalPage: PrincipalPage = new PrincipalPage();
	var autuacaoPage : AutuacaoPage = new AutuacaoPage();
                
    it ('Deveria logar na tela', () => {
        loginPage.open();
        loginPage.login('aaa', '123');
    });
    
    it ('Deveria acessar a pagina de peticao física', () => {
        principalPage.iniciarProcesso();
        principalPage.iniciarPeticaoFisica();
    });
    
    it('Deveria preencher as informações da petição física', () => {
    	autuacaoPage.preencherQtdVolumes(2);
    	autuacaoPage.preencherQtdApensos(3);
    	autuacaoPage.selecionarFormaRecebimento();
    	autuacaoPage.selecionarTipoRecebimento();
    	autuacaoPage.registrarPeticao();
    });
    
});