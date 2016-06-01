import ElementFinder = protractor.ElementFinder;

export class AutuacaoPage {
	
	public cadastrarPoloAtivo(nome : string) : void {
		element(by.id('txtPoloAtivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	};
	
	public cadastrarPoloPassivo(nome : string) : void {
		element(by.id('txtPoloPassivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	};
	
	public selecionarClasse() : void {
		element(by.id('classes')).click();
		element.all(by.repeater('classe in autuacao.classes')).get(2).click();
	};
	
	public registrarAutuacao() : void {
		let select = element(by.id("btnRegistrarAutuacao" ));
	    browser.executeScript("arguments[0].scrollIntoView();", select.getWebElement());
	    select.click();
	};
	
}