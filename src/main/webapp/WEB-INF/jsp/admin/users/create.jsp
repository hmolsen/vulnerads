<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<h1><spring:message code="user.create"/></h1>
<a href="<spring:url value="/admin/users/list" />"><spring:message code="user.list"/></a>
<form:form method="POST" action="/admin/users/create" modelAttribute="form">
    <form:errors path="" element="div"/>
    <div>
        <form:label path="username"><spring:message code="user.username"/></form:label>
        <form:input path="username"/>
        <form:errors path="username"/>
    </div>
    <div>
        <form:label path="password"><spring:message code="user.password"/></form:label>
        <form:password path="password"/>
        <form:errors path="password"/>
    </div>
    <div>
        <form:label path="passwordRetyped"><spring:message code="user.passwordretype"/></form:label>
        <form:password path="passwordRetyped"/>
        <form:errors path="passwordRetyped"/>
    </div>
    <div>
        <input type="submit"/>
    </div>
</form:form>
</body>
</html>