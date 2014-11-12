<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<jsp:include page="../modules/head.jsp"/>
<body>
<jsp:include page="../modules/header.jsp"/>
<div class="container-fluid" id="body-container">
    <div class="container container-pad" id="ad-listing">

        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <h2>Standardbild ändern</h2>

                <div class="row">
                    <div class="col-md-12">
                        <img class="full-image" alt="image" src="/photo?fn="></a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form:form action="/admin/defaultphoto" method="post" enctype="multipart/form-data"
                                   accept-charset="ISO-8859-1">
                            <input type="file" name="image">

                            <div class="pull-right btn-group">
                                <form:button class="btn btn-primary" type="submit">
                                    <span class="glyphicon glyphicon-check"></span> Standardbild ändern
                                </form:button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<jsp:include page="../modules/scripts.jsp"/>
</body>
</html>