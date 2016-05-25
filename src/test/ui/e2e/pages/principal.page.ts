import ElementFinder = protractor.ElementFinder;

export class PrincipalPage {
    
    private linkIniciarProcesso: ElementFinder = element.all(by.css('a[ui-sref="app.novo-processo"]')).get(0);
	private linkAutuacao: ElementFinder = element(by.css('div[ui-sref="app.novo-processo.autuacao"]'));
	private linkAnalisePressupostos: ElementFinder = element(by.css('div[ui-sref="app.novo-processo.analise-pressupostos"]'));
    
   
    public iniciarProcesso() : void {
        this.linkIniciarProcesso.click();
    }
    
    public iniciarAutuacao() : void {
    	this.linkAutuacao.click();
     	browser.sleep(200);
    }
    
    public iniciarAnalisePressupostos() : void {
    	this.linkAnalisePressupostos.click();
    	browser.sleep(200);
    }
}