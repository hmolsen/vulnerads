<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="modules/head.jsp"/>
<body>
<jsp:include page="modules/header.jsp"/>
<c:if test="${deleted}">
    <div class="alert alert-success alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Schlie&szlig;en</span></button>
        Anzeige wurde gel&ouml;scht.
    </div>
</c:if>
<jsp:include page="ad_list.jsp" />
<jsp:include page="modules/scripts.jsp"/>
</body>
</html>