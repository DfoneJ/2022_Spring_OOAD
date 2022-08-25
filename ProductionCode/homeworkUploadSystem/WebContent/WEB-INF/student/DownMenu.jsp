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
<p></p>
<div class="ui container">
  <div class="ui message" style="margin:50px 0px 15px 0px">
    <div class="header">
      最新公告  <i class="fas fa-bullhorn"></i>
    </div>
	<br>
    <marquee scrollamount="10">
      <span style="font-family:標楷體; font-size:medium; behavior:slide; word-wrap:break-word; word-break:normal; font-weight:bold;">Hello~ ${Name } Welcome to ${Course } classes</span>
    </marquee>
    <span style="font-family: 標楷體; font-size:medium ;word-wrap:break-word; word-break:normal;">${message }</span>
  </div>
  <%for(int i = 0; i < list.size(); i++){
	 System.out.println(list.get(i)[0]);
	  if(list.get(i)[0].equals(request.getAttribute("ID"))){%>
  		<div class="ui message" style="margin:5px 0px 15px 0px">
			<div class="ui divided selection list" >
				<a class="item" href="CheckResult.jsp?questionID=<%=list.get(i)[1]%>&studentID=<%=list.get(i)[0]%>">
    				<div class="ui red horizontal label">new</div>
    				<span style="font-family: 標楷體; font-size:medium ;word-wrap:break-word; word-break:normal; color:black">你的作業<%=list.get(i)[1]%>已更新，請點擊確認測試結果</span>
 				</a>
			</div>
 		 </div>
    <%} %>
  <%} %>
</div>
</body>
</html>