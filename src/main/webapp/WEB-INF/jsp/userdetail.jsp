<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="modules/head.jsp"/>
<body class="user">
<jsp:include page="modules/header.jsp"/>
<div class="row no-margin">
    <div class="col-md-4 col-md-offset-4">
        <h1><spring:message code="userdetail.title"/></h1>
        <form:form>
            <div class="form-group">
                <spring:message code="userdetail.username" var="username"/>
                <form:input path="username" class="form-control" placeholder="${username}" disabled="true"/>
            </div>
            <div class="form-group">
                <spring:message code="userdetail.firstname" var="firstname"/>
                <form:input path="firstname" class="form-control" placeholder="${firstname}" disabled="true"/>
            </div>
            <div class="form-group">
                <spring:message code="userdetail.lastname" var="lastname"/>
                <form:input path="lastname" class="form-control" placeholder="${lastname}" disabled="true"/>
            </div>
            <div class="form-group">
                <spring:message code="userdetail.phonenumber" var="phonenumber"/>
                <form:input path="phonenumber" class="form-control" placeholder="${phonenumber}" disabled="true"/>
            </div>
            <div class="form-group">
                <spring:message code="userdetail.zip" var="zip"/>
                <form:input path="zip" class="form-control" placeholder="${zip}" disabled="true"/>
            </div>
            <div class="form-group">
                <spring:message code="userdetail.town" var="town"/>
                <form:input path="town" class="form-control" placeholder="${town}" disabled="true"/>
            </div>
        </form:form>
    </div>
</div>
<jsp:include page="modules/scripts.jsp"/>
</body>
</html>
