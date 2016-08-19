import ElementFinder = protractor.ElementFinder;

import mdHelpers = require('../shared/helpers/md-helpers');

export class AutuacaoRecursalPage {
	
	public cadastrarPoloAtivo(nome : string) : void {
	    element(by.id('txtPoloAtivo')).click();
		element(by.id('txtPoloAtivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	};
	
	public cadastrarPoloPassivo(nome : string) : void {
	    element(by.id('txtPoloPassivo')).click();
		element(by.id('txtPoloPassivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	};
	
	public selecionarAssuntos(termo : string) : void {
		element(by.id('assunto')).click();
		element(by.css('input[type=search]')).click();
		element(by.css('input[type=search]')).sendKeys(termo);
		browser.driver.sleep(500);
		element.all(by.repeater('assunto in $select.items')).get(1).click();
	};
	
	public autuar(idBotao : string) : void {
		element(by.id(idBotao)).click();
	};
	
}