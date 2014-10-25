<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title><spring:message code="index.title"/></title>
  <link href="resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="col-md-2"></div>
  <div class="col-md-8">
    <div class="input-group">
      <input type="text" class="form-control">
      <span class="input-group-btn">
        <button class="btn btn-default" type="button">Search</button>
      </span>
    </div>
  </div>
  <div class="col-md-2"></div>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="resources/js/bootstrap.min.js" type="application/javascript"></script>
</body>
</html>