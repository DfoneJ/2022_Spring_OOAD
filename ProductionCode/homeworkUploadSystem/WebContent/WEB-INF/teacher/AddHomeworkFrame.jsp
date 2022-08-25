<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" content="width=device-width, initial-scale=1.0">
<title>Online Exam</title>
<!-- this is semantic 
  <script
  src="https://code.jquery.com/jquery-3.1.1.min.js"
  integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
  crossorigin="anonymous"></script>
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>	
-->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<link rel="stylesheet" href="style/AddHomeworkStyle.css"></link>
<link rel="stylesheet" href="style/DownBackgroud.css"></link>
<script type="text/javascript" src="javaScript/limitStudent.js"></script>	

</head>
<body>
 
 <div class="container" style="background: rgb(251,231,204);">
 <form action=AddHomework method=post name=from1>

   <h2> Enter your questions </h2>
   <div class="row100">
     <div class="col">
       <div class="inputBox">
         <input type="text" name="hwId" required="required">
         <span class="text"  onkeyup="value=value.replace(/[^\\d]/g,'')" style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">題 號</span>
         <span class="line"></span>
       </div>
     </div>
     
     <div class="col">
       <div class="inputBox">
         <input type="text" name="deadline" placeholder="             yyyy/mm/dd hh:mm" required="required"/>
         <span class="text" style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">期 限</span>
         <span class="line"></span>
       </div>
     </div>
     
     <div class="col">
       <div class="inputBox">
         <input type="text" name="weights" required="required">
         <span class="text" style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">相 似 度 (%)</span>
         <span class="line"></span>
       </div>
     </div>

   </div>
   
   <div class="row100" >
       <div class="col">
	     <div class="inputBox">
           <input type="text" name="title"  required="required" value=<c:if test="${not empty data }">${data[1] }</c:if>>
           <span class="text" style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">標 題</span>
           <span class="line"></span>
         </div>
	   </div>
   </div>
     
     <div class="row101" >
       <div class="col">
	       <div class="inputBox textarea">
	         <textarea required="required" name="content" ROWS=6 COLS=40><c:if test="${not empty data }">${data[2] }</c:if></textarea>
	         <span class="line2"></span>
	       </div>
	    </div>
     </div>
     
     <div class="row100">
         <div class="col">
           <span class="text" style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">作 業 類 型 : </span>
           <span class="line"></span>
           <select name=type>
			 <option selected value="課後作業">課後作業</option>
			 <option value="隨堂練習">隨堂練習</option> 
			 <option value="實習練習" >實習練習</option>
		   </select>
         </div>
       
         <div class="col">
           <span class="text" style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">程 式 語 言 : </span>
           <span class="line"></span>
           <select name="language">
			 <option selected value="C">C</option>
			 <option value="Python">Python</option> 
			 <option value="Java" >Java</option>
			 <option value="C#" >C#</option>
		   </select>
         </div>
         
         <div class="col">
           <span class="text" style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">出 題 方 式 : </span>
           <span class="line"></span>
           <select name="option">
			 <option selected value="New">一般出題</option>
			 <!--<option value=Addition>出題並新增至題庫</option> 
			 <option value=Modification >出題並修改題庫</option>-->
		   </select>
         </div>
     </div>
     
     <div class="row102" >
       <div class="col">
         <span class="text" style="font-family:標楷體; word-wrap:break-word; word-break:normal; font-weight:bold;">題 目 標 籤 :</span>
         <c:forEach var="i" begin ="0" end = "${fn:length(topic)-1}">
           <c:choose>
           <c:when test="${not empty data }">  <!-- if -->
             <c:choose>
             <c:when test="${fn:contains(data[3], topic[i][0])}">  <!-- if -->
               <input style="margin-left:1em" type="checkbox" name="topic" value = ${ topic[i][0]} checked="checked"> ${ topic[i][1]}
             </c:when>
             <c:otherwise>  <!-- else -->
               <input style="margin-left:1em" type="checkbox" name="topic" value = ${ topic[i][0]}> ${ topic[i][1]}
             </c:otherwise>
             </c:choose>
             
           </c:when>
           <c:otherwise>  <!-- else -->
             <input style="margin-left:1em" type="checkbox" name="topic" value = ${ topic[i][0]}> ${ topic[i][1]}
           </c:otherwise>

	           
	       </c:choose>
	     </c:forEach>
	   </div>
     </div>
     
     <div class="row100">
       <div class="col">
         <input type="submit" value="Send">
       </div>
     </div>
 <input type="hidden" name="QID" value=${data[0] }>   
 </form>
 </div>
</body>
</html>