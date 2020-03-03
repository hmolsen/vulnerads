<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="modules/head.jsp"/>
<body>
<jsp:include page="modules/header.jsp"/>
<c:if test="${success}">
    <div class="alert alert-success" role="alert">
            ${username}<spring:message code="register.message.success"/>
    </div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
            ${username}<spring:message code="register.message.failed"/>${error}.
    </div>
</c:if>
<div class="row no-margin">
<div class="col-md-5"></div>
    <div class="col-md-2">
        <h1><spring:message code="register.heading"/></h1>
        <form:form action="register" method="POST" accept-charset="ISO-8859-1">

            <div class="form-group">
                <spring:message code="register.field.username.hint" var="usrhint"/>
                <form:input path="username" class="form-control" placeholder="${usrhint}"/>
                <form:errors path="username" cssClass="has-error" />
            </div>
            <div class="form-group">
                <spring:message code="register.field.password.hint" var="pwhint"/>
                <form:password path="password" class="form-control" placeholder="${pwhint}"/>
                <form:errors path="password" cssClass="has-error" />
            </div>
            <div class="form-group">
                <spring:message code="register.field.retype.hint" var="repwhint"/>
                <form:password path="password2" class="form-control" placeholder="${repwhint}"/>
                <form:errors path="password2" cssClass="has-error" />
            </div>
            <input name="submit" class="btn btn-primary" type="submit" value="<spring:message code="register.heading"/>"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </div>
    <div class="col-md-5"></div>
</div>
<jsp:include page="modules/scripts.jsp"/>
</body>
</html>