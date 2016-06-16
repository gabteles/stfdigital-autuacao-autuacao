import ElementFinder = protractor.ElementFinder;

export class AnaliseRevisaoPressupostosPage {
	
	public classificar() : void {
		element(by.id('classificacao')).click();
		browser.driver.sleep(200);
	};
	
	public selecionarMotivos() : void {
		element(by.id('motivo')).click();
		element.all(by.repeater('motivo in analise.motivos')).get(1).click();
		element.all(by.repeater('motivo in analise.motivos')).get(2).click();
		browser.driver.sleep(200);
		browser.actions().sendKeys(protractor.Key.ESCAPE).perform();
	};
	
	public preencherObservacao() : void {
		element(by.id('observacao')).sendKeys('teste de observacao');
	}
	
	public registrarAnalise() : void {
		element(by.id('btnRegistrarAnalise')).click();
	};
	
	public registrarRevisao() : void {
		element(by.id('btnRevisarAnalisePressupostos')).click();
	};
	
}