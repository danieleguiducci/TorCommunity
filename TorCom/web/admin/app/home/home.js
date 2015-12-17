(function(){

var homeApp=angular.module('homeApp', ['ngRoute']);
homeApp.controller('HomeCtrl', ['$scope', '$http',
          function ($scope, $http) {
            $scope.createNewCommunity=function() {
                $http.post('/api/createcommunity', {}).then(function(resp){
                    console.log("SUCCESSO",resp);
                },function(){});
            };
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