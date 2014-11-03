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
<sec:authorize access="isAuthenticated()">
    <jsp:include page="header_user.jsp"/>
</sec:authorize>
<sec:authorize access="isAnonymous()">
    <jsp:include page="header.jsp"/>
</sec:authorize>
<%--<div class="col-md-2"></div>
<div class="col-md-8">
    <c:forEach var="ad" items="${latestAds}">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">${ad.owner.firstname} ${ad.owner.lastname} - ${ad.title}</h3>
            </div>
            <div class="panel-body">
                    ${ad.description}
            </div>
        </div>
    </c:forEach>
</div>
<div class="col-md-2"></div>--%>
<jsp:include page="ad_list.jsp" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js" type="application/javascript"></script>
</body>
</html>