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
  
  <link rel="stylesheet" href="fontawesom/css/all.css"></link>
  
  <link rel="stylesheet" href="style/Loading.css"></link>
  <link rel="stylesheet" href="style/DownBackgroud.css"></link>
  <script type="text/javascript" src="javaScript/limitStudent.js"></script>		
</head>
<body>
<p></p>
<div class="ui grid">
<div class="six wide column centered ">
  <div class="ui message brown" style="margin:50px 0px 15px 0px">
    <div class="header">
      <span style="font-family: 標楷體; font-size:medium ;word-wrap:break-word; word-break:normal; color:black">你的程式已上傳至作業編號: ${hwId }</span>
      <br>
      <br>
      <c:if test="${not empty FileDescription}">
        <span style="font-family: 標楷體; font-size:medium ;word-wrap:break-word; word-break:normal; color:black">你的副程式: ${FileDescription }</span>
        <br>
      </c:if>
        <span style="font-family: 標楷體; font-size:medium ;word-wrap:break-word; word-break:normal; color:black">你上傳的狀態為 : </span>
        <span style="font-family: 標楷體; font-size:medium ;word-wrap:break-word; word-break:normal; color:red">${status }</span>
    </div>
  </div>
</div>
</div>

</body>
</html>