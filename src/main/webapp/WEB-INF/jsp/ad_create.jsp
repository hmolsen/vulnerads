<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="modules/head.jsp"/>
<body class="user">
<jsp:include page="modules/header.jsp"/>
<form:form action="/ad/create" method="post" enctype="multipart/form-data" accept-charset="ISO-8859-1">
    <div class="container-fluid" id="body-container">
        <div class="container container-pad" id="ad-listing">

            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <div class="input-group">
                        <span class="input-group-addon"><spring:message code="ad.create.title"/></span>
                        <spring:message code="ad.create.title" var="title"/>
                        <form:input path="title" placeholder="${title}" cssClass="form-control" />
                        <form:errors path="title" cssClass="has-error" />
                    </div>
                    <div class="brdr own-ad pad-10 box-shad btm-mrg-20">
                        <div class="row">
                            <div class="col-md-4">
                                <p><span class="glyphicon glyphicon-user"></span> ${ad.owner.firstname} ${ad.owner.lastname}</p>
                                <p><span class="glyphicon glyphicon-envelope"></span> ${ad.owner.zip} ${ad.owner.town}</p>
                            </div>
                            <div class="col-md-4">
                                <p><span class="glyphicon glyphicon-earphone"></span> ${ad.owner.phonenumber}</p>
                                <p><span class="glyphicon glyphicon-calendar"></span> ${ad.printCreatedTimestamp()}</p>
                            </div>
                            <div class="col-md-4">
                                <div class="input-group">
                                    <span class="input-group-addon"><spring:message code="ad.create.price"/> / &euro;</span>
                                    <spring:message code="ad.create.price" var="price"/>
                                    <form:input path="price" placeholder="${price}" cssClass="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <spring:message code="ad.create.imageup"/><input type="file" name="adphoto"><br />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <h3><spring:message code="ad.create.description"/></h3>
                                <spring:message code="ad.create.description" var="desc"/>
                                <form:textarea path="description" placeholder="${desc}" rows="20" cssClass="form-control" />
                                <form:errors path="description" cssClass="has-error" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6"></div>
                            <div class="col-md-6">
                                <div class="pull-right btn-group">
                                    <form:button class="btn btn-primary">
                                        <span class="glyphicon glyphicon-check"></span> <spring:message code="ad.create.submitad"/>
                                    </form:button>
                                    <form:button type="button" class="btn btn-danger" onClick="location.href='/ads'">
                                        <span class="glyphicon glyphicon-arrow-left"></span> <spring:message code="ad.create.discardad"/>
                                    </form:button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</form:form>
<jsp:include page="modules/scripts.jsp"/>
</body>
</html>