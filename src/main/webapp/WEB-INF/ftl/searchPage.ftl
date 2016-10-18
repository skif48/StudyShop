<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="user" type="com.shop.domain.user.User" -->
<#-- @ftlvariable name="productTypes" type="java.util.List<com.shop.domain.entity.ProductType>" -->
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
    <link rel="stylesheet" type="text/css" href="resources/css/searchPageCSS.css">
    <!-- JS -->
    <script src="resources/js/searchPageJS.js"></script>
    <!-- fonts -->
    <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body ng-app="searchPageApp">
    <nav class="navbar navbar-default" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/">Shop Simulation</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <div class="col-sm-3 col-md-3">
            <form class="navbar-form">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search">
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                    </div>
                </div>
            </form>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/advancedSearch">Advanced Search</a></li>
            <#if user??>
                <#assign role = user.getRole()>
                <#if role == "ADMIN">
                    <li><a href="/adminPage">Admin Page</a></li>
                </#if>
            </#if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <#if user??>
                <#assign id = user.getId()>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="/user/${id}"><span class="glyphicon glyphicon-user"></span><span id="email"> ${user.getEmail()}</span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#"><span class="glyphicon glyphicon-user"></span>Profile details</a></li>
                        <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span>Cart</a></li>
                        <li class="divider"></li>
                        <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
                    </ul>
                </li>
                <#else>
                    <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                    <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
            </#if>
        </ul>
    </div><!-- /.navbar-collapse -->
</nav>
    <div class="container">
        <form class="form-horizontal" id="searchProductForm" role="form" ng-controller="SearchProductController">
            <fieldset>
                <legend>Advanced Search</legend>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="typeInput">Type: </label>
                    <div class="col-md-4">
                        <select class="form-control" id="productTypeSelect" name="productType" ng-model="data.typeSelection" ng-change="getDataForType()">
                        <#list productTypes as productType>
                            <option>${productType.getName()}</option>
                        </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="priceInput">Price: </label>
                    <div class="col-md-2">
                        <input id="priceFrom" type="text" class="form-control" placeholder="from..." required>
                    </div>
                    <div class="col-md-2">
                        <input id="priceTo" type="text" name="priceInput" class="form-control" placeholder="to..." required>
                    </div>
                </div>
                <div ng-if="data.manufacturersOfType != null">
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="manufacturerCheckbox">Manufacturer: </label>
                        <div class="col-md-4">
                            <div ng-repeat="manufacturer in data.manufacturersOfType">
                                <div class="checkbox">
                                    <label><input ng-attr-id="checkBox{{manufacturer.name}}" name="{{manufacturer.name}}" type="checkbox" value=""/>{{manufacturer.name}}</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div ng-repeat="attribute in data.attributesOfType">
                    <div class="form-group">
                        <div class="row">
                            <label class="col-md-4 control-label" for="labelInput"> {{attribute.name}} : </label>
                            <div ng-if="attribute.inputType == 'TEXT'">
                                <div class="col-md-2">
                                    <input ng-attr-id="{{attribute.name}}From" class="form-control" name="{{attribute.name}}From" type="text" placeholder="from..." required/>
                                </div>
                                <div class="col-md-2">
                                    <input ng-attr-id="{{attribute.name}}To" class="form-control" name="{{attribute.name}}To" type="text" placeholder="to..." required/>
                                </div>
                            </div>
                            <div ng-if="attribute.inputType == 'ENUMERABLE'">
                                <div class="col-md-4">
                                    <div class="checkbox" ng-repeat="enumerable in attribute.enumerableAttributeValueSet">
                                        <label><input ng-attr-id="checkBox{{attribute.name}}" name="{{attribute.name}}" type="checkbox" value=""/>{{enumerable.value}}
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4"></div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4"></div>
                        <div class="col-md-4">
                            <input type="submit" class="btn btn-info" value="Search" id="submitButton" ng-click="search()"/>
                        </div>
                        <div class="col-md-4"></div>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</body>
</html>