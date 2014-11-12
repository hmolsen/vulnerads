<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication var="username" property="principal.username"/>
<c:set var="isOwnAd" value="${ad.owner.username eq username}" />
<c:choose>
    <c:when test="${isOwnAd}">
        <c:set var="adBackgroundColorClass" value="own-ad" />
    </c:when>
    <c:otherwise>
        <c:set var="adBackgroundColorClass" value="bgc-fff" />
    </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html lang="en">
<head>
    <title><spring:message code="index.title"/></title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/vulnerapp.css" rel="stylesheet">
</head>
<body>
<jsp:include page="modules/header.jsp"/>
<div class="container-fluid" id="body-container">
<div class="container container-pad" id="ad-listing">

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2>${ad.title}</h2>
            <div class="brdr ${adBackgroundColorClass} pad-10 box-shad btm-mrg-20">
                <c:if test="${isOwnAd}">
                    <div class="row">
                        <div class="col-md-6"></div>
                        <div class="col-md-6">
                            <div class="pull-right btn-group">
                                <button type="button" class="btn btn-default" onClick="location.href='/ad/${ad.id}/edit'">
                                    <span class="glyphicon glyphicon-pencil"></span> Bearbeiten
                                </button>
                                <button type="button" class="btn btn-default" onClick="location.href='/ad/${ad.id}/delete'">
                                    <span class="glyphicon glyphicon-trash"></span> L&ouml;schen
                                </button>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="row">
                    <div class="col-md-4">
                        <p><span class="glyphicon glyphicon-user"></span> ${ad.owner.firstname} ${ad.owner.lastname}</p>
                        <p><span class="glyphicon glyphicon-envelope"></span> ${ad.owner.zip} ${ad.owner.town}</p>
                    </div>
                    <div class="col-md-6">
                        <p><span class="glyphicon glyphicon-earphone"></span> ${ad.owner.phonenumber}</p>
                        <p><span class="glyphicon glyphicon-calendar"></span> ${ad.printCreatedTimestamp()}</p>
                    </div>
                    <div class="col-md-2">
                        <h3 class="pull-right">${ad.price},- &euro;</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <img class="full-image" alt="image" src="/photo?fn=${ad.photofilename}"></a>
                     </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h3>Beschreibung</h3>
                        <p>${ad.description}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="/resources/js/bootstrap.min.js" type="application/javascript"></script>
</body>
</html>