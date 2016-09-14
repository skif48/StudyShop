'use strict';
var myApp = angular.module('myApp', ['ngAnimate']);

myApp.controller('ExampleController', ['$scope', function($scope) {
    $scope.attributes = new Array();
}]);


$(document).ready(function(){
    var productTypeSelect = document.getElementById("productTypeSelect");
    productTypeSelect.addEventListener("change", function() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {

            }
        };
        xhttp.open("GET", "/attributesOfType?typeName=" + productTypeSelect.options[productTypeSelect.selectedIndex].text + "", true);
        xhttp.setRequestHeader('Accept', 'application/json');
        xhttp.send();
    });
});