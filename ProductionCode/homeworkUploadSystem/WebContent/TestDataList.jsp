<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" import="bean.*"%>
<%@ page contentType="text/html; charset=UTF-8" import="componment.*"%>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*"%>
<%
	Teacher tr = (Teacher)session.getAttribute("Teacher");
	Student st = (Student)session.getAttribute("Student");
	String dbName = "";
	if(st!=null)
		dbName = st.getDbName();
	else if(tr!=null)
		dbName = tr.getDbName();		
	DBcon dbCon = new DBcon();

	int questionID = Integer.parseInt(request.getParameter("questionID"));
	String QID = request.getParameter("questionID");
	ArrayList<TestData> result = dbCon.getTestDatas(dbName,questionID);
	if(session.getAttribute("Teacher")==null)
		return;
%>
<html>
<head>
<meta content="text/html; charset=Big5">
<title>TestResult</title>
</head>
<BODY TEXT=#FFFFFF BGCOLOR=#000000 link=#00FFFF vlink=#CCFF33 alink=#FFCCFF>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">
	function updateInput(element, testID)
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
			if (confirm(msg)==true){ 
				var recompile = 1;
			}else{ 
				var recompile = 0;
			} 
			
			updateInputData(testID, newdata, recompile);
		//當觸發時判斷新增元素值是否為空，為空則不修改，並返回原有值 
		}
		element.innerHTML = '';
		element.appendChild(newobj);
		newobj.focus();
	}
	
	function updateResult(element,testID)
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
			if (confirm(msg)==true){ 
				var recompile = 1;
			}else{ 
				var recompile = 0;
			}
			
			updateTrueResult(testID, newdata, recompile);
		//當觸發時判斷新增元素值是否為空，為空則不修改，並返回原有值 
		}
		element.innerHTML = '';
		element.appendChild(newobj);
		newobj.focus();
	}
</script>


題目<%=questionID%>測試資料:
<table border="1" class="table" style="max-width:100%;">
	<tr>
		<td style ="text-align:center;">編號</td><td style="max-width:50%;">測試資料</td><td style="max-width:50%;">預估正確結果</td><td>-</td>
	</tr>
	<%
		for(int i=0;i<result.size();i++){ 
	%>
	
	<tr>
	    <%int id =result.get(i).getId();%>
	    <td style ="text-align:center;"><%=id%></td>
		<td ondblclick="updateInput(this, <%=id%>);this.onclick=null;"><%=result.get(i).getInputData().replaceAll("\n","<br>")%></td>
		<td ondblclick="updateResult(this, <%=id%>);this.onclick=null;"><%=result.get(i).getExpectedOutput().replaceAll("\n","<br>")%></td>
		
		<td>
			<button type="button" class="btn btn-primary" onClick="deleteTestData(<%=result.get(i).getId()%>);">刪除</button>
		</td>
		
	</tr>
	<% } %>
	<tr>
		<td style ="text-align:center;">-</td>
		<td><textarea style="width:100%" rows="5" id="input_data"></textarea></td>
		<td><textarea style="width:100%" rows="5" id="true_result"></textarea></td>
		<td><button type="button" class="btn btn-primary" onClick="addTestData()">新增測試資料</button></td>
	</tr>
</table>

<script>

function addTestData(){
		var theUrl = 'TestDataAjax?m=addTestData';
	$.ajax({
		url: theUrl,
		type: 'POST',
		data: {
			questionID:<%=questionID%>,
			input_data:$('#input_data').val(),
			true_result:$('#true_result').val()
		},
		success: function(data, textStatus, jqXHR)
		{
			window.location.reload();
			//bootbox.alert("1234");
		},
		error: function(jqXHR, textStatus, errorThrown)
		{
			console.log('ERRORS: ' + textStatus +"|"+ errorThrown);
		}
	});
		
}

function deleteTestData(id){
		var theUrl = 'TestDataAjax?m=deleteTestData';
	$.ajax({
		url: theUrl,
		type: 'POST',
		data: {
			testDataID:id
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
			title:"<%=QID%>",
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

function updateTrueResult(testID, data, recompile){
	var theUrl = 'TestDataAjax?m=updateTureResult';
$.ajax({
	url: theUrl,
	type: 'POST',
	data: {
		testID:testID,
		title:"<%=QID%>",
		true_result:data,
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