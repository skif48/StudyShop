'use strict';
var searchPageApp = angular.module('searchPageApp', ['ngAnimate']);

searchPageApp.controller('SearchProductController', ['$scope', '$http', function($scope, $http){
    $scope.data = {
        typeSelection : null,
        manufacturersOfType : null,
        attributesOfType : null,
        checkedEnumerableAttributes : null
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
        var searchRequest = {};
        var manufacturers = [];
        var priceFrom = parseFloat(angular.element("#priceFrom").val());
        var priceTo   = parseFloat(angular.element("#priceTo").val());
        var attributesFromTo = {};
        var checkedEnumerableAttributes = {};

        for(var attribute in $scope.data.attributesOfType){
            if($scope.data.attributesOfType[attribute].inputType == 'TEXT'){
                var from = parseFloat(angular.element("#" + $scope.data.attributesOfType[attribute].name + "From").val());
                var to   = parseFloat(angular.element("#" + $scope.data.attributesOfType[attribute].name + "To").val());
                attributesFromTo[$scope.data.attributesOfType[attribute].name] = [from, to];
            } else {
                var checkedEnumerableAttributesArray = [];
                for(var enumerable in $scope.data.attributesOfType[attribute].enumerableAttributeValueSet){
                    if($scope.data.attributesOfType[attribute].enumerableAttributeValueSet[enumerable].selected){
                        checkedEnumerableAttributesArray.push($scope.data.attributesOfType[attribute].enumerableAttributeValueSet[enumerable].value);
                    }
                    checkedEnumerableAttributes[$scope.data.attributesOfType[attribute].name] = checkedEnumerableAttributesArray;
                }
            }
        }

        searchRequest["manufacturers"] = manufacturers;
        searchRequest["priceFrom"] = priceFrom;
        searchRequest["priceTo"] = priceTo;
        searchRequest["attributesFromTo"] = attributesFromTo;
        searchRequest["checkedEnumerableAttributes"] = checkedEnumerableAttributes;
    };
}]);
