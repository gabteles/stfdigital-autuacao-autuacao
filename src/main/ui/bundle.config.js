var lazypipe = require('lazypipe');
var path = require('path');
var typescript = require('gulp-typescript');
var ngAnnotate = require('gulp-ng-annotate');
var embedTemplates = require('gulp-angular-embed-templates');
var moduleNameInjector = require('gulp-systemjs-module-name-injector');

var conf = require('../../../gulp/conf');

var createTsProject = function() {
	return typescript.createProject(path.join(conf.paths.src, 'tsconfig.json'));
};
var libraryTypeScript = path.join(conf.paths.src, 'typings/main/**/*.d.ts');

module.exports = {
  bundle: {
    'analise-pressupostos': {
      scripts: [path.join(conf.paths.app, 'analise-pressupostos.ts'),
                path.join(conf.paths.app, 'recursal/pressuposto/analise/**/*.ts'),
                path.join(conf.paths.app, 'services/**/*.ts'), libraryTypeScript],
      options: {
    	  rev: false,
    	  transforms: {
              scripts: lazypipe()
              	.pipe(typescript, createTsProject())
              	.pipe(moduleNameInjector, {rootDir: 'src/main/ui/app/', prefix: 'autuacao/'})
              	.pipe(ngAnnotate)
              	.pipe(embedTemplates, {
              		skipErrors: true, 
              		minimize: {
              			empty : true,
              			spare : true,
              			quotes: true
              		}
              	})
          }
      }
    },
    'analise-repercussao-geral': {
      scripts: [path.join(conf.paths.app, 'analise-repercussao-geral.ts'),
                path.join(conf.paths.app, 'recursal/repercussao/analise/**/*.ts'),
                path.join(conf.paths.app, 'services/**/*.ts'), libraryTypeScript],
      options: {
    	  rev: false,
    	  transforms: {
              scripts: lazypipe()
              	.pipe(typescript, createTsProject())
              	.pipe(moduleNameInjector, {rootDir: 'src/main/ui/app/', prefix: 'autuacao/'})
              	.pipe(ngAnnotate)
              	.pipe(embedTemplates, {
              		skipErrors: true, 
              		minimize: {
              			empty : true,
              			spare : true,
              			quotes: true
              		}
              	})
          }
      }
    },
    'autuacao': {
      scripts: [path.join(conf.paths.app, 'autuacao.ts'),
                path.join(conf.paths.app, 'autuacao/**/*.ts'),
                path.join(conf.paths.app, 'services/**/*.ts'), libraryTypeScript],
      options: {
    	  rev: false,
    	  transforms: {
              scripts: lazypipe()
              	.pipe(typescript, createTsProject())
              	.pipe(moduleNameInjector, {rootDir: 'src/main/ui/app/', prefix: 'autuacao/'})
              	.pipe(ngAnnotate)
              	.pipe(embedTemplates, {
              		skipErrors: true, 
              		minimize: {
              			empty : true,
              			spare : true,
              			quotes: true
              		}
              	})
          }
      }
    },
    'autuacao-criminal': {
      scripts: [path.join(conf.paths.app, 'autuacao-criminal.ts'),
                path.join(conf.paths.app, 'recursal/autuacao/criminal/**/*.ts'),
                path.join(conf.paths.app, 'services/**/*.ts'), libraryTypeScript],
      options: {
    	  rev: false,
    	  transforms: {
              scripts: lazypipe()
              	.pipe(typescript, createTsProject())
              	.pipe(moduleNameInjector, {rootDir: 'src/main/ui/app/', prefix: 'autuacao/'})
              	.pipe(ngAnnotate)
              	.pipe(embedTemplates, {
              		skipErrors: true, 
              		minimize: {
              			empty : true,
              			spare : true,
              			quotes: true
              		}
              	})
          }
      }
    },
    'autuacao-recursal': {
      scripts: [path.join(conf.paths.app, 'autuacao-recursal.ts'),
                path.join(conf.paths.app, 'recursal/autuacao/recursal/**/*.ts'),
                path.join(conf.paths.app, 'services/**/*.ts'), libraryTypeScript],
      options: {
    	  rev: false,
    	  transforms: {
              scripts: lazypipe()
              	.pipe(typescript, createTsProject())
              	.pipe(moduleNameInjector, {rootDir: 'src/main/ui/app/', prefix: 'autuacao/'})
              	.pipe(ngAnnotate)
              	.pipe(embedTemplates, {
              		skipErrors: true, 
              		minimize: {
              			empty : true,
              			spare : true,
              			quotes: true
              		}
              	})
          }
      }
    },
    'revisao-analise-pressupostos': {
      scripts: [path.join(conf.paths.app, 'revisao-analise-pressupostos.ts'),
                path.join(conf.paths.app, 'recursal/pressuposto/revisao/**/*.ts'),
                path.join(conf.paths.app, 'services/**/*.ts'), libraryTypeScript],
      options: {
    	  rev: false,
    	  transforms: {
              scripts: lazypipe()
              	.pipe(typescript, createTsProject())
              	.pipe(moduleNameInjector, {rootDir: 'src/main/ui/app/', prefix: 'autuacao/'})
              	.pipe(ngAnnotate)
              	.pipe(embedTemplates, {
              		skipErrors: true, 
              		minimize: {
              			empty : true,
              			spare : true,
              			quotes: true
              		}
              	})
          }
      }
    },
    'revisao-repercussao-geral': {
      scripts: [path.join(conf.paths.app, 'revisao-repercussao-geral.ts'),
                path.join(conf.paths.app, 'recursal/repercussao/revisao/**/*.ts'),
                path.join(conf.paths.app, 'services/**/*.ts'), libraryTypeScript],
      options: {
    	  rev: false,
    	  transforms: {
              scripts: lazypipe()
              	.pipe(typescript, createTsProject())
              	.pipe(moduleNameInjector, {rootDir: 'src/main/ui/app/', prefix: 'autuacao/'})
              	.pipe(ngAnnotate)
              	.pipe(embedTemplates, {
              		skipErrors: true, 
              		minimize: {
              			empty : true,
              			spare : true,
              			quotes: true
              		}
              	})
          }
      }
    }
  },
  copy: [{
	  src : path.join(conf.paths.app, '**/*.json'),
      base: conf.paths.app
  }]
};