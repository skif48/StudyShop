'use strict';
var homePageApp = angular.module('homePageApp', ['ngAnimate']);

homePageApp.controller("SearchController", ['$scope', '$http', function($scope, $http){
    $scope.search = function(){
        var query = angular.element('#searchID').val();
        $http.get("/search?q=" + query).then(function(response) {
                    console.log(response.data);
                    //$scope.data.manufacturersOfType = response.data;
                }, function(response) {
                    console.log(response.textData);
                });
    };
}]);