'use strict';
var myApp = angular.module('myApp', ['ngAnimate']);

myApp.controller('CreateProductController', ['$scope', '$http', function($scope, $http) {
    $scope.data = {
        selection: null ,
        manufacturerSelection: null
    };
    $scope.attributesOfType = null ;
    $scope.manufacturers = null ;
    $scope.isImageSet = false;
    $scope.imageID = 1;

    $http.get("/manufacturers").then(function(response) {
        $scope.manufacturers = response.data;
        console.log($scope.manufacturers);
    });

    $scope.getAttributes = function() {
        $http.get("/attributesOfType?typeName=" + $scope.data.selection).then(function(response) {
            $scope.attributesOfType = response.data;
        }, function(response) {
            console.log(response.textData);
        });
    };

    $scope.validation = function(attributes) {
        for (var attribute in $scope.attributesOfType) {
            if (!attributes[attribute] && attribute.inputType == 'TEXT') {
                return false;
            }
        }
        for (var attribute in attributes) {
            var attributeString = attributes[attribute].toString();
            if (isNaN(attributeString)) {
                alert("Input error! " + attribute + " should be a number");
                return false;
            }
        }
        return true;
    };

    $scope.sendProductData = function() {
        if ($scope.isImageSet == false){
            alert("Upload image!");
            return;
        }

        var product = {};
        var attributesInput = {};
        var attributesSelect = {};
        var attributes = {};
        var enumerableAttributes = {};
        var uuid = null ;

        product.manufacturer = angular.element('#manufacturerSelect').val();
        product.label = angular.element('#labelInput').val();
        product.type = $scope.data.selection;
        product.price = angular.element('#priceInput').val();
        product.imageID = $scope.imageID;

        if (isNaN(product.price.toString())) {
            alert("Input error! Price should be a number!");
            return;
        }

        for (var attribute in $scope.attributesOfType) {
            if ($scope.attributesOfType[attribute].inputType == 'TEXT') {
                var elementID = '#input' + $scope.attributesOfType[attribute].name;
                attributesInput[$scope.attributesOfType[attribute].name] = parseFloat(angular.element(elementID).val());
            } else {
                var elementID = '#select' + $scope.attributesOfType[attribute].name;
                enumerableAttributes[$scope.attributesOfType[attribute].name] = angular.element(elementID).val();
            }
        }

        if ($scope.validation(attributesInput)) {
            product.attributes = attributesInput;
            product.enumerableAttributes = enumerableAttributes;

            var config = {
                headers: {
                    'Content-Type': 'application/json'
                }
            };

            $http.post('/addProduct', product, config).then(function(response) {
                $scope.uuid = response.data;
                console.log($scope.uuid.toString());
                window.location = "/product?uuid=" + $scope.uuid;
            });
        }
    };

    $scope.uploadImage = function() {
        var dataSend = new FormData($("#productCreateForm")[0]);
        for(var pair of dataSend.entries()) {
            if(pair[0] = "imageUploadad" && pair[1].size == 0) {
               alert('No image selected!');
               return;
            }
        }
        var ajaxObj = {
            url: "/addImageByFile",
            type: "POST",
            data: dataSend,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            success: function(response) {
                    // Handle upload success
                        $scope.isImageSet = true;
                        $scope.imageID = response.toString();
                    },
            error: function(response) {
                        // Handle upload error
                        console.log(response.toString());
                   }
            };
            $.ajax(ajaxObj);
    };
}]);
