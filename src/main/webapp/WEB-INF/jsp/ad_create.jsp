<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title><spring:message code="index.title"/></title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/vulnerapp.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header_user.jsp"/>
<form:form action="/ad/create" method="post" accept-charset="ISO-8859-1">
    <div class="container-fluid" id="body-container">
        <div class="container container-pad" id="ad-listing">

            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <div class="input-group">
                        <span class="input-group-addon">Titel</span>
                        <form:input path="title" placeholder="Titel" cssClass="form-control" />
                    </div>
                    <div class="brdr own-ad pad-10 box-shad btm-mrg-20">
                        <div class="row">
                            <div class="col-md-4">
                                <p><span class="glyphicon glyphicon-user"></span> ${ad.owner.firstname} ${ad.owner.lastname}</p>
                                <p><span class="glyphicon glyphicon-envelope"></span> ${ad.owner.zip} ${ad.owner.town}</p>
                            </div>
                            <div class="col-md-4">
                                <p><span class="glyphicon glyphicon-earphone"></span> ${ad.owner.phonenumber}</p>
                                <p><span class="glyphicon glyphicon-calendar"></span> ${ad.printCreatedTimestamp()}</p>
                            </div>
                            <div class="col-md-4">
                                <div class="input-group">
                                    <span class="input-group-addon">Preis / &euro;</span>
                                    <form:input path="price" placeholder="Preis" cssClass="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <img class="full-image" alt="image" src="http://images.prd.mris.com/image/V2/1/Yu59d899Ocpyr_RnF0-8qNJX1oYibjwp9TiLy-bZvU9vRJ2iC1zSQgFwW-fTCs6tVkKrj99s7FFm5Ygwl88xIA.jpg"></a>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <h3>Beschreibung</h3>
                                <form:textarea path="description" placeholder="Beschreibung" rows="20" cssClass="form-control" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6"></div>
                            <div class="col-md-6">
                                <div class="pull-right btn-group">
                                    <form:button class="btn btn-primary">
                                        <span class="glyphicon glyphicon-check"></span> Anzeige aufgeben
                                    </form:button>
                                    <form:button type="button" class="btn btn-danger" onClick="location.href='/ads'">
                                        <span class="glyphicon glyphicon-arrow-left"></span> Verwerfen
                                    </form:button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</form:form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="/resources/js/bootstrap.min.js" type="application/javascript"></script>
</body>
</html>