<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/profile" var="profileUrl"/>
<spring:url value="/ad/create" var="adCreateUrl"/>
<spring:url value="/ad/import" var="adImportUrl"/>
<spring:url value="/logout" var="logoutUrl"/>
<sec:authentication var="principal" property="principal"/>
<spring:url value="/ads/{username}" var="myAdsUrl"><spring:param name="username" value="${principal.username}"/></spring:url>

    <li>
        <a href="${profileUrl}">
            <span class="glyphicon glyphicon-user"></span>
            ${principal.firstname} ${principal.lastname}
        </a>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="header.user.action"/><span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
            <li><a href="${adCreateUrl}"><spring:message code="header.user.ad.place"/></a></li>
            <li><a href="${adImportUrl}"><spring:message code="header.user.ad.import"/></a></li>
            <li><a href="${myAdsUrl}"><spring:message code="header.user.ad.my"/></a></li>
            <li class="divider"></li>
            <li><a href="${profileUrl}"><spring:message code="header.user.profile.edit"/></a></li>
        </ul>
    </li>
    <li><a href="${logoutUrl}"><spring:message code="header.user.logout"/></a></li>