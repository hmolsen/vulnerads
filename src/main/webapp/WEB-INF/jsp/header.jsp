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
                    <input name="s" type="text" class="form-control" placeholder="Search" value="${s}">
                </div>
                <button type="submit" class="btn btn-default"><spring:message code="submit"/></button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="register"><spring:message code="header.register"/></a></li>
                <li><a href="login"><spring:message code="header.login"/></a></li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>
<div class="row">
    <div class="header-padder"></div>
</div>