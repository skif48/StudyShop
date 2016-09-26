<#-- @ftlvariable name="user" type="com.shop.domain.user.User" -->
<#-- @ftlvariable name="productTypes" type="java.util.List<ProductType>" -->
<#-- @ftlvariable name="attributesOfType" type="java.util.List<Attribute>" -->
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
                        <li><a href="/user/${id}"><span class="glyphicon glyphicon-user"></span><span id="email"> ${user.getEmail()}</span></a></li>
                    <#else>
                        <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                        <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    </#if>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <form class="form-horizontal" role="form"  ng-controller="CreateProductController">
            <fieldset>
                <legend>Create Product</legend>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="labelInput">Label: </label>
                    <div class="col-md-4">
                        <input id="labelInput" type="text" name="label" placeholder="label" class="form-control input-md" required autofocus/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="productTypeSelect">Select type:</label>
                    <div class="col-md-4">
                        <select class="form-control" id="productTypeSelect" ng-model="data.selection" ng-change="getAttributes()">
                            <#list productTypes as productType>
                                <option>${productType.getName()}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4">

                        </div>
                        <div class="col-md-4">
                            <div class="animate-switch-container" ng-switch on="data.selection" >
                                <#list productTypes as productType>
                                    <div class="animate-switch" ng-switch-when="${productType.getName()}">
                                        {{attributesOfType}}
                                    </div>
                                </#list>
                            </div>
                        </div>
                        <div class="col-md-4">

                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-4"></div>
                    <div class="col-md-4">
                        <input type="submit" class="btn btn-info" value="Create product" id="submitButton">
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</body>
</html>