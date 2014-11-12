<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="modules/head.jsp"/>
<body>
<jsp:include page="modules/header.jsp"/>
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">Benutzername oder Passwort falsch.</div>
</c:if>
<div class="row">
    <div class="col-md-5"></div>
    <div class="col-md-2">
        <h1><spring:message code="login.heading"/></h1>
        <form name='loginForm' action="/login" method='POST' accept-charset="ISO-8859-1">

            <div class="form-group">
                <input type='text' class="form-control" name='username' placeholder="Benutzername">
            </div>
            <div class="form-group">
                <input type='password' class="form-control" name='password' placeholder="Passwort"/>
            </div>
            <input name="submit" class="btn btn-primary" type="submit" value="<spring:message code="login.heading"/>"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
    <div class="col-md-5"></div>
</div>
<jsp:include page="modules/scripts.jsp"/>
</body>
</html>