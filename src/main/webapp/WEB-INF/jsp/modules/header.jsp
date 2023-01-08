<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container" style="width: 100%;">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#searchbar">
                <span class="sr-only">Navigation wechseln</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><spring:message code="index.title"/></a>
        </div>

        <div class="navbar-collapse collapse" id="searchbar">

            <ul class="nav navbar-nav navbar-right center-block">
                <li>
                    <c:choose>
                        <c:when test="${pageContext.response.locale eq 'en'}">
                            <a href="${requestScope['javax.servlet.forward.request_uri']}?lang=de"><span class="flag-icon flag-icon-de"> </span></a>
                        </c:when>
                        <c:when test="${pageContext.response.locale eq 'de'}">
                            <a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en"><span class="flag-icon flag-icon-us"> </span></a>
                        </c:when>
                        <c:otherwise>
                            <div class="dropdown">
                                <button class="btn navbar-btn dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                    <spring:message code="header.languagedropdown"/>
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                    <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=de"><span class="flag-icon flag-icon-de"> </span> Deutsch</a></li>
                                    <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en"><span class="flag-icon flag-icon-us"> </span> English</a></li>
                                </ul>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </li>
                <sec:authorize access="hasAuthority('ADMIN')">
                    <jsp:include page="header_admin.jsp"/>
                </sec:authorize>
                <sec:authorize access="hasAuthority('USER')">
                    <jsp:include page="header_user.jsp"/>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <jsp:include page="header_anonymous.jsp"/>
                </sec:authorize>
            </ul>

            <form method="get" action="/ads" class="navbar-form" role="search">
                <div class="form-group" id="search_form_group">
                    <div class="input-group" id="search_input_group">
                        <input class="form-control" name="s" placeholder="<spring:message code="header.searchbar.hint"/>" autocomplete="off"
                               autofocus="autofocus" type="text" value="${s}">
                        <span class="input-group-btn" id="search_button">
                            <button class="btn btn-default" type="submit">
                                <span class="glyphicon glyphicon-search"></span>
                            </button>
                        </span>
                    </div>
                </div>
            </form>
        </div>
    </div>
</nav>
<div class="row no-margin">
    <div class="header-padder"></div>
</div>