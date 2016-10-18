'use strict';
var searchPageApp = angular.module('searchPageApp', ['ngAnimate']);

searchPageApp.controller('SearchProductController', ['$scope', '$http', function($scope, $http){
    $scope.data = {
        typeSelection : null,
        manufacturersOfType : null,
        attributesOfType : null
    };

    $scope.getDataForType = function() {
        $http.get("/getManufacturersOfType?type=" + $scope.data.typeSelection).then(function(response) {
            console.log(response.data);
            $scope.data.manufacturersOfType = response.data;
        }, function(response) {
            console.log(response.textData);
        });

        $http.get("/attributesOfType?typeName=" + $scope.data.typeSelection).then(function(response) {
            console.log(response.data);
            $scope.data.attributesOfType = response.data;
        }, function(response){
            console.log(response.textData);
        });
    };

    $scope.search = function(){
        
    };
}]);
