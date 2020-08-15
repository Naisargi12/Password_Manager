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
    <title>Home</title>
    <link rel="stylesheet" type="text/css" th:href="${contextpath}/css/home.css"/>
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
    <div class="panel-group" style="margin-top:40px">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <span>${userName}</span>
                <span><a class="pull-right" style="color: #eeeeee" href="${contextpath}/logout"> Logout</a></span>
            </div>
            <div class="panel-body">
<%--                <img src="${contextpath}/images/beer.jpg" class="img-responsive center-block" width="400" height="400"--%>
<%--                     alt="Beer"/>--%>
            <c:if test="${user.secretKey!=null}">
            	<div class="pull-left">
	            	<a class="btn btn-primary" href="${contextpath}/catagoryDetail">Categories</a>
	            	<c:if test="${catagories==null}">
	            		<small style="color: red;">Please add Category</small>
	            	</c:if>
            	</div>
            	<c:if test="${catagories!=null}">
	            	<div class="pull-right">
		            	<select class="form-control" id="searchFeild" name="searchFeild" onchange="changeChange()">
		            		<option value="">--Select Category--</option>
		            		<c:forEach var="category" items="${catagories}">
		            			<option value="${category.id}">${category.name}</option>
		            		</c:forEach>
		            	</select>
	            	</div>
	            </c:if>
                
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Domain</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="tBody">
                        <jsp:include page="trData.jsp"></jsp:include>
                    </tbody>
                    <tfoot>
	                    <c:if test="${catagories!=null}">
	                    	<tr>
	                    		<th colspan="4" class="text-center">
	                   				<button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#flipFlop" onclick="$('#errorMsg').hide();">
		                            	Add Credential
			                        </button>
	                    		</th>
	                    	</tr>
	                    </c:if>
                    </tfoot>
                </table>
            </c:if>
            </div>
            <c:if test="${user.secretKey==null}">
                <div  class="text-center">
                    <form action="${contextpath}/addSecretKey" method="post">
                        <input type="text" id="secretKey" name="secretKey" placeholder="Secret Key of 16 digit">       <button id="secretKeySubmit" type="submit">Save</button>
                    </form>
                </div>

                <br>
                <p class="admin-message-text text-center" ><b>Please add secret key.</b></p>
            </c:if>
        </div>
    </div>
    <jsp:include page="../popup/_addCredentialPopup.jsp"></jsp:include>

</div>
</body>
</html>
<script>
    $(document).ready(function(){
        $("#secretKeySubmit").hide();
    });
    $("#secretKey").keyup(function() {
        if($(this).val().trim().length==16){
            $("#secretKeySubmit").show();
        }else{
            $("#secretKeySubmit").hide();
        }
    });
    function changeChange(){
    	var catId = $("#searchFeild").val();
    	$.ajax({
	   		 type: "POST",
	   		 url: "${contextpath}/filterCredentials",
	   		 data: {catId:catId},
	   		 success: function(data) {
	   			$("#tBody").html(data);
	   		 }
   		});
    }
    function deleteCredentials(credentialId){
    	$.ajax({
	   		 type: "POST",
	   		 url: "${contextpath}/removeCredential",
	   		 data: {credentialId:credentialId},
	   		 success: function(data) {
	   			if(data=='success'){
	   				location.reload();
	   			}
	   		 }
  		});
    }
</script>

<!--

-->