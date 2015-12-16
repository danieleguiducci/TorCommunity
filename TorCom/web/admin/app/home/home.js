(function(){

var homeApp=angular.module('homeApp', ['ngRoute']);
homeApp.controller('HomeCtrl', ['$scope', '$http',
          function ($scope, $http) {

          }]);
homeApp.config(['$routeProvider',
     function($routeProvider) {
       $routeProvider.
         when('/', {
           templateUrl: 'app/home/home.html',
           controller: 'HomeCtrl'
         });
     }]);
})();