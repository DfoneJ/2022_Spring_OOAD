<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Online Exam</title>
 <script
  src="https://code.jquery.com/jquery-3.1.1.min.js"
  integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
  crossorigin="anonymous"></script>
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
  
  <link rel="stylesheet" href="style/DownBackgroud.css"></link>
  <script type="text/javascript" src="javaScript/limitStudent.js"></script>		
</head>
<body>
<div class="ui container">
<center>
<canvas id="myCanvas" width="1000" height="120" style="border:2px solid #000000;background:rgb(140,103,63);margin:10px 0px 15px 0px"></canvas>
</center>
<table class="ui inverted brown table" style="margin:40px 0px 15px 0px">
  <thead>
    <tr>
      <th>錯誤率</th>
      <th>程式碼</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
    <c:when test="${fn:length(degree) > 2 }">
	  <c:forEach var="i" begin = "0" end = "${fn:length(statements)-1}">
	    <tr>
	      <td>
	         <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${degree[i] } %</span>
	      </td>
	      
	      <c:choose>
	      	<c:when test="${degree[i] < 14 }">
	      	  <td style="background-color:rgb(102,180,45)">
	            <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${statements[i] }</span>
	          </td>
	      	</c:when>
	      	<c:when test="${degree[i] < 28 }">
	      	  <td style="background-color:rgb(140,190,33)">
	            <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${statements[i] }</span>
	          </td>
	      	</c:when>
	      	<c:when test="${degree[i] < 42 }">
	      	  <td style="background-color:rgb(226,222,9)">
	            <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${statements[i] }</span>
	          </td>
	      	</c:when>
	      	<c:when test="${degree[i] < 56 }">
	      	  <td style="background-color:rgb(249,215,22)">
	            <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${statements[i] }</span>
	          </td>
	      	</c:when>
	      	<c:when test="${degree[i] < 70 }">
	      	  <td style="background-color:rgb(248,173,19)">
	            <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${statements[i] }</span>
	          </td>
	      	</c:when>
	      	<c:when test="${degree[i] < 84 }">
	      	  <td style="background-color:rgb(240,123,7)">
	            <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${statements[i] }</span>
	          </td>
	      	</c:when>
	      	<c:otherwise>
	      	  <td style="background-color:rgb(232,76,25)">
	            <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${statements[i] }</span>
	          </td>
	      	</c:otherwise>
	      </c:choose>
	    </tr>
	  </c:forEach>
	</c:when>
	<c:otherwise>
	  <td>
	    <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">未達到錯誤回饋條件</span>
	  </td>
	  <td>
	    <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">必須通過全部範例測試</span>
	  </td>
	</c:otherwise>
	</c:choose>
  </tbody>
</table>
</div>
</body>
<script>
var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");
ctx.font = "25px 標楷體";
ctx.fillStyle = "white";
ctx.fillText("可疑度: 數值越大，錯誤的嫌疑就越大", 310, 40);

ctx.fillStyle = "rgb(232,76,25)";
ctx.fillRect(5, 70, 30, 30);
ctx.font = "20px 標楷體";
ctx.fillText(":極高可疑", 40, 89);

ctx.fillStyle = "rgb(240,123,7)";
ctx.fillRect(160, 70, 30, 30);
ctx.font = "20px 標楷體";
ctx.fillText(":非常可疑", 195, 89);

ctx.fillStyle = "rgb(248,173,19)";
ctx.fillRect(310, 70, 30, 30);
ctx.font = "20px 標楷體";
ctx.fillText(":可疑", 345, 89);

ctx.fillStyle = "rgb(249,215,22)";
ctx.fillRect(430, 70, 30, 30);
ctx.font = "20px 標楷體";
ctx.fillText(":普通", 465, 89);

ctx.fillStyle = "rgb(226,222,9)";
ctx.fillRect(555, 70, 30, 30);
ctx.font = "20px 標楷體";
ctx.fillText(":有點可疑", 590, 89);

ctx.fillStyle = "rgb(140,190,33)";
ctx.fillRect(710, 70, 30, 30);
ctx.font = "20px 標楷體";
ctx.fillText(":極低可疑", 745, 89);

ctx.fillStyle = "rgb(102,180,45)";
ctx.fillRect(865, 70, 30, 30);
ctx.font = "20px 標楷體";
ctx.fillText(":幾乎沒有", 900, 89);
</script>

</html>