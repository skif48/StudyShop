'use strict';
var myApp = angular.module('myApp', ['ngAnimate']);


myApp.controller('CreateProductController', ['$scope', '$http', function($scope, $http) {
    $scope.data = {
        selection : null,
        manufacturerSelection : null
    };

    $scope.inputError = "";

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

    $scope.validation = function(attributes){
        for(var attribute in $scope.attributesOfType){
            if(!attributes[attribute] && attribute.inputType == 'TEXT'){
                return false;
            }
        }

        for(var attribute in attributes) {
            var attributeString = attributes[attribute].toString();
            if(isNaN(attributeString)){
                return false;
            }
        }

        return true;
    };

    $scope.sendProductData = function(){
        var product = {};
        var attributesInput = {};
        var attributesSelect = {};
        var attributes = {};
        var uuid = null;
        product.manufacturer = angular.element('#manufacturerSelect').val();
        product.label = angular.element('#labelInput').val();
        product.type = $scope.data.selection;
        for(var attribute in $scope.attributesOfType){
            if( $scope.attributesOfType[attribute].inputType == 'TEXT'){
                var elementID = '#input' + $scope.attributesOfType[attribute].name;
                attributesInput[$scope.attributesOfType[attribute].name] = angular.element(elementID).val();
            } else {
                var elementID = '#select' + $scope.attributesOfType[attribute].name;
                attributesSelect[$scope.attributesOfType[attribute].name] = angular.element(elementID).val();
            }
        }

        if($scope.validation(attributesInput)) {
            var attributes = attributesInput;
            for(var select in attributesSelect){
                attributes[select] = attributesSelect[select];
            }
            product.attributes = attributes;

            var config = {
                headers : {
                    'Content-Type' : 'application/json'
                }
            };

            $http.post('/addProduct', product, config).then(
                function(response){
                    debugger;
                    $scope.uuid = response.data;
                    console.log($scope.uuid.toString());
                    window.location = "/product?uuid=" + $scope.uuid;
                }
            );
        } else {
            alert("Input Error. Check your values!");
        }
    };
}]);
