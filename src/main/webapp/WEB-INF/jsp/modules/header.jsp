<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#searchbar">
                <span class="sr-only">Navigation wechseln</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><spring:message code="index.title"/></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="searchbar">

            <form method="get" action="/ads" class="navbar-form navbar-left" role="search" accept-charset="ISO-8859-1">
                <div class="form-group">
                    <div class="input-group">
                        <input name="s" type="text" class="form-control" autofocus="autofocus" placeholder="Suchen" value="${s}">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span></span>
                    </div>
                </div>
            </form>
            <sec:authorize access="hasRole('ADMIN')">
                <jsp:include page="header_admin.jsp"/>
            </sec:authorize>
            <sec:authorize access="hasRole('USER')">
                <jsp:include page="header_user.jsp"/>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <jsp:include page="header_anonymous.jsp"/>
            </sec:authorize>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>
<div class="row">
    <div class="header-padder"></div>
</div>