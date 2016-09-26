'use strict';
var myApp = angular.module('myApp', ['ngAnimate']);

function getProductTypeAttributes(){
    var productTypeSelect = document.getElementById("productTypeSelect");
    productTypeSelect.addEventListener("change", function() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                console.log(xhttp.responseText);
            }
        };
        xhttp.open("GET", "/attributesOfType?typeName=" + productTypeSelect.options[productTypeSelect.selectedIndex].text + "", true);
        xhttp.setRequestHeader('Accept', 'application/json');
        xhttp.send();
    });
};

myApp.controller('CreateProductController', ['$scope', '$http',function($scope, $http) {
    $scope.data = {
        selection : null
    };

    $scope.attributesOfType = null;

    $scope.getAttributes = function(){
    $http.get("/attributesOfType?typeName=" + $scope.data.selection)
        .then(function(response) {
            $scope.attributesOfType = response.data;
            console.log(response.data);
        });
    };
}]);
