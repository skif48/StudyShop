<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Simple Shop Simulation</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="resources/css/indexCSS.css">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
    <div class="container">
        <form class="form-horizontal" role="form" action="/login" method="post">

            <fieldset>
                <!-- Form Name -->
                <legend>Login</legend>
                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="emailInput">Email: </label>
                    <div class="col-md-4">
                        <input id="emailInput" type="email" name="email" placeholder="login" class="form-control input-md" required autofocus/>
                    </div>
                </div>
                <!-- Password input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="passwordInput">Password:</label>
                    <div class="col-md-4">
                        <input id="passwordInput" name="password" type="password" placeholder="password" class="form-control input-md" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-4"></div>
                    <div class="col-md-4">
                        <input type="submit" class="btn btn-info" value="Log in" id="submitButton">
                    </div>
                </div>
            </fieldset>
        </form>
        <#if error.isPresent()>
            <p>The email or password you have entered is invalid, try again.</p>
        </#if>
    </div>
    </body>
</html>

