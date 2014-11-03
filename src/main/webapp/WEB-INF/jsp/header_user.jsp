<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><spring:message code="index.title"/></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <form method="get" action="/ads" class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input name="s" type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default"><spring:message code="submit"/></button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <p class="navbar-text">
                        <span class="glyphicon glyphicon-user"></span>
                        <sec:authentication property="principal.username"/>
                    </p>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Actions<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Create Ad</a></li>
                        <li><a href="#">Your Ads</a></li>
                        <li class="divider"></li>
                        <li><a href="profile">Edit Profile</a></li>
                    </ul>
                </li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>
<div class="row">
    <div class="header-padder"></div>
</div>