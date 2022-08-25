<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Online Exam</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	
	<link rel="stylesheet" href="fontawesom/css/all.css"></link>
	<link rel="stylesheet" href="style/odometer-theme-car.css">
	<link rel="stylesheet" href="style/LoginStyle.css">
	<script type="text/javascript" src="javaScript/limitStudent.js"></script>

</head>
<body>
	<div class="login-box">
	<form action = Login method=POST name=FORM1>
			<h1>歡迎光臨線上課程系統 </h1>
			<p></p><p></p>
			
			<div class="textbox" style="text-align:center;">
				<span style="font-family: 標楷體; font-size: x-large ;font-weight:bold;">你是第</span>
				<div id="odometer" class="odometer" style="font-size:20px;">00000</div>
				<span style="font-family: 標楷體; font-size: x-large ;font-weight:bold;">個上線者~</span>
				<script type="text/javascript" src="javaScript/odometer.min.js"></script>
				<script type="text/javascript">
					setTimeout(function(){
						odometer.innerHTML = ${man };
					});
				</script>
				<br><br>
				<i class="fas fa-users"></i>
				<div class="container col-10">
					<input type="text" placeholder="Username" name="name">
				</div>
			</div>
			
			<div class="textbox">
				<i class="fas fa-lock"></i>								
				<div class="container col-10">
					<input type="password" placeholder="Password" name="passwd">
				</div>
			</div>

			<div class="container col-11">
				<div class="input-group mb-3" style="width:100%;">
					<span style="font-family:Lucida Calligraphy; font-size: large ; width:45%" >select courses : &ensp; </span>
					<select name=rdoCourse class="custom-select" id="inputGroupSelect01" style="background-color:rgba(213,170,123,0.5)">
						<c:forEach var="i" begin = "1" end = "${fn:length(optName)}">
							<option value = ${i }> ${optName[i-1]} </option>
						</c:forEach>
					</select>
					<div class ="container" style="width:80%;text-align:center;" >
						<input class="btn" type="submit" onClick="return checkString(FORM1.name.value, FORM1.passwd.value);" value="Sign in">
					</div>
						
				</div>
			</div>
		
	</form>
	</div>
</body>
</html>