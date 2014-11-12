<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><spring:message code="index.title"/></title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/vulnerapp.css" rel="stylesheet">
</head>
<body>
<jsp:include page="modules/header.jsp"/>
<c:if test="${success}">
    <div class="alert alert-success" role="alert">
        ${username}, Registrierung erfolgreich! Sie k&ouml;nnen sich nun <a href="/login">einloggen</a>.
    </div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
            ${username}, Registrierung fehlgeschlagen! ${error}.
    </div>
</c:if>
<div class="row">
    <div class="col-md-5"></div>
    <div class="col-md-2">
        <h1><spring:message code="register.heading"/></h1>
        <form:form action="register" method="POST" accept-charset="ISO-8859-1">

            <div class="form-group">
                <form:input path="username" class="form-control" placeholder="Username" />
                <form:errors path="username" cssClass="has-error" />
            </div>
            <div class="form-group">
                <form:password path="password" class="form-control" placeholder="Password"/>
                <form:errors path="password" cssClass="has-error" />
            </div>
            <div class="form-group">
                <form:password path="password2" class="form-control" placeholder="Repeat password"/>
                <form:errors path="password2" cssClass="has-error" />
            </div>
            <input name="submit" class="btn btn-primary" type="submit" value="<spring:message code="register.heading"/>"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </div>
    <div class="col-md-5"></div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js" type="application/javascript"></script>
</body>
</html>