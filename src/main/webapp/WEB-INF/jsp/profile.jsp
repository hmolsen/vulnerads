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
        TOTAL FATAL! ${error}.
    </div>
</c:if>
<div class="row no-margin">
    <div class="col-md-4 col-md-offset-4">
        <h1><spring:message code="profile.heading"/></h1>

        <h3>Kontotyp: ${authority}</h3>
        <form:form action="profile" method="POST">
            <div class="form-group">
                <form:input path="username" class="form-control" placeholder="Benutzername"/>
                <form:errors path="username" cssClass="has-error"/>
            </div>
            <div class="form-group">
                <form:input path="firstname" class="form-control" placeholder="Vorname"/>
                <form:errors path="firstname" cssClass="has-error"/>
            </div>
            <div class="form-group">
                <form:input path="lastname" class="form-control" placeholder="Nachname"/>
                <form:errors path="lastname" cssClass="has-error"/>
            </div>
            <div class="form-group">
                <form:input path="creditcardnumber" class="form-control" placeholder="Kreditkartennummer"/>
                <form:errors path="creditcardnumber" cssClass="has-error"/>
            </div>
            <div class="form-group">
                <form:input path="phonenumber" class="form-control" placeholder="Telefonnummer"/>
                <form:errors path="phonenumber" cssClass="has-error"/>
            </div>
            <div class="form-group">
                <form:input path="zip" class="form-control" placeholder="PLZ"/>
                <form:errors path="zip" cssClass="has-error"/>
            </div>
            <div class="form-group">
                <form:input path="town" class="form-control" placeholder="Ort"/>
                <form:errors path="town" cssClass="has-error"/>
            </div>
            <div class="form-group">
                <form:password path="password" class="form-control" placeholder="Passwort"/>
                <form:errors path="password" cssClass="has-error"/>
            </div>
            <div class="form-group">
                <form:password path="password2" class="form-control" placeholder="Passwort wiederholen"/>
                <form:errors path="password2" cssClass="has-error"/>
            </div>
            <!-- div class="form-group">
            <label for="tfa_enabled">2 Faktor Authentifizierung: </label>

                <div class="checkbox pull-right no-margin">
                    <form:checkbox path="tfaEnabled" id="tfa_enabled" data-toggle="toggle" data-on="Aktiviert"
                                   data-off="Deaktiviert" data-onstyle="success" data-offstyle="danger"/>
                    <form:errors path="tfaEnabled" cssClass="has-error"/>
                </div>
            </div -->
            <input name="submit" class="btn btn-primary" type="submit" value="<spring:message code="save"/>"/>
            <form:hidden path="userid"/>
        </form:form>

    </div>

    <div class="modal fade" id="showtfasecret" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title custom_align" id="Heading">2 Faktor Authentifizierung</h4>
                </div>
                <div class="modal-body">

                    <div class="alert alert-warning">
                        <span class="glyphicon glyphicon-warning-sign"></span>
                        <b>Neuer 2-Faktor Code</b>
                        <br/>
                        Alle alten Codes wurden deativiert. Scannen Sie den neuen Code mit z.B. der Google Authenticator
                        App um 2-Faktor Authentifizierung zu verwenden.
                        <br/>
                        Dieser Code wird nicht erneut angezeigt.
                    </div>
                    <img class="center-block lazy" data-src-lazy="/profile/tfasecret.png"/>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span> Schließen
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="modules/scripts.jsp"/>
<script src="/resources/js/userprofile.js" type="application/javascript"></script>
</body>
</html>