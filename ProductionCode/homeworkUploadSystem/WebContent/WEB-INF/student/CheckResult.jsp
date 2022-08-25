<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%java.util.ArrayList<String[]> list = (java.util.ArrayList<String[]>) request.getAttribute("Note");%>
<%session.setAttribute("listOfStudentInfo", list); %>
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
  
  <link rel="stylesheet" href="fontawesom/css/all.css"></link>
  
  <link rel="stylesheet" href="style/DownBackgroud.css"></link>
  <script type="text/javascript" src="javaScript/limitStudent.js"></script>		
</head>
<body>

<div class="ui container">
<table class="ui inverted brown table" style="margin:50px 0px 15px 0px">
  <thead>
    <tr>
      <th>測試編號</th>
      <th>測試結果</th>
      <th>測試資訊</th>
    </tr>
  </thead>
  <tbody>
  <c:if test="${percent ne -1 }">
  
  <c:forEach var="i" begin = "0" end = "${fn:length(isPass)-1}">
    <tr>
      <c:choose>
      <c:when test="${isPass[i] }">
        <td class="positive">
        	<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${TestID[i] }</span>
        </td>
        <td class="positive">
        	<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${errorMessage[i] }</span>
        </td>
         <td class="positive">
         	<c:if test="${i == 0}"> 
        			<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">ExecuteResult:<br>${executeResult[i] }</span>
			</c:if> 
        </td>
      </c:when>
      <c:otherwise>
        <td class="error">
        	<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${TestID[i] }</span>
        </td>
        <td class="error">
        	<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">${errorMessage[i] }</span>
        </td>
        <td class="error">
        	<c:if test="${i == 0}"> 
        		<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">ExecuteResult:<br>${executeResult[i] }</span>
			</c:if> 
        	
        </td>
      </c:otherwise>
      </c:choose>
    </tr>
	</c:forEach>
	</c:if>
  </tbody>
</table>
<div>
	<div style="text-align:left">
	  <c:if test="${percent ne -1 }">
		<span style="text-align:right; font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:red;">你的通過率 : ${percent}%</span>
	  </c:if>
	  <c:if test="${percent eq -1 }">
		 <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">未通過任何測試</span>
	  </c:if>
	</div>
	 
	  
	 <div style="text-align:right">
	  <form id="_form" action="errorReport" method="post">
	    <input type="hidden" name="questionID" value=${questionID }>
	    <!-- 
	    <a onclick="document.getElementById('_form').submit();">
	      <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">檢視測試報告>>></span>
	    </a>
	     -->
	  </form>
	</div> 
	 
</div>

</div>
</body>
</html>