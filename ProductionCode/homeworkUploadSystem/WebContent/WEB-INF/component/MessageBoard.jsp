<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



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
  <link rel ="stylesheet" href="style/ReplyTextArea.css"></link>
  <link rel="stylesheet" href="style/DownBackgroud.css">
  <script type="text/javascript" src="javaScript/limitStudent.js"></script>

</head>
<body>
<script>

function reply(element, masterTime)
{
	var oldhtml = element.innerHTML;
	var newobj = document.createElement('textarea');
	var newdata = '';
	var newSubmitButton = document.createElement('button');
	var text = document.createTextNode("submit"); 

	//建立新的input元素
	newobj.value = newdata;
	newobj.rows = 10;
	newobj.cols = 100;
	
	newSubmitButton.appendChild(text); 
	
	//為新增元素新增型別,按下enter送出execute function
	newobj.addEventListener("keyup",function(event){
		if(event.keyCode  === 13){
			element.innerHTML = this.value;
			// most
			newdata = element.innerHTML.replace(/<br>/g,"\n");
			replyAJ(masterTime, newdata);
		}
	});
	
	newSubmitButton.addEventListener("click",function(event){
			element.innerHTML = newobj.value;
			// most
			newdata = element.innerHTML.replace(/<br>/g,"\n");
			replyAJ(masterTime, newdata);
			this.onclick = null;
	});
	
	
	//most
	element.innerHTML = '';
	element.appendChild(newobj);
	element.appendChild(newSubmitButton);
	newobj.focus();
}

function updateInput(element, testID)                         //modify the data input of the weekly question
{
	var oldhtml = element.innerHTML;
	var newobj = document.createElement('textarea');
	var newdata = oldhtml.replace(/<br>/g,"\n");
	var msg = "是否要重新編譯所有程式?"; 

	//建立新的input元素
	newobj.value = newdata
	//為新增元素新增型別
	newobj.onblur = function(){
		element.innerHTML = this.value ? this.value : oldhtml;
		newdata = element.innerHTML.replace(/<br>/g,"\n");

		updateInputData(testID, newdata, recompile);
	//當觸發時判斷新增元素值是否為空，為空則不修改，並返回原有值 
	}
	element.innerHTML = '';
	element.appendChild(newobj);
	newobj.focus();
}
</script>


<div class="ui container">
  <div class="ui message brown " style="margin:50px 0px 15px 0px">
	<div class="ui comments">
	  <h3 class="ui dividing header">Comments</h3>
	  
	  <c:forEach var="i" begin = "0" end = "${fn:length(Articles)}">
	 	<c:if test="${Articles[i].isMaster eq 0 }">
		  <div class="comment">
		    <a class="avatar">
		      <c:if test="${teacherID eq Articles[i].name}">
		     	 <i class="fas fa-chalkboard-teacher" style="font-size:25px;"></i>
		      </c:if>
		      <c:if test="${teacherID ne Articles[i].name}">
		     	 <i class="fas fa-user-graduate" style="font-size:25px;"></i>
		      </c:if>
		    </a>
		    <div class="content">
		      <a class="author">${Articles[i].name }</a>
		      <div class="metadata">
		        <span class="date">${Articles[i].time }</span>
		      </div>
		      <div class="text">
		        <p style="font-family: 微軟正黑體; font-size:medium ;word-wrap:break-word; word-break:normal;">${Articles[i].title }</p>
		        <p style="font-family: 微軟正黑體; font-size:medium ;word-wrap:break-word; word-break:normal;">${Articles[i].body }</p>
		      </div>
		      <div class="actions">
		        <a class="reply" OnClick="reply(this, '${Articles[i].time }');this.onclick=null;">Reply</a>
		        <c:if test="${isTeacher}">
		     	  <a class="reply" href=# OnMouseDown="checkDel('deleteArticle?time=${Articles[i].time }')">Delete</a>
		    	</c:if>
		      </div>
		    </div>
		    
		    <c:forEach var="j" begin = "0" end = "${fn:length(Articles)}">
		      <c:if test="${Articles[i].time eq Articles[j].title}">
			    <div class="comments">
			      <div class="comment">
			        <a class="avatar">
			          <i class="fas fa-user-graduate" style="font-size:25px;"></i>
			        </a>
			        <div class="content">
			          <a class="author">${Articles[j].name }</a>
			          <div class="metadata">
			            <span class="date">${Articles[j].time }</span>
			          </div>
			          <div class="text">
		      			<p style="font-family: 微軟正黑體; font-size:medium ;word-wrap:break-word; word-break:normal;">${Articles[j].body }</p>
			          </div>
			          <div class="actions">
		    		     <c:if test="${isTeacher}">
		     	 		   <a class="reply" href=# OnMouseDown="checkDel('deleteArticle?time=${Articles[j].time }')">Delete</a>
		    		     </c:if>
		              </div>
			        </div>
			      </div>
			    </div>   
			  </c:if>
		    </c:forEach> 
		    <hr style="border: 0; height: 0; box-shadow: 0 0 5px 1px brown;">
		  </div>
		</c:if>
	  </c:forEach>
	  
	</div>
	  <form class="ui reply form" ACTION=AddMsgBoard METHOD=post name = form1 > 
	    <div class="field">
	      <!--  主    題：<INPUT TYPE=text NAME=title SIZE=40> <br>-->
	      內    容：<textarea NAME=body ROWS=6 COLS=40></textarea>
	    </div>
	    <input class="ui blue submit icon button" type="submit" value="comment" onClick="return checkString(form1.body.value);">
	  </form>
  </div>
</div>


<script type="text/javascript">
function replyAJ(masterTime, data){
	var theUrl = 'MessageAjax?case=replySomeone';
$.ajax({
	url: theUrl,
	type: 'POST',
	data: {
		masterTime:masterTime,
		replyContent:data
	},
	success: function(data, textStatus, jqXHR)
	{
		window.location.reload();
	},
	error: function(jqXHR, textStatus, errorThrown)
	{
		console.log('ERRORS: ' + textStatus +"|"+ errorThrown);
	}
});
}

function updateInputData(testID, data, recompile){
	var theUrl = 'TestDataAjax?m=updateInputData';
$.ajax({
	url: theUrl,
	type: 'POST',
	data: {
		testID:testID,
		title:"<%=1%>",
		input_data:data,
		recompile:recompile
	},
	success: function(data, textStatus, jqXHR)
	{
		window.location.reload();
	},
	error: function(jqXHR, textStatus, errorThrown)
	{
		console.log('ERRORS: ' + textStatus +"|"+ errorThrown);
	}
});
}
</script>
</body>
</html>