"use strict";
var AnalisePage = (function () {
    function AnalisePage() {
    }
    AnalisePage.prototype.classificarInaptidao = function () {
        element(by.id('classificacao')).click();
        browser.driver.sleep(200);
    };
    ;
    AnalisePage.prototype.selecionarMotivos = function () {
        element(by.id('motivo')).click();
        element.all(by.repeater('motivo in analise.motivos')).get(1).click();
        element.all(by.repeater('motivo in analise.motivos')).get(2).click();
        browser.driver.sleep(200);
        browser.actions().sendKeys(protractor.Key.ESCAPE).perform();
    };
    ;
    AnalisePage.prototype.preencherObservacao = function () {
        element(by.id('observacao')).sendKeys('teste de observacao');
    };
    AnalisePage.prototype.registrarAnalise = function () {
        element(by.id('btnRegistrarAnalise')).click();
    };
    ;
    return AnalisePage;
}());
exports.AnalisePage = AnalisePage;
