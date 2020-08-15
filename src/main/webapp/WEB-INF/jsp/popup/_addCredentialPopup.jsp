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
    <title>Add Creadential</title>
    <link rel="stylesheet" type="text/css" th:href="${contextpath}/css/home.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
    <div class="container">
        <!-- The modal -->
        <div class="modal fade" id="flipFlop" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="reset();">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="modalLabel">Add Credential</h4>
                    </div>
                    <div class="modal-body">
                        <span class="form-control text-center alert alert-danger" id="errorMsg">Please enter valid details.</span>
                        <input type="hidden" id="credentialId" name="credentialId" class="form-control"/> <br/>
                        <input type="text" id="domainName" name="domainName" placeholder="Domain Name"
                               class="form-control"/> <br/>
                        <input type="text" placeholder="User Id"
                               id="userName" name="userName" class="form-control"/> <br/>
                        <input type="text" placeholder="Password"
                               id="password" name="password" class="form-control"/> <br/>
                        <select class="form-control" name="catId" id="catId">
                        <c:forEach items="${catagories}" var="category">
                        	<option value="${category.id}">${category.name}</option>
                        </c:forEach>
                        	
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="addCredentials()">Add</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="reset();">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

<!-- Initialize Bootstrap functionality -->
<script>
    $(document).ready(function(){
        $("#errorMsg").hide();
    });
    // Initialize tooltip component
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

    // Initialize popover component
    $(function () {
        $('[data-toggle="popover"]').popover()
    })

    function addCredentials() {
        var domainName = $("#domainName").val();
        var userName = $("#userName").val();
        var password = $("#password").val();
        var credentialId = $("#credentialId").val();
        var catId = $("#catId").val();
        if(domainName=='' || userName=='' || password=='' || catId==''){
            $("#errorMsg").show();
            return;
        }

        $.ajax({
            url:'${contextpath}/addCredencials',
            type:'POST',
            data: {
                credentialId:credentialId,
                domainName: domainName,
                userName: userName,
                password: password,
                catId: catId
            },
            success: function(data) {
                location.reload();
            }
        });

        $('#flipFlop').modal('toggle');
    }

    function reset(){

        $("#credentialId").val("");
        $("#domainName").val("");
        $("#userName").val("");
        $("#password").val("");
    }

    function openpopupforedit(credentialId,domain,username,catId){
        $.ajax({
            url:'${contextpath}/getDecodedCredencial',
            type:'GET',
            data: {
                credentialId:credentialId
            },
            success: function(data) {
                $("#credentialId").val(credentialId);
                $("#domainName").val(domain);
                $("#userName").val(username);
                $("#password").val(data);
                $("#catId").val(catId);
                $('#flipFlop').modal('toggle');
            }
        });

    }
</script>