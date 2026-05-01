<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://localhost:8080/sanitizer"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="container-fluid" id="body-container">
<div class="container container-pad" id="ad-listing">

    <div class="row no-margin">
    <div class="col-md-8 col-md-offset-2">
        <c:choose>
            <c:when test="${not empty s}">
                <h1><spring:message code="ad.list.searchresult"/></h1>

                <p><spring:message code="ad.list.searchfor"/>${s}</p>
            </c:when>
            <c:otherwise>
                <h1><spring:message code="ad.list.latestads"/></h1>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="row">
<div class="col-sm-8 col-sm-offset-2">
    <c:forEach var="ad" items="${latestAds}">
        <spring:url value="/ad/{id}" var="adDetailUrl"><spring:param name="id" value="${ad.id}"/></spring:url>
        <spring:url value="/photo" var="adPhotoUrl"><spring:param name="fn" value="${ad.id}/${ad.photofilename}"/></spring:url>
        <spring:url value="/userdetail" var="userDetailUrl"><spring:param name="id" value="${ad.owner.id}"/></spring:url>

        <c:set var="adBackgroundColorClass" value="bgc-fff" />

        <sec:authorize access="isAuthenticated()">
            <sec:authentication var="username" property="principal.username"/>
            <c:if test="${ad.owner.username eq username}">
                <c:set var="adBackgroundColorClass" value="own-ad" />
            </c:if>
        </sec:authorize>

        <div class="brdr ${adBackgroundColorClass} pad-10 box-shad btm-mrg-20 ad-listing">
            <div class="media">
                <a class="pull-left" href="${adDetailUrl}" target="_parent">
                    <img alt="image" class="img-responsive" src="${adPhotoUrl}"></a>

                <div class="clearfix visible-sm"></div>

                <div class="media-body fnt-smaller">
                    <a href="${adDetailUrl}" target="_parent"></a>

                    <h4 class="media-heading">
                        <a href="${adDetailUrl}" target="_parent">${ad.title}
                            <small class="pull-right">
                                <c:if test="${ad.isFromToday()}">
                                    <spring:message code="ad.today"/>,
                                </c:if>
                                <c:if test="${ad.isFromYesterday()}">
                                    <spring:message code="ad.yesterday"/>,
                                </c:if>
                                ${ad.printCreatedTimestamp()}
                            </small>
                        </a>
                    </h4>

                    <ul class="list-inline mrg-0 btm-mrg-10 clr-535353">
                        <li><b>${ad.price},- &euro;</b></li>

                        <li style="list-style: none">|</li>

                        <c:choose>
                            <c:when test="${(not empty ad.owner.firstname) and (not empty ad.owner.lastname)}">
                                <li>
                                    <a href="${userDetailUrl}">${ad.owner.firstname} ${ad.owner.lastname}</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${userDetailUrl}">${ad.owner.username}</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${(not empty ad.owner.zip) and (not empty ad.owner.town)}">
                            <li style="list-style: none">|</li>
                            <li>${ad.owner.zip} ${ad.owner.town}</li>
                        </c:if>
                    </ul>

                    <p class="hidden-xs"><s:sanitize untrustedHtml="${ad.getShortDescription()}"/></p>
                    <a href="${adDetailUrl}"><span class="fnt-smaller fnt-lighter fnt-arial">
                        <spring:message code="ad.list.more"/>
                    </span></a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</div>

</div>
</div>