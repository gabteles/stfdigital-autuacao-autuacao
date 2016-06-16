import ElementFinder = protractor.ElementFinder;

export class AnaliseRevisaoRepercussaoPage {
	
	public selecionarTipoTese(controller : string, indice : number) : void {
		element.all(by.repeater('tipo in ' + controller + '.tiposTese')).get(indice).click();
	}
	
	public inserirNumeroTese(numeroTese : string) : void {
		element(by.id('numeroTese')).sendKeys(numeroTese);
		browser.actions().sendKeys(protractor.Key.ENTER).perform();
	}
	
	public selecionarAssuntos(termo : string, controller : string) : void {
		element(by.model('autuacao.assunto')).click();
		element(by.css('input[type=search]')).click();
		element(by.css('input[type=search]')).sendKeys(termo);
		element.all(by.repeater('codigo as assunto in ' + controller + '.assuntos')).get(1).click();
	};
	
	public removerAssuntos(controller : string, indice : number) : void {
		element.all(by.repeater('ass in ' + controller + '.assuntosSelecionados')).
		  get(indice).element(by.css('a')).click();
	}
	
	public registrarRepercussao(botaoId : string) : void {
		let select = element(by.id(botaoId));
		//para que a tela desça e o botão de autuar fique visível no momento do teste
	    browser.executeScript("arguments[0].scrollIntoView();", select.getWebElement());
	    select.click();
	};
}