<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
    
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

<form action="/language" method="post">
    <input type="text"  name="language">

    <input type="submit" value="<fmt:message key="language.input"/>">
</form>

<form action="/login" method="post">
    <label for="login"><fmt:message key="login.label"/>: </label>
    <input type="text" id="login" name="login" placeholder="<fmt:message key="login.input"/>" required>

    <label for="password"><fmt:message key="password.label"/>:</label>
    <input type="password" id="password" name="password" placeholder="<fmt:message key="password.input"/>" required>

    <input type="submit" value="<fmt:message key="input.login"/>">
</form>
<form action="/registration" method="get">
    <input type="submit" value="<fmt:message key="registration.go"/>">
</form>
</body>
</html>