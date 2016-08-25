<#-- @ftlvariable name="attributes" type="java.util.List<com.shop.domain.entity.Attribute>" -->
<#-- @ftlvariable name="attributeValues" type="java.util.List<com.shop.domain.entity.AttributeValue>" -->
<#-- @ftlvariable name="product" type=com.shop.domain.entity.Product" -->
<#-- @ftlvariable name="user" type="com.shop.domain.user.User" -->
<!DOCTYPE HTML>
<html ng-app="app">
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
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="resources/css/productCSS.css">
    <!-- JS -->
    <script src="resources/js/productJS.js"></script>
    <!-- fonts -->
    <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
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
       <div class="container" id="productInfo">
           <div class="row">
               <div class="col-md-6">
                   <span>${product.getLabel()}</span>
               </div>
               <div class="col-md-3">
                   <span class="pull-right">100$</span>
               </div>
               <div class="col-md-3">
                   <button type="button" class="btn btn-default btn-sm">
                       <span class="glyphicon glyphicon-shopping-cart"></span> Add to cart
                   </button>
               </div>
           </div>
           <div class="row">
               <div class="col-md-6">
                   <table class="table table-hover">
                       <thead>
                           <tr>
                               <th>Attribute</th>
                               <th>Value</th>
                           </tr>
                       </thead>
                       <tbody>
                       <#assign y = 0>
                       <#list attributeValues as attributeValue>
                           <tr>
                               <td>${attributes[y].name}</td>
                               <td>${attributeValue.value}</td>
                           </tr>
                           <#assign y++>
                       </#list>
                       </tbody>
                   </table>
               </div>
               <div class="rol-md-6">
                   <div class="col-md-6">
                       <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false" ng-controller="carousel">
                           <!-- Indicators -->
                           <ol class="carousel-indicators">
                               <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                               <li data-target="#myCarousel" data-slide-to="1"></li>
                               <li data-target="#myCarousel" data-slide-to="2"></li>
                               <li data-target="#myCarousel" data-slide-to="3"></li>
                           </ol>

                           <!-- Wrapper for slides -->
                           <div class="carousel-inner" role="listbox">
                               <div class="item active">
                                   <img src="https://unsplash.it/600/400" alt="Chania">
                               </div>

                               <div class="item">
                                   <img src="https://unsplash.it/601/401" alt="Chania">
                               </div>

                               <div class="item">
                                   <img src="https://unsplash.it/602/402" alt="Flower">
                               </div>

                               <div class="item">
                                   <img src="https://unsplash.it/603/403" alt="Flower">
                               </div>
                           </div>

                            <!-- Left and right controls -->
                            <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                       </div>
                   </div>
               </div>
           </div>
       </div>
</body>
</html>