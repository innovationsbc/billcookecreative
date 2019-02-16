var gulp            = require('gulp');
var proxyMiddleware = require('http-proxy-middleware');
var browserSync     = require('browser-sync').create();
var less            = require('gulp-less');
var header          = require('gulp-header');
var cleanCSS        = require('gulp-clean-css');
var rename          = require("gulp-rename");
var uglify          = require('gulp-uglify');
var pkg             = require('./package.json');

// Set the banner content
var banner = ['/*!\n',
    ' * <%= pkg.author %> - v<%= pkg.version %>\n',
    ' * Copyright 2017-' + (new Date()).getFullYear(), ' <%= pkg.author %>\n',
    ' * Licensed under <%= pkg.license %>\n',
    ' */\n',
    ''
].join('');

// Compile LESS files from /less into /css
gulp.task('less', function() {
    return gulp.src(['dev/less/*.less', '!dev/less/variables.less', '!dev/less/mixins.less'])
        .pipe(less())
        .pipe(header(banner, { pkg: pkg }))
        .pipe(gulp.dest('dev/css'))
        .pipe(browserSync.reload({
            stream: true
        }))
});

// Minify compiled CSS
gulp.task('minify-css', gulp.series('less', function() {
    return gulp.src(['dev/css/*.css'])
        .pipe(cleanCSS({ compatibility: 'ie8' }))
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('css'))
        .pipe(browserSync.reload({
            stream: true
        }))
}));

// Minify JS
gulp.task('minify-js', function() {
    return gulp.src(['dev/js/*.js'])
        .pipe(uglify())
        .pipe(header(banner, { pkg: pkg }))
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('js'))
        .pipe(browserSync.reload({
            stream: true
        }))
});

// Configure the browserSync task
gulp.task('browserSync', function() {
    //var proxy = proxyMiddleware('./', {target: 'http://localhost:5000/'});
    browserSync.init({
        server: {
            baseDir: './'
        },
    })

    gulp.watch("dev/less/*.less", gulp.series('less'));
    gulp.watch("dev/css/*.css", gulp.series('minify-css'));    
    gulp.watch("dev/js/*.js", gulp.series('minify-js'));
    gulp.watch("*.html", browserSync.reload());
})

// Run everything
gulp.task('default', gulp.series('less', 'minify-css', 'minify-js', 'browserSync'));
