"use strict";
var login_page_1 = require("./pages/login.page");
var principal_page_1 = require("./pages/principal.page");
var autuacao_page_1 = require("./pages/autuacao.page");
describe('Autuação de Petições Físicas Originárias', function () {
    var loginPage = new login_page_1.LoginPage();
    var principalPage = new principal_page_1.PrincipalPage();
    var autuacaoPage = new autuacao_page_1.AutuacaoPage();
    it('Deveria logar na tela', function () {
        loginPage.open();
        loginPage.login('aaa', '123');
    });
    it('Deveria acessar a pagina para autuar petição', function () {
        principalPage.iniciarProcesso();
        principalPage.iniciarAutuacao();
    });
    it('Deveria preencher as informações da petição física', function () {
        autuacaoPage.cadastrarPoloAtivo('Maria da Silva');
        autuacaoPage.cadastrarPoloPassivo('João Carneiro');
        autuacaoPage.selecionarClasse();
        autuacaoPage.registrarAutuacao();
    });
});
