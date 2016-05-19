"use strict";
var RecebimentoPage = (function () {
    function RecebimentoPage() {
    }
    RecebimentoPage.prototype.preencherQtdVolumes = function (quantidade) {
        element(by.id('qtdVolumes')).sendKeys(quantidade.toString());
    };
    ;
    RecebimentoPage.prototype.selecionarFormaRecebimento = function () {
        element(by.id('formaRecimento')).click();
        element.all(by.repeater('forma in registro.formasRecebimento')).get(2).click();
    };
    ;
    RecebimentoPage.prototype.preencherQtdApensos = function (quantidade) {
        element(by.id('qtdApensos')).sendKeys(quantidade.toString());
    };
    ;
    RecebimentoPage.prototype.preencherNumSedex = function (quantidade) {
        element(by.id('numeroSedex')).sendKeys(quantidade.toString());
    };
    ;
    RecebimentoPage.prototype.selecionarTipoRecebimento = function () {
        element(by.id('tipoProcesso')).click();
        element.all(by.repeater('tipo in registro.tiposProcessos')).get(0).click();
    };
    ;
    RecebimentoPage.prototype.registrarPeticao = function () {
        element(by.id('btnRegistrarPeticao')).click();
    };
    ;
    return RecebimentoPage;
}());
exports.RecebimentoPage = RecebimentoPage;
