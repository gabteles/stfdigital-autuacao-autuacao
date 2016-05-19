import ElementFinder = protractor.ElementFinder;

export class AutuacaoPage {
	
	public cadastrarPoloAtivo(nome : string) : void {
		element(by.id('txtPoloAtivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ESCAPE).perform();
	};
	
	public cadastrarPoloPassivo(nome : string) : void {
		element(by.id('txtPoloPassivo')).sendKeys(nome);
		browser.actions().sendKeys(protractor.Key.ESCAPE).perform();
	};
	
	public selecionarClasse() : void {
		element(by.id('classes')).click();
		element.all(by.repeater('classe in autuacao.classes')).get(2).click();
	};
	
	public registrarPeticao() : void {
		element(by.id('btnRegistrarPeticao')).click();
	};
	
}