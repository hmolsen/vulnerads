<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="encode" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>

<sec:authentication var="username" property="principal.username"/>
<c:set var="isOwnAd" value="${ad.owner.username eq username}" />
<c:choose>
    <c:when test="${isOwnAd}">
        <c:set var="adBackgroundColorClass" value="own-ad" />
    </c:when>
    <c:otherwise>
        <c:set var="adBackgroundColorClass" value="bgc-fff" />
    </c:otherwise>
</c:choose>
<spring:url value="/ad/{id}/edit" var="editUrl"><spring:param name="id" value="${ad.id}"/></spring:url>
<spring:url value="/ad/{id}/delete" var="deleteUrl"><spring:param name="id" value="${ad.id}"/></spring:url>
<spring:url value="/ad/{id}/translate" var="translateUrl"><spring:param name="id" value="${ad.id}"/></spring:url>
<spring:url value="/photo" var="photoUrl"><spring:param name="fn" value="${ad.id}/${ad.photofilename}"/></spring:url>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="modules/head.jsp"/>
<body class="user">
<jsp:include page="modules/header.jsp"/>
<div class="container-fluid" id="body-container">
<div class="container container-pad" id="ad-listing">

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <c:if test="${isOwnAd}">
                <div class="row">
                    <div class="col-md-12">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default" onClick="location.href='${editUrl}'">
                                <span class="glyphicon glyphicon-pencil"></span> <spring:message code="ad.detail.edit"/>
                            </button>
                            <button type="button" class="btn btn-default" onClick="location.href='${deleteUrl}'">
                                <span class="glyphicon glyphicon-trash"></span> <spring:message code="ad.detail.delete"/>
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
            <h2 id="ad-title">${ad.title}</h2>

            <div class="brdr ${adBackgroundColorClass} pad-10 box-shad btm-mrg-20">

            <div class="row">
                    <div class="col-md-4">
                        <p>
                            <span class="glyphicon glyphicon-user"></span> ${ad.owner.firstname} ${ad.owner.lastname}
                        </p>

                        <p><span class="glyphicon glyphicon-envelope"></span> ${ad.owner.zip} <c:out
                                value="${ad.owner.town}"/></p>
                    </div>
                    <div class="col-md-6">
                        <p><span class="glyphicon glyphicon-earphone"></span> ${ad.owner.phonenumber}</p>
                        <p>
                            <span class="glyphicon glyphicon-calendar"></span>
                            <c:if test="${ad.isFromToday()}">
                                <spring:message code="ad.today"/>,
                            </c:if>
                            <c:if test="${ad.isFromYesterday()}">
                                <spring:message code="ad.yesterday"/>,
                            </c:if>
                            ${ad.printCreatedTimestamp()}
                        </p>
                    </div>
                    <div class="col-md-2">
                        <h3 class="pull-right">${ad.price},- &euro;</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <img class="full-image" alt="image" src="${photoUrl}">
                     </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h3 class="translaite-heading">
                            <span><spring:message code="ad.detail.description"/></span>
                            <c:if test="${pageContext.response.locale.language eq 'en'}">
                                <button type="button" class="btn btn-translaite" id="translate-button">
                                    <svg class="translaite-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                        <path d="M12 2l1.85 5.15L19 9l-5.15 1.85L12 16l-1.85-5.15L5 9l5.15-1.85z"/>
                                        <path d="M18.5 13.5l.85 2.15L21.5 16.5l-2.15.85L18.5 19.5l-.85-2.15L15.5 16.5l2.15-.85z"/>
                                        <path d="M6 15l.7 1.8L8.5 17.5l-1.8.7L6 20l-.7-1.8L3.5 17.5l1.8-.7z"/>
                                    </svg><spring:message code="ad.detail.translate"/>
                                </button>
                            </c:if>
                        </h3>
                        <p id="ad-description">${ad.description}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
<jsp:include page="modules/scripts.jsp"/>
<c:if test="${pageContext.response.locale.language eq 'en'}">
<script type="application/javascript">
    $(function () {
        $("#translate-button").on("click", function () {
            var button = $(this);
            button.prop("disabled", true);
            $.post("${translateUrl}", function (data) {
                // The model answer is inserted as raw HTML on purpose: a successful
                // prompt injection reaches the DOM from here.
                $("#ad-title").html(data.title);
                $("#ad-description").html(data.description);
            }).fail(function (xhr) {
                alert("Translation failed (" + xhr.status + "): " + xhr.responseText);
            }).always(function () {
                button.prop("disabled", false);
            });
        });
    });
</script>
</c:if>
</body>
</html>