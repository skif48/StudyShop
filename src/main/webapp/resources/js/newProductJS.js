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
        }, function(response){
            console.log(response.textData);
        }
        );
    };

    $scope.validation = function(product, attributes){
        for(attribute in $scope.attributesOfType){
            if(!attributes[attribute]){
                return false;
            }
        }

        for(attribute in attributes) {

        }

        return true;
    };

    $scope.sendProductData = function(){
        var product = {};
        var attributes = {};
        var uuid = null;
        product.manufacturer = angular.element('#manufacturerSelect').val();
        product.label = angular.element('#labelInput').val();
        product.type = $scope.data.selection;
        for(var attribute in $scope.attributesOfType){
            console.log($scope.attributesOfType[attribute]);
            var elementID = '#input' + $scope.attributesOfType[attribute].name;
            attributes[$scope.attributesOfType[attribute].name] = angular.element(elementID).val();
        }

        product.attributes = attributes;

        var config = {
            headers : {
                'Content-Type' : 'application/json'
            }
        };

        $http.post('/addProduct', product, config).then(
            function(response){
                $scope.uuid = response.data;
                console.log($scope.uuid);
            }
        );
    };
}]);
