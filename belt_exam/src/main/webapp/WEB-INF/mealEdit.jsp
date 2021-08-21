<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Document</title>
</head>
<body>
    <h1>Edit: ${idea.name}</h1>
    <h1 class="text-danger">${notAllowed}</h1>

    <form:form action="/idea/update/${idea.id}" method="POST" modelAttribute="idea">
        <p>
            <form:label path="name">Content:</form:label>
            <form:input path="name"/>
        </p>
        <input type="submit" value="Update">
    </form:form>
    <a href="/delete/${idea.id}">Delete</a>
</body>
</html>