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
  <div class="ui container" style="margin:50px 0px 15px 0px">
	<a class="ui teal tag label" href="HomeworkBoard?hwType= 課後作業"> 課後作業 </a>
	
	<div class="ui teal floating labeled icon dropdown button" style="margin:0px 0px 0px 78%">
      <i class="filter icon"></i>
      <span class="text">新增作業</span>
      <div class="menu">
        <div class="header">
          <i class="tags icon teal"></i>
            新增方式  ⮯
        </div>
        <div class="item">
          <a href="AddHomework" style="font-family:標楷體; word-wrap:break-word;
           word-break:normal; font-weight:bold; color:rgba(0,0,0)"> 出新題目</a>
        </div>
        <div class="item">
          <a href="SelectQuestionsFromBank" style="font-family:標楷體; word-wrap:break-word;
           word-break:normal; font-weight:bold; color:rgba(0,0,0)"> 從題庫中選取</a>
        </div>
      </div>
    </div>
  </div>
  	

	<table class="ui selectable inverted table">
	  <thead>
	    <tr>
	      <th>編號</th>
	      <th>種類</th>
	      <th>題號</th>
	      <th>繳交期限</th>
	      <th>繳交</th>
	      <th>語言</th>
	      <th>備註</th>
	      <th>測試</th>
	      <th>測試結果</th>
	      <th>匯出</th>
	      <th>刪除</th>
	      <c:choose>
	      	  <c:when test="${IsShowActiveStateField == true}">
				<th>作業狀態</th>
	      	  </c:when>
      	   </c:choose>
	      <th class="right aligned"></th>
	    </tr>
	  </thead>
	  <tbody>
		<c:forEach var="i" begin = "1" end = "${fn:length(Homework)}">
	    <tr>
	      <!-- 編號 -->
	      <td>${i }</td>
	      <!-- 種類 -->
	      <td>
	      	<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">
				${Homework[i-1].type}
			</span>
	      </td>
	      <!-- 題號 -->
	      <td class="selectable">
	      	<a href="showHomework?hwId=${Homework[i-1].id }" style="color:rgba(159,162,255);">${Homework[i-1].id }</a>
	      </td>
		  <!-- 繳交期限 -->
		  <td>${Homework[i-1].deadline}</td>  
		  <!-- 繳交 -->
		  <c:choose>
		 	 <c:when test="${Homework[i-1].expired == true}"> 	
	      		<td>
				  <span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">
					期限已過
				  </span>
				</td>
	      	</c:when>
	     	<c:otherwise>
	      		<c:choose>
	      			<c:when test="${Lock[i-1] == true}">
	      			  <td class="selectable">
						<a href="upLoadHw?hwId=${Homework[i-1].id }" style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:rgba(54,241,54)"> 可繳交 </a>
	      			  </td>
	     			</c:when>
	     			<c:otherwise>
			 		  <td>
			 		  	<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:red">
							準備中
						</span>
			 		  </td>
					</c:otherwise>
		 		</c:choose>
		 	 </c:otherwise>
		  </c:choose>
		  <!-- 語言 -->
	      <td>
			${Homework[i-1].language }
		  </td>
		  <!-- 備註 -->
		  <td class="selectable">
	      	<a href="SetHomework?hwId=${Homework[i-1].id }" style="color:rgba(159,162,255);">編修</a>
	      </td>
		  <!-- 測試 -->
		  <td class="selectable">
	      	<a href="TestDataList.jsp?questionID=${Homework[i-1].id }" style="color:rgba(159,162,255);">測試資料</a>
	      </td>
		  <!-- 測試結果 -->
		  <td class="selectable">
	      	<a href="HW_result_report.jsp?HW_ID=${Homework[i-1].id }" style="color:rgba(159,162,255);">測試結果</a>
	      </td>
		  <!-- 匯出 -->
		  <td class="selectable">
	      	<a href="ExportStudentHW?title=${Homework[i-1].id }" style="color:rgba(159,162,255);">匯出學生繳交作業</a>
	      </td>
		  <!-- 刪除 -->
		  <td class="selectable">
	      	<a href="#" style="color:rgba(159,162,255);" onmousedown="checkDel('DelHomework?hwId=${Homework[i-1].id }')">刪除</a>
	      </td>
	      <!-- Hide --> 
	      <c:choose>
	      	<c:when test="${IsShowActiveStateField == true && ActiveStates[i-1] == true}">
	      	  <td class="selectable">
				<a href="#" onmousedown="checkDel('ActiveHomework?hwId=${Homework[i-1].id }&setActive=false')">設為隱藏</a>
	      	  </td>
	     	</c:when>
	     	<c:when test="${IsShowActiveStateField == true && ActiveStates[i-1] == false}">
			  <td class="selectable">
			    <a href="#" onmousedown="checkDel('ActiveHomework?hwId=${Homework[i-1].id }&setActive=true')">設為公開</a>
			  </td>
			</c:when>
		  </c:choose>
		  <!-- Lock --> 
		  <c:choose>
	      	<c:when test="${Lock[i-1] == true}">
	      	  <td class="right aligned selectable">
				<button style="background-color:#22CC22;border:3px orange double;color:black;" onClick="location.href='UpLoadStateConversion?hwID=${Homework[i-1].id }'">可上傳</button>
	      	  </td>
	     	</c:when>
	     	<c:otherwise>
			  <td class="right aligned selectable">
			    <button style="background-color:#CC2222;border:3px orange double;color:black;" onClick="location.href='UpLoadStateConversion?hwID=${Homework[i-1].id }'">不可上傳</button>
			  </td>
			</c:otherwise>
		  </c:choose>
	    </tr>
	    </c:forEach>
	  </tbody>
	</table>
</div>

<!-- 
<FORM ACTION=AddHomework METHOD=post name=form1> <br>
作業類型：<select name= type>
<option " + "selected" + " value=" + v[0] + " >" + v[0] + "</option>
<option value=" + v[1] + " >" + v[1] + "</option>
<option value=" + v[2] + " >" + v[2] + "</option>
</select><br>
程式語言：<select name= ltype>
<option " + "selected" + " value=" + t[0] + " >" + t[0] + "</option>
			for(int i=1;i<t.length;i++) {
				System.out.println(t[i]);
			pw.println("<option value=" + t[i] + " >" + t[i] + "</option>");
			}
</select><br>
			pw.println("相 似 度：<INPUT TYPE=text NAME=weights SIZE=20> %以上算抄襲<br>");
			pw.println("題        號：<INPUT TYPE=text NAME=hwId SIZE=40 onkeyup=\"value=value.replace(/[^\\d]/g,'')\" placeholder=\"請輸入數字\"> <br>");
			pw.println("期        限：<INPUT TYPE=text NAME=deadline SIZE=40 placeholder=\"yyyy/mm/dd hh:mm\"> <br>");	
			pw.println("內        容：<TEXTAREA NAME=content ROWS=6 COLS=40></TEXTAREA> <br>");
			pw.println(
					"<INPUT TYPE=submit onClick=\"return checkString(form1.deadline.value, form1.hwId.value); \""
				   + "VALUE=增加> <INPUT TYPE=reset VALUE=清除>");
			pw.println("</FORM>");
		}
 -->

    <script type="text/javascript">
    $('.ui.dropdown')
    .dropdown() ;
    </script>
    
</body>
</html>