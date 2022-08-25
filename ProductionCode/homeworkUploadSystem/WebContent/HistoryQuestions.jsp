<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" import="bean.*"%>
<%@ page contentType="text/html; charset=UTF-8" import="componment.*"%>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*"%>
<% 
	Teacher tr = (Teacher) session.getAttribute("Teacher");
	if(tr == null){
		response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
	}
	
	ArrayList<?> courses = Course.getAllCourse();
	String[] optName = new String[courses.size()];
	Course c = null;
	for (int i = 0; i < courses.size(); i++) {
		c = (Course) courses.get(i);
		optName[i] = c.getId();
	}
	
	String hwId = request.getParameter("hwId");
	
	List<TestData> testdata = (List<TestData>)session.getAttribute("testData");	
	ArrayList<Question> question = (ArrayList<Question>)session.getAttribute("question");
	
	System.out.println("JSP_t:"+testdata);
	System.out.println("JSP_c:"+question);
	
	//if(session.getAttribute("content") == null){
	//	question = (ArrayList<Question>)session.getAttribute("question");
	//}
	

	
	if(question == null){
		question = new ArrayList<Question>();
		Question q = new Question();
		q.setDescription("查無資料");
		question.add(q);
	}
	if(testdata == null){
		testdata = new ArrayList<>();
		TestData t = new TestData();
		t.setInput_data("無測試資料");
		t.setTrue_result("無預期結果");
		testdata.add(t);
	}
	
	
	session.removeAttribute("testData");
	session.removeAttribute("content");
	session.removeAttribute("question");
%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>History Homework</title>
</head>
<body TEXT=#FFFFFF BGCOLOR=#000000 link=#00FFFF vlink=#CCFF33 alink=#FFCCFF>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
   <form action="HistoryQuestion" methon="post">
  <div id="block1">
    <select id = "chooseCourse" size = 1 >
      <%for(int i = 0; i < optName.length; i++){ %>
	    <option value = <%=optName[i]%>> <%=optName[i]%> </option>
	  <%} %>
	</select>
	<select id = "chooseMethon" size = 1 >
      <option value = "content"> content </option>
      <option value = "id"> id </option>
	</select>
	  <input type="text" id="search"></input>
	  <input type="button" value="查詢" id="sButton" onclick="searchData()"/>
	  <input type="reset" value="清除"/>
  </div>
 
  <div id="block2">
    <table style="width:100%;height:100%">
      <tr>
        <th style="width:4%;">選取</th>
        <th style="width:50%;">題目</th> 
        <th style="width:40%;">測資/結果</th>
        <th style="width:5%;">勾選</th>
      </tr>
     
    
      <%for(int i = 0; i < question.size(); i++){ %>
      <tr>
        <td>
          <input type="radio" name="testInfor" value=<%=question.get(i).getId() %>>
        </td>
        <td style="border:2px #105FC7 solid;width:60%">
          <%=question.get(i).getDescription()%>
        </td>
        <td style="height:30%">
          <table border="1" style="height:100%;width:100% ;table-layout:fixed;">
              <%for(int k = 0;k < testdata.size();k++){%>    
              	<%if(question.get(i).getId() == testdata.get(k).getQuestionID()){ %>
           	    <tr>
           		  <td width="100%"  style="overflow:auto;">
           		    <%=testdata.get(k).getInputData().replaceAll("\n","<br>")%>
			      </td>
		          <td width="100%"  style="overflow:auto;">
				    <%=testdata.get(k).getExpectedOutput().replaceAll("\n","<br>")%>
			      </td>
			      <td>
           		    <input type="checkbox" name=<%=testdata.get(k).getQuestionID()%> value=<%=testdata.get(k).getId()%> >
				  </td>
      	        </tr>
      	       <%} %>
      	      <%} %>
      	  </table>
        </td>
      </tr>
        <%} %>
    </table>
      <div align="center">
      	<input type="hidden" value="<%=hwId%>" name="hwId">
        <input type="submit" value="確認" style="width:100px;height:40px;">
      </div>
  </div>
  </form>
  <script>
    function searchData(){
	  var theUrl = 'HistoryQuestionAjax?do=searchData';
      $.ajax({
	    url: theUrl,
	    type: 'POST',
	    data: {
	      chooseCourse:$('#chooseCourse').val(),
	      chooseMethon:$('#chooseMethon').val(),
	      search:$('#search').val()
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

  </script>
</body>
</html>