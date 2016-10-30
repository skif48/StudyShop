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

        for(var manufacturer in $scope.data.manufacturersOfType){
            if($scope.data.manufacturersOfType[manufacturer].selected){
                manufacturers.push($scope.data.manufacturersOfType[manufacturer].name);
            }
        }

        searchRequest["manufacturers"] = manufacturers;
        searchRequest["priceFrom"] = priceFrom;
        searchRequest["priceTo"] = priceTo;
        searchRequest["attributesFromTo"] = attributesFromTo;
        searchRequest["checkedEnumerableAttributes"] = checkedEnumerableAttributes;

        var url = "/advancedSearchResults";
        if(searchRequest["manufacturers"].length != 0){
            var counter = 0;
            for(var manufacturer in searchRequest["manufacturers"]){
                if(counter == 0){
                    url = url + "?manufacturer=";
                    url = url + searchRequest["manufacturers"][manufacturer];
                } else {
                    url = url + "&manufacturer="
                    url = url + searchRequest["manufacturers"][manufacturer];
                }

                counter++;
            }
        } else {
            for(var manufacturer in $scope.data.manufacturersOfType){
                if(counter == 0){
                    url = url + "?manufacturer=";
                    url = url + $scope.data.manufacturersOfType[manufacturer].name;
                } else {
                    url = url + "&manufacturer="
                    url = url + $scope.data.manufacturersOfType[manufacturer].name;
                }

                counter++;
            }
        }

        url = url + "&type=" + $scope.data.typeSelection;

        if(isNaN(searchRequest["priceFrom"]))
            url = url + "&priceFrom=0";
        else
            url = url + "&priceFrom=" + searchRequest["priceFrom"];

        if(searchRequest["priceTo"] != undefined)
            url = url + "&priceTo=2147483647";
        else
            url = url + "&priceTo=" + searchRequest["priceTo"];

        for(var attribute in searchRequest["attributesFromTo"]){
            if(isNaN(searchRequest["attributesFromTo"][attribute][0])){
                url = url + "&" + attribute + "From" + "=";
                url = url + "0";
            } else {
                url = url + "&" + attribute + "From" + "=";
                url = url + searchRequest["attributesFromTo"][attribute][0];
            }

            if(isNaN(searchRequest["attributesFromTo"][attribute][1])){
                url = url + "&" + attribute + "To" + "=";
                url = url + "2147483647"
            } else {
                url = url + "&" + attribute + "To" + "=";
                url = url + searchRequest["attributesFromTo"][attribute][1];
            }
        }

        function isEmptyObject(obj) {
            return Object.getOwnPropertyNames(obj).length === 0;
        }

        for(var checked in searchRequest["checkedEnumerableAttributes"]){
            if(searchRequest["checkedEnumerableAttributes"][checked].length == 0){
                for(var attribute in $scope.data.attributesOfType){
                    if($scope.data.attributesOfType[attribute].name == checked){
                        for(var enumerable in $scope.data.attributesOfType[attribute].enumerableAttributeValueSet){
                            url = url + "&" + $scope.data.attributesOfType[attribute].name + "=" + $scope.data.attributesOfType[attribute].enumerableAttributeValueSet[enumerable].value;
                        }
                    }
                }
            } else {
                for(var checked in searchRequest["checkedEnumerableAttributes"]){
                    var counter = 0;
                    for(var value in searchRequest["checkedEnumerableAttributes"][checked]){
                        url = url + "&" + checked + "=";
                        var temp = searchRequest["checkedEnumerableAttributes"][checked][value];
                        url = url + temp;
                        counter++;
                    }
                }
            }
        }
        window.location = url;
    };
}]);
