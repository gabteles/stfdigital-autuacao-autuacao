System.config({
	transpiler: null,
	defaultJSExtensions: true,
	map: {
		'systemjs': './node_modules/systemjs/dist/system.src.js',
		'system-polyfills': './node_modules/systemjs/dist/system-polyfills.src.js'
	},
	bundles: {
		'autuacao/bundle': ['autuacao/autuacao/*', 'autuacao/recursal/autuacao-recursal/*', 'autuacao/recursal/autuacao-criminal-eleitoral/*', 'autuacao/services/*']
	}
});
