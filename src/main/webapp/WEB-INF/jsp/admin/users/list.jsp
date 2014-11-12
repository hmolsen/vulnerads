<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<jsp:include page="../../modules/head.jsp"/>
<body class="admin">
<jsp:include page="../../modules/header.jsp"/>
<div class="container-fluid" id="body-container">
    <div class="container container-pad" id="ad-listing">
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <h2>Benutzerliste</h2>
                <div class="brdr bgc-fff pad-10 box-shad btm-mrg-20">
                    <div class="table-responsive">
                        <table id="mytable" class="table table-bordred table-striped">
                            <thead>
                            <th>Benutzername</th>
                            <th>Vorname</th>
                            <th>Nachname</th>
                            <th>PLZ</th>
                            <th>Ort</th>
                            <th>Telefon</th>
                            <th>Edit</th>
                            <th>Delete</th>
                            </thead>
                            <tbody>

                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td><b>${user.username}</b></td>
                                    <td>${user.firstname}</td>
                                    <td>${user.lastname}</td>
                                    <td>${user.zip}</td>
                                    <td>${user.town}</td>
                                    <td>${user.phonenumber}</td>
                                    <td><p><button class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" data-target="#edit" data-placement="top" rel="tooltip"><span class="glyphicon glyphicon-pencil"></span></button></p></td>
                                    <td><p><button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" data-placement="top" rel="tooltip"><span class="glyphicon glyphicon-trash"></span></button></p></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>

                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title custom_align" id="Heading">Benutzer löschen</h4>
                        </div>
                        <div class="modal-body">

                            <div class="alert alert-warning"><span class="glyphicon glyphicon-warning-sign"></span> Möchten Sie den Benutzer wirklich löschen?</div>

                        </div>
                        <div class="modal-footer ">
                            <button type="button" class="btn btn-danger" ><span class="glyphicon glyphicon-ok-sign"></span> Löschen</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Behalten</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../modules/scripts.jsp"/>
</body>
</html>