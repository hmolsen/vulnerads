<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid" id="body-container">
<div class="container container-pad" id="ad-listing">

<div class="row">
    <div class="col-md-8 col-md-offset-2">
        <c:choose>
            <c:when test="${not empty s}">
                <h1>Search Results</h1>

                <p>You searched for: ${s}</p>
            </c:when>
            <c:otherwise>
                <h1>Latest Ads</h1>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="row">
<div class="col-sm-8 col-sm-offset-2">
    <c:forEach var="ad" items="${latestAds}">
        <div class="brdr bgc-fff pad-10 box-shad btm-mrg-20 ad-listing">
            <div class="media">
                <a class="pull-left" href="#" target="_parent">
                    <img alt="image" class="img-responsive"
                         src="http://images.prd.mris.com/image/V2/1/Yu59d899Ocpyr_RnF0-8qNJX1oYibjwp9TiLy-bZvU9vRJ2iC1zSQgFwW-fTCs6tVkKrj99s7FFm5Ygwl88xIA.jpg"></a>

                <div class="clearfix visible-sm"></div>

                <div class="media-body fnt-smaller">
                    <a href="#" target="_parent"></a>

                    <h4 class="media-heading">
                        <a href="#" target="_parent">${ad.title}
                            <small class="pull-right">Today</small>
                        </a>
                    </h4>

                    <ul class="list-inline mrg-0 btm-mrg-10 clr-535353">
                        <li><b>${ad.price},- &euro;</b></li>

                        <li style="list-style: none">|</li>

                        <li>${ad.owner.firstname} ${ad.owner.lastname}</li>

                        <li style="list-style: none">|</li>

                        <li>23568 L&uuml;beck</li>
                    </ul>

                    <p class="hidden-xs">${ad.getShortDescription()}</p>
                    <span class="fnt-smaller fnt-lighter fnt-arial">[more...]</span>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</div>

</div>
</div>