<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<form:form action="/admin/defaultphoto" method="post" enctype="multipart/form-data" accept-charset="ISO-8859-1">
    <input type="file" name="image">
    <form:button type="submit"><span class="glyphicon glyphicon-check"></span> Default Image ändern</form:button>
</form:form>
</body>
</html>