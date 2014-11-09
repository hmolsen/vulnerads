<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#searchbar">
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
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <p class="navbar-text">
                        <span class="glyphicon glyphicon-user"></span>
                        <sec:authentication var="principal" property="principal"/>
                        ${principal.firstname} ${principal.lastname}
                    </p>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Aktionen<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="/ad/create">Anzeige aufgeben</a></li>
                        <li><a href="/ads/${principal.username}">Meine Anzeigen</a></li>
                        <li class="divider"></li>
                        <li><a href="/profile">Profil bearbeiten</a></li>
                    </ul>
                </li>
                <li><a href="/logout">Abmelden</a></li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>
<div class="row">
    <div class="header-padder"></div>
</div>