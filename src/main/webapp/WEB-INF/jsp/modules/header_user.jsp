<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <li>
        <a href="/profile">
            <span class="glyphicon glyphicon-user"></span>
            <sec:authentication var="principal" property="principal"/>
            ${principal.firstname} ${principal.lastname}
        </a>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="header.user.action"/><span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
            <li><a href="/ad/create"><spring:message code="header.user.ad.place"/></a></li>
            <li><a href="/ad/import"><spring:message code="header.user.ad.import"/></a></li>
            <li><a href="/ads/${principal.username}"><spring:message code="header.user.ad.my"/></a></li>
            <li class="divider"></li>
            <li><a href="/profile"><spring:message code="header.user.profile.edit"/></a></li>
        </ul>
    </li>
    <li><a href="/logout"><spring:message code="header.user.logout"/></a></li>
