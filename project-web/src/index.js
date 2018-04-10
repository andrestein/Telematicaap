var angular = require('angular');

require('angular-material');
require('angular-ui-router');
require('angular-aria');
require('angular-animate');
require('angular-material');
require('angular-messages');
require('angular-moment');
require('angular-resource');
require('angular-cookies');

var virtual = require('./app/components/Virtual/Virtual');
var Navbar = require('./app/components/navbar/Navbar');
var routesConfig = require('./routes');
var config = require('./config');
var Home = require('./app/components/home/Home');
var Api = require('./app/services/Data');
var Util = require('./app/services/Util');
var Items = require('./app/services/Items');

require('./index.css');
require('angular-material/angular-material.css');

angular
  .module('app', [
    'ui.router',
    'ngAria',
    'ngAnimate',
    'ngResource',
    'ngCookies',
    'ngMessages',
    'ngMaterial',
    'angularMoment'
  ])
  .config(config)
  .config(routesConfig)
  .component('home', Home)
  .component('virtual', virtual)
  .component('navbarComponent', Navbar)
  .service('Util', Util)
  .service('Items', Items)
  .factory('Api', Api);
