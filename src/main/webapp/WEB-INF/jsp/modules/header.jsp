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

        <sec:authorize access="hasRole('ADMIN')">
                <jsp:include page="header_admin.jsp"/>
            </sec:authorize>
            <sec:authorize access="hasRole('USER')">
                <jsp:include page="header_user.jsp"/>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <jsp:include page="header_anonymous.jsp"/>
            </sec:authorize>

            <form method="get" action="/ads" class="navbar-form" role="search" accept-charset="ISO-8859-1">
                <div class="form-group" style="display:inline;">
                    <div class="input-group" style="display:table;">
                        <input class="form-control" name="search" placeholder="Suche..." autocomplete="off"
                               autofocus="autofocus" type="text">
                        <span class="input-group-btn" id="search_button" style="width:1%;">
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