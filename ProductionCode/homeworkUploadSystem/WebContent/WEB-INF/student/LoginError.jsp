<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Online Exam</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	
	<link rel="stylesheet" href="style/LoginStyle.css">
	<script src="https://kit.fontawesome.com/de6f9b3aa4.js"></script>
</head>
<body>
	<div class="login-box">
		<div class="card text-center">
    			<%if(request.getAttribute("status").toString().equals("2")){%>
    				<div class="card-header">
    					Error
  					</div>
    				<div class="card-body">
    					<h4 class="card-title">${message }</h4>
    				<div class="container col-6">
    			 	   <a href="Login" class="btn btn-primary">
    			 	     <span style="font-family: 標楷體; font-size:large ;">重新登入</span>
    			 	   </a>
    			    </div>
    			<% }else {%>
    				<div class="card-header" style="background:red;color:white;">
    					Error
  					</div>
    			   	<div class="card-body" style="color:red">
    					<h4 class="card-title">${message }</h4>
    				<!-- <div class="container col-6">
    				  	<i class="fas fa-exclamation-triangle"></i>
    			  	</div> -->
    			<%} %>
    			</div>
  			</div>
		</div>
	</div>
</body>
</html>