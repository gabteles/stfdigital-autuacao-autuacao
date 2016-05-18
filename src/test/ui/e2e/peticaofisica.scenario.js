"use strict";
var login_page_1 = require("./pages/login.page");
var principal_page_1 = require("./pages/principal.page");
var recebimento_page_1 = require("./pages/recebimento.page");
describe('Autuação de Petições Físicas Originárias', function () {
    var loginPage = new login_page_1.LoginPage();
    var principalPage = new principal_page_1.PrincipalPage();
    var recebimentoPage = new recebimento_page_1.RecebimentoPage();
    it('Deveria logar na tela', function () {
        loginPage.open();
        loginPage.login('aaa', '123');
    });
    it('Deveria acessar a pagina de peticao física', function () {
        principalPage.iniciarProcesso();
        principalPage.iniciarPeticaoFisica();
    });
    it('Deveria preencher as informações da petição física', function () {
        recebimentoPage.preencherQtdVolumes(2);
        recebimentoPage.preencherQtdApensos(3);
        recebimentoPage.selecionarFormaRecebimento();
        recebimentoPage.selecionarTipoRecebimento();
        recebimentoPage.registrarPeticao();
    });
});
