'use strict';
var myApp = angular.module('myApp', ['ngAnimate']);

myApp.controller('CreateProductController', ['$scope', '$http', function($scope, $http) {
    $scope.data = {
        selection : null,
        manufacturerSelection : null
    };

    $scope.attributesOfType = null;
    $scope.manufacturers    = null;

    $http.get("/manufacturers")
    .then(function(response) {
        $scope.manufacturers = response.data;
        console.log($scope.manufacturers);
    });

    $scope.getAttributes = function(){
        $http.get("/attributesOfType?typeName=" + $scope.data.selection)
        .then(function(response) {
            $scope.attributesOfType = response.data;
        });
    };

    $scope.validation = function(product){
        for(attribute in $scope.attributesOfType){
            if(!product[attribute]){
                return false;
            }
        }

        return true;
    };

    $scope.sendProductData = function(){
        debugger;
        var product = {};
        product.manufacturer = $scope.manufacturer;
        console.log($scope.attributesOfType);
        for(var attribute in $scope.attributesOfType){
            console.log(attribute);
            product[attribute.name] = $scope[attribute.name];
        }

        console.log(product);
    };
}]);
