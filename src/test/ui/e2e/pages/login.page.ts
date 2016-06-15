import ElementFinder = protractor.ElementFinder;

export class LoginPage {
    
    private usernameInput: ElementFinder = element(by.model('vm.form.usuario'));
    private passwordInput: ElementFinder = element(by.model('vm.form.senha'));
    private submitButton: ElementFinder = element(by.css('button[type="submit"]'));
	private usuarioMenu: ElementFinder = element(by.id('user-menu'));
	private opcaoSair: ElementFinder = element(by.css('button[aria-label="Sair"]'));

	constructor() {
		
	}
    
    public open(): void {
        browser.get('/login');
    }
    
    public login(username, password): void {
        this.usernameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.submitButton.click();
    }
    
    public close() : void {
    	this.usuarioMenu.click();
    	this.opcaoSair.click();
    	
    }
}