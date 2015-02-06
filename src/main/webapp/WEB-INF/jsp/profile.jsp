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
<c:if test="${success}">
    <div class="alert alert-success" role="alert">
            Profil&auml;nderungen gespeichert.
    </div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
            Benutzerprofil konnte nicht gespeichert werden! ${error}.
    </div>
</c:if>
<div class="row no-margin">
    <div class="col-md-4 col-md-offset-4">
    <h1><spring:message code="profile.heading"/></h1>
        <form:form action="profile" method="POST" accept-charset="ISO-8859-1">
            <div class="form-group">
                <form:input path="firstname" class="form-control" placeholder="Vorname" />
                <form:errors path="firstname" cssClass="has-error" />
            </div>
            <div class="form-group">
                <form:input path="lastname" class="form-control" placeholder="Nachname" />
                <form:errors path="lastname" cssClass="has-error" />
            </div>
            <div class="form-group">
                <form:input path="creditcardnumber" class="form-control" placeholder="Kreditkartennummer" />
                <form:errors path="creditcardnumber" cssClass="has-error" />
            </div>
            <div class="form-group">
                <form:input path="phonenumber" class="form-control" placeholder="Telefonnummer" />
                <form:errors path="phonenumber" cssClass="has-error" />
            </div>
            <div class="form-group">
                <form:input path="zip" class="form-control" placeholder="PLZ" />
                <form:errors path="zip" cssClass="has-error" />
            </div>
            <div class="form-group">
                <form:input path="town" class="form-control" placeholder="Ort" />
                <form:errors path="town" cssClass="has-error" />
            </div>
            <div class="form-group">
                <form:password path="password" class="form-control" placeholder="Passwort"/>
                <form:errors path="password" cssClass="has-error" />
            </div>
            <div class="form-group">
                <form:password path="password2" class="form-control" placeholder="Passwort wiederholen"/>
                <form:errors path="password2" cssClass="has-error" />
            </div>
            <input name="submit" class="btn btn-primary" type="submit" value="<spring:message code="save"/>"/>
            <form:hidden path="userid"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </div>
</div>
<jsp:include page="modules/scripts.jsp"/>
</body>
</html>