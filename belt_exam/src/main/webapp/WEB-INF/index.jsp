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
    <title>Login</title>
</head>
<body>
    <div class="container">
        <h1 class="text-danger">${notAllowed}</h1>
        <h1>Login and Registration</h1>
        <form:form action="/register" method="post" modelAttribute="newUser">
            <div class="form-group">
                <label>User Name:</label>
                <form:input path="username" class="form-control" />
                <form:errors path="username" class="text-danger" />
            </div>
            <div class="form-group">
                <label>Email:</label>
                <form:input path="email" class="form-control" />
                <form:errors path="email" class="text-danger" />
            </div>
            <div class="form-group">
                <label>Password:</label>
                <form:password path="password" class="form-control" />
                <form:errors path="password" class="text-danger" />
            </div>
            <div class="form-group">
                <label>Confirm Password:</label>
                <form:password path="confirm" class="form-control" />
                <form:errors path="confirm" class="text-danger" />
            </div>
            <input type="submit" value="Register" class="btn btn-primary" />
        </form:form>
        
        <form:form action="/login" method="post" modelAttribute="newLogin">
            <div class="form-group">
                <label>Email:</label>
                <form:input path="email" class="form-control" />
                <form:errors path="email" class="text-danger" />
            </div>
            <div class="form-group">
                <label>Password:</label>
                <form:password path="password" class="form-control" />
                <form:errors path="password" class="text-danger" />
            </div>
            <input type="submit" value="Login" class="btn btn-success" />
        </form:form>
    </div>
</body>
</html>