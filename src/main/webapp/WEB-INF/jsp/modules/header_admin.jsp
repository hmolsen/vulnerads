<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/profile" var="profileUrl"/>
<spring:url value="/ad/create" var="adCreateUrl"/>
<spring:url value="/ad/import" var="adImportUrl"/>
<spring:url value="/admin/users/list" var="userListUrl"/>
<spring:url value="/admin/defaultphoto" var="defaultPhotoUrl"/>
<spring:url value="/logout" var="logoutUrl"/>
<sec:authentication var="principal" property="principal"/>
<spring:url value="/ads/{username}" var="myAdsUrl"><spring:param name="username" value="${principal.username}"/></spring:url>

    <li>
        <a href="${profileUrl}">
            <span class="glyphicon glyphicon-user"></span>
            ${principal.firstname} ${principal.lastname} (Admin)
        </a>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="header.admin.action"/><span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
            <li><a href="${adCreateUrl}"><spring:message code="header.admin.ad.place"/></a></li>
            <li><a href="${adImportUrl}"><spring:message code="header.admin.ad.import"/></a></li>
            <li><a href="${myAdsUrl}"><spring:message code="header.admin.ad.my"/></a></li>
            <li class="divider"></li>
            <li><a href="${profileUrl}"><spring:message code="header.admin.profile.edit"/></a></li>
            <li class="divider"></li>
            <li><a href="${userListUrl}"><spring:message code="header.admin.user.edit"/></a></li>
            <li><a href="${defaultPhotoUrl}"><spring:message code="header.admin.picture.edit"/></a></li>
        </ul>
    </li>
    <li><a href="${logoutUrl}"><spring:message code="header.admin.logout"/></a></li>