"use strict";
var PrincipalPage = (function () {
    function PrincipalPage() {
        this.linkIniciarProcesso = element.all(by.css('a[ui-sref="app.novo-processo"]')).get(0);
        this.linkAutuacao = element(by.css('div[ui-sref="app.novo-processo.autuacao"]'));
        this.linkAnalisePressupostos = element(by.css('div[ui-sref="app.novo-processo.analise-pressupostos"]'));
    }
    PrincipalPage.prototype.iniciarProcesso = function () {
        this.linkIniciarProcesso.click();
    };
    PrincipalPage.prototype.iniciarAutuacao = function () {
        this.linkAutuacao.click();
        browser.sleep(200);
    };
    PrincipalPage.prototype.iniciarAnalisePressupostos = function () {
        this.linkAnalisePressupostos.click();
        browser.sleep(200);
    };
    return PrincipalPage;
}());
exports.PrincipalPage = PrincipalPage;
