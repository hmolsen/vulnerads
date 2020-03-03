<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <li>
        <a href="/profile">
            <span class="glyphicon glyphicon-user"></span>
            <sec:authentication var="principal" property="principal"/>
            ${principal.firstname} ${principal.lastname} (Admin)
        </a>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="header.admin.action"/><span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
            <li><a href="/ad/create"><spring:message code="header.admin.ad.place"/></a></li>
            <li><a href="/ad/import"><spring:message code="header.admin.ad.import"/></a></li>
            <li><a href="/ads/${principal.username}"><spring:message code="header.admin.ad.my"/></a></li>
            <li class="divider"></li>
            <li><a href="/profile"><spring:message code="header.admin.profile.edit"/></a></li>
            <li class="divider"></li>
            <li><a href="/admin/users/list"><spring:message code="header.admin.user.edit"/></li>
            <li><a href="/admin/defaultphoto"><spring:message code="header.admin.picture.edit"/></a></li>
        </ul>
    </li>
    <li><a href="/logout"><spring:message code="header.admin.logout"/></a></li>
