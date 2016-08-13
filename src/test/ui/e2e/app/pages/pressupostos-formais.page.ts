import ElementFinder = protractor.ElementFinder;

import mdHelpers = require('../shared/helpers/md-helpers');

export class AnaliseRevisaoPressupostosPage {
	
	public classificar() : void {
		element(by.id('classificacao')).click();
		browser.driver.sleep(200);
	};
	
	public selecionarMotivoInaptidao(...motivos: string[]): void {
        mdHelpers.mdSelect.selectMultipleOptionsWithText(element(by.id('motivoInaptidao')), motivos);
    }
    
	public preencherObservacao() : void {
		element(by.id('observacao')).sendKeys('analise pressupostos formais');
	}
	
	public analisar() : void {
		element(by.id('analisar-pressupostos-formais')).click();
	};
	
	public revisar() : void {
		element(by.id('revisar-pressupostos-formais')).click();
	};
	
}