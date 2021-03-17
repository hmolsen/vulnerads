<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="bodyclass" value=""/>
<sec:authorize access="isAuthenticated()">
    <c:set var="bodyclass" value="user"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<jsp:include page="modules/head.jsp"/>
<body class="${bodyclass}">
<jsp:include page="modules/header.jsp"/>
<center>
    <h1><spring:message code="error.error"></spring:message> ${statusCode}</h1>
    <h2><spring:message code="error.generalerrormessage"></spring:message></h2>
</center>
<jsp:include page="modules/scripts.jsp"/>
</body>
</html>