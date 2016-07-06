System.config({
	transpiler: null,
	defaultJSExtensions: true,
	map: {
		'systemjs': './node_modules/systemjs/dist/system.src.js',
		'system-polyfills': './node_modules/systemjs/dist/system-polyfills.src.js'
	},
	bundles: {
<<<<<<< HEAD
		'autuacao/bundle': ['autuacao/autuacao/*', 'autuacao/recursal/autuacao-recursal/*', 'autuacao/recursal/autuacao-criminal-eleitoral/*', 'autuacao/services/*']
=======
		'autuacao/bundle': ['autuacao/autuacao/*', 'autuacao/recursal/autuacao/recursal/*', 'autuacao/recursal/autuacao/criminal/*', 'autuacao/services/*']
>>>>>>> branch 'master' of https://github.com/supremotribunalfederal/stfdigital-autuacao-autuacao
	}
});
