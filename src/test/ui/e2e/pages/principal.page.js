"use strict";
var PrincipalPage = (function () {
    function PrincipalPage() {
        this.linkIniciarProcesso = element.all(by.css('a[ui-sref="app.novo-processo"]')).get(0);
        this.linkNovaPeticaoFisica = element(by.css('div[ui-sref="app.novo-processo.recebimento"]'));
    }
    PrincipalPage.prototype.iniciarProcesso = function () {
        this.linkIniciarProcesso.click();
    };
    PrincipalPage.prototype.iniciarPeticaoFisica = function () {
        this.linkNovaPeticaoFisica.click();
        browser.sleep(5000);
    };
    return PrincipalPage;
}());
exports.PrincipalPage = PrincipalPage;
