<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="nav navbar-nav navbar-right">
    <li><a href="<c:url value="register"/>"><spring:message code="header.register"/></a></li>
    <li><a href="<c:url value="login"/>"><spring:message code="header.login"/></a></li>
</ul>