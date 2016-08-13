import ElementFinder = protractor.ElementFinder;

import mdHelpers = require('../shared/helpers/md-helpers');

export class AnaliseRevisaoRepercussaoPage {
	
	public selecionarTipoTese(controller : string, indice : number) : void {
		element.all(by.repeater('tipo in ' + controller + '.tiposTese')).get(indice).click();
	}
	
	public selecionarComboTipoTese(tipo: string): void {
        mdHelpers.mdSelect.selectOptionWithText(element(by.id('tipoTese')), tipo);
    }
	
    public removerTese(controller : string, indice : number) : void {
        element.all(by.repeater('tese in ' + controller + '.teses')).
          get(indice).element(by.css('a')).click();
    }
	
	public inserirNumeroTese(numeroTese : string) : void {
		element(by.id('numeroTese')).sendKeys(numeroTese);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	}
	
	public selecionarAssuntos(termo : string) : void {
		element(by.id('assunto')).click();
		element(by.css('input[type=search]')).click();
		element(by.css('input[type=search]')).sendKeys(termo);
		browser.driver.sleep(500);
		element.all(by.repeater('assunto in $select.items')).get(0).click();
	};
	
	public removerAssuntos(controller : string, indice : number) : void {
		element.all(by.repeater('ass in ' + controller + '.assuntosSelecionados')).
		  get(indice).element(by.css('a')).click();
	}
	
	public inserirObservacao() : void {
		element(by.id('observacao')).click();
		element(by.id('observacao')).sendKeys('observacao da revisao da repercussao geral');
	}
	
	public acionarBotao(botaoId : string) : void {
		element(by.id(botaoId)).click();
	};
}