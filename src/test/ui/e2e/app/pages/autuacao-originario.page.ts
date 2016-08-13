import ElementFinder = protractor.ElementFinder;

import mdHelpers = require('../shared/helpers/md-helpers');

export class AutuacaoOriginarioPage {
	
	public cadastrarPoloAtivo(nome : string) : void {
		element(by.id('txtPoloAtivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	};
	
	public cadastrarPoloPassivo(nome : string) : void {
		element(by.id('txtPoloPassivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	};
	
	public selecionarClasse(classe: string): void {
        mdHelpers.mdSelect.selectOptionWithText(element(by.id('classes')), classe);
    }
	
	public validarAutuacao(id : string) : void {
		element(by.id(id)).click();
	}
	
    public autuar(): void {
        element(by.id('autuar-originario')).click();
    }
	
}