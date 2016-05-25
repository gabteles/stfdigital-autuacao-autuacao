"use strict";
var AutuacaoPage = (function () {
    function AutuacaoPage() {
    }
    AutuacaoPage.prototype.cadastrarPoloAtivo = function (nome) {
        element(by.id('txtPoloAtivo')).sendKeys(nome);
        browser.actions().sendKeys(protractor.Key.ENTER).perform();
    };
    ;
    AutuacaoPage.prototype.cadastrarPoloPassivo = function (nome) {
        element(by.id('txtPoloPassivo')).sendKeys(nome);
        browser.actions().sendKeys(protractor.Key.ENTER).perform();
    };
    ;
    AutuacaoPage.prototype.selecionarClasse = function () {
        element(by.id('classes')).click();
        element.all(by.repeater('classe in autuacao.classes')).get(2).click();
    };
    ;
    AutuacaoPage.prototype.registrarAutuacao = function () {
        var select = element(by.id("btnRegistrarAutuacao"));
        browser.executeScript("arguments[0].scrollIntoView();", select.getWebElement());
        select.click();
    };
    ;
    return AutuacaoPage;
}());
exports.AutuacaoPage = AutuacaoPage;
