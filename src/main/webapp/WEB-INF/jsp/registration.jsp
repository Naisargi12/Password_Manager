<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextpath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="${contextpath}/css/registration.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .fa_custom {
            color: #f3fff5;
            padding: 20px;
        }
    </style>
</head>
<body style="background-image: url(${contextpath}/images/matte-black-wallpaper-1.jpg);">
<div class="container">
<i href="#" class="fa fa-key fa_custom fa-2x"><strong>Key</strong></i>
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <springForm:form modelAttribute="user" autocomplete="off"  action="${contextpath}/registration"
                              method="post" class="form-horizontal" role="form">
                <h4 style="font-size:40px; color: #fff;" >Sign Up</h4>
                <div class="form-group">
                    <div class="col-sm-9">
                        <label class="validation-message"><springForm:errors path="name"/></label>
                        <springForm:input type="text" path="name" placeholder="Name"
                               class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9">
                        <label class="validation-message"><springForm:errors path="lastName"/></label>
                        <springForm:input type="text" path="lastName"
                               placeholder="Last Name" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-9">
                        <springForm:input type="text" path="email" placeholder="Email"
                               class="form-control"/> <label class="validation-message"><springForm:errors path="email"/></label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-9">
                        <springForm:input type="password" path="password"
                               placeholder="Password" class="form-control"/> <label class="validation-message"><springForm:errors path="password"/></label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-9">
                        <button type="submit" class="btn btn-primary btn-block">Sign Up</button>
                    </div>
                </div>
                <div class=" col-sm-9 text-center">
                    <a href="${contextpath}/" >Login</a>
                </div>
                <h2><span class="text-success">${successMessage}</span></h2>

            </springForm:form >

        </div>
    </div>
</div>

</body>
</html>