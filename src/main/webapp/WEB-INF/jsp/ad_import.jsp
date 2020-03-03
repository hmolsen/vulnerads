<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="modules/head.jsp" />
<body class="user">
	<jsp:include page="modules/header.jsp" />
	<form:form action="/ad/import" method="post"
		enctype="multipart/form-data" accept-charset="ISO-8859-1">
		<div class="container-fluid" id="body-container">
			<div class="container container-pad" id="ad-listing">
				<div class="row">
					<div class="col-md-8 col-md-offset-2">
						<div class="input-group">
							<span class="input-group-addon"><spring:message code="ad.import.importad"/></span>
						</div>
						<div class="brdr own-ad pad-10 box-shad btm-mrg-20">
							<div class="row">
								<div class="col-md-12">
									<spring:message code="ad.import.file"/><input type="file" name="adfile"><br />
								</div>
							</div>
							<c:if test="${not empty importError}">
								<div class="alert alert-danger" role="alert">${importError}</div>
							</c:if>
							<div class="row">
								<div class="col-md-6"></div>
								<div class="col-md-6">
									<div class="pull-right btn-group">
										<form:button class="btn btn-primary">
											<span class="glyphicon glyphicon-check"></span> <spring:message code="ad.import.importad"/>                                     </form:button>
										<form:button type="button" class="btn btn-danger"
											onClick="location.href='/ads'">
											<span class="glyphicon glyphicon-arrow-left"></span> <spring:message code="ad.import.discard"/>                                     </form:button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
	<jsp:include page="modules/scripts.jsp" />
</body>
</html>

