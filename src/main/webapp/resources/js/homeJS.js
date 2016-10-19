'use strict';
var homePageApp = angular.module('homePageApp', ['ngAnimate']);

homePageApp.controller("SearchController", ['$scope', '$http', function($scope, $http){
    $scope.search = function(){
        var query = angular.element('#searchID').val();
        $http.get("/search?q=" + query).then(function(response) {
                    window.location = "/search?q=" + query;
                }, function(response) {
                    console.log(response.textData);
                });
    };
}]);