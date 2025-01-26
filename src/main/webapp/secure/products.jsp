<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        .product-delete-button{
            color: red;
        }
    </style>
</head>
<body>
<h1>Product List</h1>
<form action="/secure/products" method="post">
    <fieldset>
        <div>
            <label> Name: <input type="text" name="name"></label>
        </div>
        <div>
            <label> imageUrl: <input type="text" name="imageUrl"></label>
        </div>
        <button type="submit">Add product</button>
    </fieldset>
</form>

<c:forEach var="product" items="${products}">
    <p>${product.name}</p>
    <img src="${product.imageUrl}">
    <form action="/secure/products" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="hidden" name="productId" value="${product.id}">
        <button type="submit" class="product-delete-button">delete</button>
    </form>
</c:forEach>
</body>
</html>