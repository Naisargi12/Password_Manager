<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextpath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <title>Password Manager</title>
    <link rel="stylesheet" type="text/css" href="${contextpath}/css/login.css"/>
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
    <form action="${contextpath}/login" method="POST" class="form-signin">
        <h3 class="form-signin-heading" text="Welcome">Login</h3>
        <br/>

        <input type="text" id="email" name="email" placeholder="Email"
               class="form-control"/> <br/>
        <input type="password" placeholder="Password"
               id="password" name="password" class="form-control"/> <br/>

        <c:if test="${param.error}">
            <div align="center">
                <p style="font-size: 20; color: #FF1C19;">Email or Password invalid, please verify</p>
            </div>
        </c:if>
        <button class="btn btn-lg btn-primary btn-block" name="Submit" value="Login" type="Submit">Login</button>
        <div >
           <div class="pull-left">
               <a href="#" onclick="openpasswordpopup();">Forgot password</a>
           </div>
            <div class="pull-right">
                <a href="${contextpath}/registration" >Sign Up</a>
            </div>

        </div>
    </form>
</div>
<jsp:include page="popup/_forgotPassword.jsp"></jsp:include>
</body>
</html>
<script>
    function openpasswordpopup() {
        $("#savePasswordButton").hide();
        $("#newPassword").hide();
        $("#verifyButton").hide();
        $("#sendOTP").show();
        $('#forgotPassword').modal('toggle');
    }
</script>