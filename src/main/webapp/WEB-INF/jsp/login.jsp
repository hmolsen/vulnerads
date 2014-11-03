<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><spring:message code="index.title"/></title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/vulnerapp.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">Wrong username and/or password.</div>
</c:if>
<div class="row">
    <div class="col-md-5"></div>
    <div class="col-md-2">
        <h1><spring:message code="login.heading"/></h1>
        <form name='loginForm'
              action="<c:url value='login'/>" method='POST'>

            <div class="form-group">
                <input type='text' class="form-control" name='username' placeholder="Username">
            </div>
            <div class="form-group">
                <input type='password' class="form-control" name='password' placeholder="Password"/>
            </div>
            <input name="submit" class="btn btn-primary" type="submit" value="<spring:message code="login.heading"/>"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
    <div class="col-md-5"></div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js" type="application/javascript"></script>
</body>
</html>