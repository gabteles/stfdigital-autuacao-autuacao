"use strict";
var login_page_1 = require("./pages/login.page");
var principal_page_1 = require("./pages/principal.page");
var analise_page_1 = require("./pages/analise.page");
describe('Autuação de Processos Recursais sem ser criminal ou eleitoral', function () {
    var loginPage = new login_page_1.LoginPage();
    var principalPage = new principal_page_1.PrincipalPage();
    var analisePage = new analise_page_1.AnalisePage();
    it('Deveria logar na tela', function () {
        loginPage.open();
        loginPage.login('aaa', '123');
    });
    it('Deveria acessar a pagina de análise de pressupostos', function () {
        principalPage.iniciarProcesso();
        principalPage.iniciarAnalisePressupostos();
    });
    it('Deveria analisar os pressupostos do processo recursal informando como inapto ', function () {
        analisePage.classificarInaptidao();
        analisePage.selecionarMotivos();
        analisePage.preencherObservacao();
        analisePage.registrarAnalise();
    });
});
