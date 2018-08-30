<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<ul class="nav navbar-nav navbar-right">
    <li>
        <a href="/profile">
            <span class="glyphicon glyphicon-user"></span>
            <sec:authentication var="principal" property="principal"/>
            ${principal.firstname} ${principal.lastname}
        </a>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Aktionen<span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
            <li><a href="/ad/create">Anzeige aufgeben</a></li>
            <li><a href="/ad/import">Anzeige importieren</a></li>
            <li><a href="/ads/${principal.username}">Meine Anzeigen</a></li>
            <li class="divider"></li>
            <li><a href="/profile">Profil bearbeiten</a></li>
        </ul>
    </li>
    <li><a href="/logout">Abmelden</a></li>
</ul>