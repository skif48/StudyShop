<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="user" type="com.shop.domain.user.User" -->
<#-- @ftlvariable name="allProducts" type="java.util.List<Product>" -->
<#-- @ftlvariable name="query" type="java.lang.String" -->
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
    <link rel="stylesheet" type="text/css" href="resources/css/searchResultsCSS.css">
    <!-- JS -->
    <script src="resources/js/searchResultsJS.js"></script>
    <!-- fonts -->
    <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body ng-app="searchResultsPageApp">
    <nav class="navbar navbar-default" role="navigation" ng-controller="SearchController">
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
                    <input id="searchID" type="text" class="form-control" placeholder="Search">
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit" ng-click="search()""><i class="glyphicon glyphicon-search"></i></button>
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
        <legend>Search results for query "${query}": </legend>
        <#if allProducts?size <= 3>
            <#assign rowsCount = 1>
        <#else>
            <#assign rowsCount = allProducts?size % 3>
            <#if rowsCount != 0>
                <#assign rowsCount = (allProducts?size / 3) + 1>
            <#else>
                <#assign rowsCount = allProducts?size / 3>
            </#if>
        </#if>
        <#assign productCounter = 0>
        <#list 1..rowsCount as x>
            <div class="row">
                <#list 0..2 as y>
                <#if productCounter < allProducts?size>
                    <div class="col-md-4">
                        <div class="effect eff-11">
                            <img class="img-thumbnail" width="300" height="300" src="/getImage/${allProducts[productCounter].getUuid()}?imgNumber=0" alt="Effect #11" />
                            <div class="overlay">
                                <div class="icon"></div>
                            </div>
                            <div class="gradient"></div>
                            <div class="caption">
                                <h4>${allProducts[productCounter].getLabel()}</h4>
                                <p> Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut. </p>
                                <a class="btn btn-default" href="/product?uuid=${allProducts[productCounter].getUuid()}" title="View More">View More</a>
                            </div>
                        </div>
                     </div>
                     <#assign productCounter++>
                <#else>
                    <#break>
                </#if>
                </#list>
            </div>
        </#list>
    </div>
</body>
</html>
