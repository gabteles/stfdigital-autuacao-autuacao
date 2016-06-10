import ElementFinder = protractor.ElementFinder;

export class AutuacaoRePage {
	
	public cadastrarPoloAtivo(nome : string) : void {
		element(by.id('txtPoloAtivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	};
	
	public cadastrarPoloPassivo(nome : string) : void {
		element(by.id('txtPoloPassivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	};
	
	public selecionarAssuntos(termo : string) : void {
		element(by.model('autuacao.assunto')).click();
		element(by.css('input[type=search]')).click();
		element(by.css('input[type=search]')).sendKeys(termo);
		element.all(by.repeater('codigo as assunto in autuacao.assuntos')).get(1).click();
	};
	
	public registrarAutuacaoCriminalEleitoral() : void {
		let select = element(by.id("btnRegistrarAutuacao" ));
	    browser.executeScript("arguments[0].scrollIntoView();", select.getWebElement());
	    select.click();
	};
	
}