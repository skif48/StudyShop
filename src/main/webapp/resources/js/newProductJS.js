'use strict';
var myApp = angular.module('myApp', ['ngAnimate']);
myApp.directive('fileModel', ['$parse', function($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function() {
                scope.$apply(function() {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}
]);

myApp.service('fileUpload', ['$http', function($http) {
    this.uploadFileToUrl = function(file, uploadUrl) {
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }).success(function(response) {
            console.log(response.textData);
        }).error(function(response) {
            console.log(response.toString());
        });
    }
}
]);

myApp.controller('CreateProductController', ['$scope', '$http', 'fileUpload', function($scope, $http, fileUpload) {
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
        var product = {};
        var attributesInput = {};
        var attributesSelect = {};
        var attributes = {};
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
                attributesInput[$scope.attributesOfType[attribute].name] = angular.element(elementID).val();
            } else {
                var elementID = '#select' + $scope.attributesOfType[attribute].name;
                attributesSelect[$scope.attributesOfType[attribute].name] = angular.element(elementID).val();
            }
        }

        if ($scope.validation(attributesInput)) {
            var attributes = attributesInput;
            for (var select in attributesSelect) {
                attributes[select] = attributesSelect[select];
            }
            product.attributes = attributes;

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
        $.ajax({
                url: "/addImageByFile",
                type: "POST",
                data: new FormData($("#productCreateForm")[0]),
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false,
                success: function (response) {
                  // Handle upload success
                  $scope.isImageSet = true;
                  $scope.imageID = response.toString();
                },
                error: function (response) {
                  // Handle upload error
                  console.log(response.toString());
                }
        });
    };
}]);
