'use strict';
var searchResultsPageApp = angular.module('searchResultsPageApp', ['ngAnimate']);

searchResultsPageApp.controller("SearchController", ['$scope', '$http', function($scope, $http){
    $scope.search = function(){
        var query = angular.element('#searchID').val();
        $http.get("/search?q=" + query).then(function(response) {
                    window.location = "/search?q=" + query;
                    //$scope.data.manufacturersOfType = response.data;
                }, function(response) {
                    console.log(response.textData);
                });
    };
}]);