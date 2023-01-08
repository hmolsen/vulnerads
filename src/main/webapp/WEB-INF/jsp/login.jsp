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
    <div class="alert alert-danger" role="alert"><spring:message code="login.failedloginattempt"/></div>
</c:if>
<div class="row no-margin">
    <div class="col-md-5"></div>
    <div class="col-md-2">
        <h1><spring:message code="login.title"/></h1>

        <form name='loginForm' action="<c:url value="login"/>" method='POST'>

            <div class="form-group">
                <spring:message code="login.username" var="usrname"/>
                <input type='text' class="form-control" name='username' placeholder="${usrname}">
            </div>
            <div class="form-group">
                <spring:message code="login.password" var="password"/>
                <input type='password' class="form-control" name='password' placeholder="${password}"/>
            </div>
            <!-- div class="form-group">
                <input type='text' class="form-control" name='tfakey' placeholder="2FA Code"/>
            </div -->
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input name="submit" class="btn btn-primary" type="submit" value="<spring:message code="login.title"/>"/>
        </form>
    </div>
    <div class="col-md-5"></div>
</div>
<jsp:include page="modules/scripts.jsp"/>
</body>
</html>