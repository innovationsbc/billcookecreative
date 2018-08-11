var gulp            = require('gulp');
var proxyMiddleware = require('http-proxy-middleware');
var browserSync     = require('browser-sync').create();
var less            = require('gulp-less');
var header          = require('gulp-header');
var cleanCSS        = require('gulp-clean-css');
var rename          = require("gulp-rename");
var uglify          = require('gulp-uglify');
var pkg             = require('./package.json');
var plumber         = require('gulp-plumber');

// Set the banner content
var banner = ['/*!\n',
    ' * <%= pkg.author %> - v<%= pkg.version %>\n',
    ' * Copyright 2017-' + (new Date()).getFullYear(), ' <%= pkg.author %>\n',
    ' * Licensed under <%= pkg.license %>\n',
    ' */\n',
    ''
].join('');

var onError = function (err) {
  console.log(err);
  this.emit('end');
};

// Compile LESS files from /less into /css
gulp.task('less', function() {
    return gulp.src(['src/main/resources/static/dev/less/*.less', '!src/main/resources/static/dev/less/variables.less', '!src/main/resources/static/dev/less/mixins.less'])
        .pipe(plumber({
          errorHandler: onError
        }))
        .pipe(less())
        .pipe(plumber.stop())
        .pipe(header(banner, { pkg: pkg }))
        .pipe(gulp.dest('src/main/resources/static/dev/css'))
});

// Minify compiled CSS
gulp.task('minify-css', ['less'], function() {
    return gulp.src(['src/main/resources/static/dev/css/*.css'])
        .pipe(plumber({
          errorHandler: onError
        }))
        .pipe(cleanCSS({ compatibility: 'ie8' }))
        .pipe(plumber.stop())
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('src/main/resources/static/app/css'))
        .pipe(gulp.dest('target/classes/static/app/css'))
});

// Minify JS
gulp.task('minify-js', function() {
    gulp.src(['src/main/resources/static/dev/js/*.js'])
        .pipe(plumber({
          errorHandler: onError
        }))        
        .pipe(uglify())
        .pipe(plumber.stop())
        .pipe(header(banner, { pkg: pkg }))
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('src/main/resources/static/app/js'))
        .pipe(gulp.dest('target/classes/static/app/js'))
});

// Copy HTML to target
gulp.task('html-copy', function() {
    gulp.src('src/main/resources/templates/**/*')
        .pipe(gulp.dest('target/classes/templates'))

});

// Copy images to target
gulp.task('images-copy', function() {
    gulp.src('src/main/resources/static/app/images')
        .pipe(gulp.dest('target/classes/resources/static/app/images'))

});

// Copy vendor libraries from /node_modules into /vendor
gulp.task('copy', function() {
    // gulp.src(['node_modules/bootstrap/dist/js/*.min.js', '!**/npm.js'])
    //     .pipe(gulp.dest('src/main/resources/static/app/js'))
})

// Testing js copy
gulp.task('testing-js', function() {
    gulp.src(['src/main/resources/static/dev/js/*.js'])
        .pipe(plumber({
          errorHandler: onError
        }))        
        .pipe(plumber.stop())
        .pipe(gulp.dest('src/main/resources/static/app/js'))
        .pipe(gulp.dest('target/classes/static/app/js'))
});

// Run everything
gulp.task('default', ['less', 'minify-css', 'minify-js', 'copy']);

// Configure the browserSync task
gulp.task('browserSync', function() {
    var proxy = proxyMiddleware('/', {target: 'http://localhost:8080/'});
    browserSync.init({
        server: {
            baseDir: ".",
            routes: { "/": "src/main/resources/templates" },
    //        middleware: [ proxy ]
        },
        startPath: '/'
    });

    // Reloads the browser 
    // gulp.watch('src/main/resources/templates/*.html', browserSync.reload);
})

// Dev task 
gulp.task('dev', ['less', 'minify-css', 'minify-js'], function() {

    gulp.watch('src/main/resources/static/dev/less/*.less', ['less']);
    gulp.watch('src/main/resources/static/dev/css/*.css', ['minify-css']);    
    gulp.watch('src/main/resources/static/dev/js/*.js', ['minify-js']);
    gulp.watch('src/main/resources/templates/**/*.html', ['html-copy']);
    gulp.watch('src/main/resources/static/app/images/*.*', ['images-copy']);

    //gulp.watch('src/main/resources/static/dev/js/*.js', ['testing-js']);
});
