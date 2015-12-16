var adminApp=angular.module('adminApp', ['ngRoute','homeApp']);


adminApp.config(['$routeProvider',
     function($routeProvider) {
       $routeProvider.
         otherwise({
           redirectTo: '/'
         });
     }]);

