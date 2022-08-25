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
	
	<link rel="stylesheet" href="style/DownBackgroud.css">
	<script type="text/javascript" src="javaScript/limitStudent.js"></script>		
	<script src="https://kit.fontawesome.com/de6f9b3aa4.js"></script>
</head>
<body>
	<div class="login-box">
		<div class="card text-center">
    				<div class="card-header" style="background:#ff7a7a;color:white;">
    				  <span style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">${errorTitle}</span>
  					</div>
    			   	<div class="card-body" style="color:red">
    					<h4 class="card-title">
    					  <span style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;"> ${errorContents }</span>
					    </h4>
					    <!--  
    				<div class="container col-6">
    				  	<i class="fas fa-exclamation-triangle"></i>
    			  	</div>-->
    			</div>
  			</div>
		</div>
</body>

	<style type="text/css">
	body{}
	  .login-box{
	width: 30%;
	background: rgba(236,237,238,0.4);
	margin-bottom: 10px;
	position: absolute;
	top: 50%;
	left: 50%;
	-webkit-transform: translate(-50%, -50%);
    -moz-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    -o-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
	color: black;
}
.login-box h1{
	float: left;
	font-size: 28px;
	border-bottom: 6px solid #23cf7a;
	margin-bottom: 10px;
	padding: 15px 0;
	font-family: 標楷體;

}
.textbox{
	width: 100%;
	overflow: hidden;
	font-size: 20px;
	padding: 8px 0;
	margin: 8px 0;
	border-botttom: 1px solid #4caf50;
}
.textbox i{
	width: 60px;
	float: left;
	text-align: center;
}
.textbox input{
	border: none;
	outline: none;
	background: rgba(120,146,181,0.5);
	color: white;
	font-size: 18px;
	width: 80%;
	float: left;
	margin: 0 10px;
}
.textbox select{
	background: #0563af;
	color: rgba(120,146,181,0.5);
	padding: 10px;
	width: 5%;
	height: 40px;
	border: none;
	font-sizx: 20px;
	box-shadow: 0 5px 25px rgba(0,0,0,0.5);
	-webkit-apperance: button;
	outline: none;
}
.btn{
	width: 100%;
	background: rgba(62,81,255,0.9);
	border: 2px solid rgba(120,146,181,0.9);
	border-radius: 300px;
	color: #ffffff;
	padding: 5px;
	font-size: 20px;
	cursor: pointer;
	margin: 12px 0;
}
	</style>
</html>