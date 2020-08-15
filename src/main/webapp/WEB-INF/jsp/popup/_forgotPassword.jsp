<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextpath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <link rel="stylesheet" type="text/css" th:href="${contextpath}/css/home.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <!-- The modal -->
    <div class="modal fade" id="forgotPassword" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="modalLabel"></h4>
                </div>
                <div class="modal-body">
                    <span class="form-control text-center alert alert-danger" id="errorMsg">Please enter valid details.</span>
                    <input type="hidden" id="credentialId" name="credentialId" class="form-control"/> <br/>
                    <input type="Label" id="userName" name="userName" placeholder="Enter login ID."
                           class="form-control"/> <br/>
                    <input type="Label" id="otpValue" name="otpValue" placeholder="Enter OTP number."
                           class="form-control"/> <br/>
                    <input type="Label" id="newPassword" name="newPassword" placeholder="Enter New Password."
                           class="form-control"/> <br/>
                </div>
                <div class="modal-footer">
                    <button type="button" id="verifyButton" class="btn btn-secondary" onclick="verify()">Verify</button>
                    <button type="button" id="sendOTP"class="btn btn-secondary" onclick="sendOTP()">Send OTP</button>
                    <button type="button" id="savePasswordButton" class="btn btn-secondary" onclick="saveNewPassword()">Save</button>
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
        $("#otpValue").hide();
        $("#verifyButton").hide();


    });
    // Initialize tooltip component
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

    // Initialize popover component
    $(function () {
        $('[data-toggle="popover"]').popover()
    })

    function sendOTP() {
        var userName = $("#userName").val();
        if(userName==''){
            $("#errorMsg").html("Please enter valid details.");
            $("#errorMsg").show();
            return;
        }
        $("#errorMsg").hide();
        $.ajax({
            url:'${contextpath}/sendOTP',
            type:'POST',
            data: {
                userName: userName
            },
            success: function(data) {
                if(data==='fail'){
                    $("#errorMsg").html("Please enter valid details.");
                    $("#errorMsg").show();
                }else{
                    $("#otpValue").show();
                    $("#verifyButton").show();
                    $("#sendOTP").hide();

                }
            }
        });
        $('#flipFlop').modal('toggle');
    }

    function reset(){
        $("#userName").val("");
    }

    function verify(){
        var userName=$("#userName").val();
        var otpNumber=$("#otpValue").val();
        $.ajax({
            url:'${contextpath}/verify',
            type:'POST',
            data: {
                userName:userName,
                otpNumber:otpNumber
            },
            success: function(data) {
                if(data==='success'){
                    $("#newPassword").show();
                    $("#savePasswordButton").show();
                    $("#verifyButton").hide();
                }else{
                    $("#errorMsg").html(data);
                    $("#errorMsg").show();
                }
            }
        });
    }
    function saveNewPassword(){
        $.ajax({
            url:'${contextpath}/saveNewPassword',
            type:'POST',
            data: {
                userName:$("#userName").val(),
                newPassword:$("#newPassword").val()
            },
            success: function(data) {
                if(data==='success'){
                    $("#forgotPassword").modal('toggle');
                }else{
                    $("#errorMsg").html(data);
                    $("#errorMsg").show();
                }
            }
        });
    }
</script>