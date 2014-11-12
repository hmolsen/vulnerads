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
            <li><a href="/ads/${principal.username}">Meine Anzeigen</a></li>
            <li class="divider"></li>
            <li><a href="/profile">Profil bearbeiten</a></li>
            <li class="divider"></li>
            <li><a href="/admin/users/list">Benutzer bearbeiten</a></li>
            <li><a href="/admin/ads">Anzeigen bearbeiten</a></li>
            <li><a href="/admin/defaultphoto">Standardbild bearbeiten</a></li>
        </ul>
    </li>
    <li><a href="/logout">Abmelden</a></li>
</ul>