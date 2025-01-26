<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/css/registration.css">
</head>
<body>
<form action="/registration" method="post">

        <div>
            <label for="login">Login:</label>
            <input type="text" id="login" name="login" placeholder="Your login.." required>
            <c:forEach var="loginValidationErrorMessage" items="${loginValidationErrorMessages}">
                <p class="validation-message">
                        ${loginValidationErrorMessage}
                </p>
            </c:forEach>
        </div>

        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Your password.." required>
        </div>

        <c:forEach var="passwordValidationErrorMessage" items="${passwordValidationErrorMessages}">
            <p class="validation-message">
                    ${passwordValidationErrorMessage}
            </p>
        </c:forEach>


        <input type="submit" value="Register">
    
</form>
</body>
</html>