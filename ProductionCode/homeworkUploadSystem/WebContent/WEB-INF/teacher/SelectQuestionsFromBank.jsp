<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Online Exam</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>


  <link rel="stylesheet" href="style/DownBackgroud.css"></link>
  <link rel="stylesheet" href="style/SelectQuestionsFromBankStyle.css"></link>
  <script type="text/javascript" src="javaScript/limitStudent.js"></script>		
</head>
<body>

<div style="margin:50px"></div>
<div class="container table table-striped table-dark">
<form action="SelectQuestionsFromBank" method="post">
  <div class="form-row">
    <div class="form-group col-ms-2">
      <span style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">
       請選擇題目種類 : 
      </span>
    </div>
      
    <div class="form-group col">
      <c:forEach var="i" begin ="0" end = "${fn:length(topic)-1}">
	    <input style="margin-left:1em" type="checkbox" name="type" value=${ topic[i][0]} > ${ topic[i][1]}
	  </c:forEach>
    </div>  
  </div>
  
  <div class="form-row" >
    <div class="form-group col-ms-2">
      <span style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold; ">
        請選擇題目名稱 :
      </span>
    </div>
    <div class="form-group col-md-3">
    
      <select name=questions style="margin:7px">
        <option value="0" selected>Choose here</option>
        <c:forEach var="i" begin ="0" end = "${fn:length(questions)-1}">
	      <option value=${ questions[i][0]}> ${ questions[i][1]}</option>
	    </c:forEach>
	  </select>
	   
    </div>
    <div class="col-md-2">
      <input type="hidden" name="topic" value=${topic }>
      <input type="hidden" name="questions" value=${questions }>
      <input style = "margin:0px 40%" type="submit" class="btn btn-secondary" value="Search">
    </div>
  </div>
</form>
</div>


<div class="container">

	<table class="table table-striped table-dark">
	  <thead>
	    <tr>
	      <th scope="col">No.</th>
	      <th scope="col">Title</th>
	      <th scope="col">Contents</th>
	      <th scope="col">Tags</th>

	    </tr>
	  </thead>
	  <c:if test="${not empty data}">
	    <form action="SelectQuestionsFromBank" method="post">
	    <c:forEach var="i" begin ="1" end = "${fn:length(data)}">
		  <tbody>
		    <tr>
		      <th scope="row">${i } </th>
		      <td>
		        <div class="box">
		          <label>
		            <input type="radio" name="choose" value=${data[i-1][0] }>
		            <span class="already">${data[i-1][1] } </span>
		          </label>
		        </div>
		      </td>
		      <td>${data[i-1][2] }</td>
		      <td>${data[i-1][3] }</td>
		    </tr>
		  </tbody>
		</c:forEach>
		 <input type="submit" class="btn btn-secondary" value="confirmation">
		</form>
	  </c:if>
	</table>
</div>
</body>
</html>