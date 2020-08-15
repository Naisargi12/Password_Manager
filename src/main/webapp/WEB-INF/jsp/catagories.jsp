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
    <title>Cotegory</title>
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
<input type="hidden" name="userId" id="userId" value="${userId}">
<div class="container">
 <i href="#" class="fa fa-key fa_custom fa-2x"><strong>Key</strong></i>
<div class="col-xs-12 text-center h2" style="color: #eeeeee" >
<a href="${contextpath}/admin/home" class="pull-left"><i style="font-size: 83%;" class="glyphicon glyphicon-menu-left"></i>Back</a>
Manage categories
</div>
<div class="col-xs-12" >
<table id="btnAddRow" class="table" style="color: #eeeeee" >
	<thead>
		<tr>
			<th>
				Name
			</th>
			<th>
				Action
			</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${catagories!=null}">
			<c:forEach items="${catagories}" var="category" varStatus="i">
				<tr>
					<td>
						<input type="text" name="catName${i.count}" id="catName${i.count}" class="form-control" value="${category.name}">
					</td>
					<td>
						<button type="button" class="btn btn-primary btn-sm" onclick="saveCategory(${i.count},${category.id},false)">
						  <span class="glyphicon glyphicon-ok"></span> Save 
						</button>
					</td>
				</tr>
			</c:forEach>
			
		</c:if>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="2">
				<button type="button" class="btn btn-primary btn-sm" onclick="addCategory()">
				
				  <span class="glyphicon glyphicon-plus"></span> Add 
				</button>
			</td>
		</tr>
	</tfoot>
</table>
</div>
</div>
<script type="text/javascript">
function addCategory(){
	    var rowCount = $("#btnAddRow > tbody > tr").length+1;
	    var col1 = '<td> <input type="text" name="catName'+rowCount+'" id="catName'+rowCount+'" class="form-control"> </td>';
	    var col2 = 	'<td>'
					+'<button type="button" class="btn btn-default btn-sm" onclick="saveCategory('+rowCount+',null,true)">'
					+'<span class="glyphicon glyphicon-ok"></span> Save' 
					+'</button>'
					+'</td>';
	    $('#btnAddRow tbody').append('<tr>' + col1 + col2 + '</tr>');
}
function saveCategory(index,id,flag){
	var userId = $("#userId").val();
	var catName = $("#catName"+index).val();
	var data = {name:catName,userId:userId};
	if(catName===""){
		alert('Please enter value');
		return false
	}
	$.ajax({
		 type: "POST",
		 url: "${contextpath}/saveCategory",
		 contentType: "application/json; charset=utf-8",
		 dataType: "json",
		 data: JSON.stringify(data),
		 success: function(msg) {
			location.reload();
		 }
		});
}
</script>
</body>
</html>
