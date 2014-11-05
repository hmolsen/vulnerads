<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container-fluid" id="body-container">
<div class="container container-pad" id="ad-listing">

<div class="row">
    <div class="col-md-8 col-md-offset-2">
        <c:choose>
            <c:when test="${not empty s}">
                <h1>Suchergebnisse</h1>

                <p>Suche nach: ${s}</p>
            </c:when>
            <c:otherwise>
                <h1>Neueste Anzeigen</h1>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="row">
<div class="col-sm-8 col-sm-offset-2">
    <c:forEach var="ad" items="${latestAds}">

        <c:set var="adBackgroundColorClass" value="bgc-fff" />

        <sec:authorize access="isAuthenticated()">
            <sec:authentication var="username" property="principal.username"/>
            <c:if test="${ad.owner.username eq username}">
                <c:set var="adBackgroundColorClass" value="own-ad" />
            </c:if>
        </sec:authorize>

        <div class="brdr ${adBackgroundColorClass} pad-10 box-shad btm-mrg-20 ad-listing">
            <div class="media">
                <a class="pull-left" href="/ad/${ad.id}" target="_parent">
                    <img alt="image" class="img-responsive"
                         src="http://images.prd.mris.com/image/V2/1/Yu59d899Ocpyr_RnF0-8qNJX1oYibjwp9TiLy-bZvU9vRJ2iC1zSQgFwW-fTCs6tVkKrj99s7FFm5Ygwl88xIA.jpg"></a>

                <div class="clearfix visible-sm"></div>

                <div class="media-body fnt-smaller">
                    <a href="/ad/${ad.id}" target="_parent"></a>

                    <h4 class="media-heading">
                        <a href="/ad/${ad.id}" target="_parent">${ad.title}
                            <small class="pull-right">${ad.printCreatedTimestamp()}</small>
                        </a>
                    </h4>

                    <ul class="list-inline mrg-0 btm-mrg-10 clr-535353">
                        <li><b>${ad.price},- &euro;</b></li>

                        <li style="list-style: none">|</li>

                        <c:choose>
                            <c:when test="${(not empty ad.owner.firstname) and (not empty ad.owner.lastname)}">
                                <li>${ad.owner.firstname} ${ad.owner.lastname}</li>
                            </c:when>
                            <c:otherwise>
                                <li>${ad.owner.username}</li>
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${(not empty ad.owner.zip) and (not empty ad.owner.town)}">
                            <li style="list-style: none">|</li>
                            <li>${ad.owner.zip} ${ad.owner.town}</li>
                        </c:if>
                    </ul>

                    <p class="hidden-xs">${ad.getShortDescription()}</p>
                    <a href="/ad/${ad.id}"><span class="fnt-smaller fnt-lighter fnt-arial">[more...]</span></a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</div>

</div>
</div>