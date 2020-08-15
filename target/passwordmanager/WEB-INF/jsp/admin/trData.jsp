<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach var="credential" items="${credencialDetails}">
  <tr>
      <td>${credential.domain}</td>
      <td>${credential.username}</td>
      <td>**********</td>
      <td>
          <button type="button" class="btn btn-default btn-sm" onclick="openpopupforedit(${credential.id},'${credential.domain}','${credential.username}',${credential.catagoryId})">
				<span class="glyphicon glyphicon-edit"></span>
					Edit
		  </button>
		  <button type="button" class="btn btn-default btn-sm" onclick="deleteCredentials(${credential.id})">
				<span class="glyphicon glyphicon-remove"></span>
					Delete
		  </button>
      </td>
  </tr>
</c:forEach>