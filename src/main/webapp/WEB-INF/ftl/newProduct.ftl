<#-- @ftlvariable name="user" type="com.shop.domain.user.User" -->
<#-- @ftlvariable name="productTypes" type="java.util.List<ProductType>" -->
<!DOCTYPE HTML>
<html>
<head>
    <title>Simple Shop Simulation</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- AngularJS -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.js"></script>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="resources/css/newProductCSS.css">
    <!-- JS -->
    <script src="resources/js/newProductJS.js"></script>
    <!-- fonts -->
    <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body ng-app="myApp">
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Shop Simulation</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Home</a></li>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Page 1 <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Page 1-1</a></li>
                            <li><a href="#">Page 1-2</a></li>
                            <li><a href="#">Page 1-3</a></li>
                        </ul>
                    </li>
                    <li><a href="#">Page 2</a></li>
                    <li><a href="#">Page 3</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <#if user??>
                        <#assign id = user.getId()>
                            <li><a href="/user/${id}"><span class="glyphicon glyphicon-user"></span><span id="email"> ${user.getEmail()}</span></a>
                            </li>
                            <#else>
                                <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                                <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    </#if>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <form class="form-horizontal" id="productCreateForm" role="form" ng-controller="CreateProductController">
            <fieldset>
                <legend>Create Product</legend>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="manufacturerSelect">Manufacturer: </label>
                    <div class="col-md-4">
                        <select class="form-control" id="manufacturerSelect" name="manufacturer">
                            <option ng-repeat="manufacturer in manufacturers">{{manufacturer.name}}</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="labelInput">Label: </label>
                    <div class="col-md-4">
                        <input id="labelInput" type="text" name="label" placeholder="label" class="form-control input-md" required autofocus/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="priceInput">Price: </label>
                    <div class="col-md-4">
                        <input id="priceInput" type="text" name="price" placeholder="price" class="form-control input-md" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="productTypeSelect">Select type:</label>
                    <div class="col-md-4">
                        <select class="form-control" id="productTypeSelect" name="productType" ng-model="data.selection" ng-change="getAttributes()">
                            <#list productTypes as productType>
                                <option>${productType.getName()}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div ng-repeat="attribute in attributesOfType">
                    <div class="form-group">
                        <div class="row">
                            <label class="col-md-4 control-label" for="labelInput"> {{attribute.name}} : </label>
                            <div class="col-md-4">
                                <div ng-if="attribute.inputType == 'TEXT'">
                                    <input ng-attr-id="input{{attribute.name}}" name="{{attribute.name}} "type="text" placeholder="{{attribute.name}}" class="form-control input-md" required/>
                                </div>
                                <div ng-if="attribute.inputType == 'ENUMERABLE'">
                                    <select class="form-control" ng-attr-id="select{{attribute.name}}" name={{attribute.name}}>
                                        <option ng-repeat="enumerable in attribute.enumerableAttributeValueSet" required>{{enumerable.value}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4"></div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="col-md-4 control-label" for="imageUpload"> Image : </label>
                        <div class="col-md-4">
                            <input id="imageUpload" type="file" name="imageUploadad" accept="image/*" />
                        </div>
                        <div class="col-md-4">
                            <input type="submit" class="btn btn-info" value="Upload image" id="imageButton" ng-click="uploadImage()"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4"></div>
                        <div class="col-md-4">
                            <input type="submit" class="btn btn-info" value="Create product" id="submitButton" ng-click="sendProductData()"/>
                        </div>
                        <div class="col-md-4"></div>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</body>
</html>