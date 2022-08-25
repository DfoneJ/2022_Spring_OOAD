<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Questions</title>
 <script
  src="https://code.jquery.com/jquery-3.4.1.min.js"
  integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  crossorigin="anonymous"></script>

  
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
  
  <link rel="stylesheet" href="style/DownBackgroud.css"></link>
  <script type="text/javascript" src="javaScript/limitStudent.js"></script>	
  
   <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
   <script type="text/javascript">
   /*//duplicate pie chart
      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Task', 'Hours per Day'],
          ['其他',     ${hwmd.type0}],
          ['超出陣列邊界',      ${hwmd.type1}],
          ['使用未初始化的值',  ${hwmd.type2}],
          ['不正確的使用函式庫', ${hwmd.type3}],
          ['Segmentation Fault',    ${hwmd.type4}],
          ['程式執行時間過長',    ${hwmd.type5}],
          ['程式抄襲',    ${hwmd.type6}]
        ]);

        var options = {
          title: '第'+${hwID}+'題作業的錯誤比率',
          backgroundColor: '#f1e2d3',
          chartArea:{width:'50%',height:'75%'},
          is3D: true,
        };
		
        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
        chart.draw(data, options);
        $(window).resize(function(){
        	drawChart();
        }); 
      }
      */ 
   </script>
   
<style>
p.p {
  font-size: 50px;
 }
hr.sep-3 {
  border: none;
  height: 1px;
  background-image: linear-gradient(to right, #f0f0f0, #8f8f8f, #f0f0f0);
}
hr.sep-3::after {
  content: '🌸';
  display: inline-block;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%) rotate(60deg);
  transform-origin: 50% 50%;
  padding: 1rem;
  background-color: #f1e2d3;
}
</style>
</head>
<body>
  <div class="ui container">
    <div class="ui message brown" style="margin:50px 0px 15px 0px">
    <p> 
	  <span style="font-family:標楷體; color:black; behavior:slide; word-wrap:break-word; word-break:normal; font-weight:bold; font-size:medium;">
		${content}
	  </span>
	</p>
	
	<p class="p">
	<hr class="sep-3" />
	<p class="p">

	<div id="piechart_3d"></div>
    </div>
    	<input type="button" value="上一頁" onclick="history.go( -1 );return true;">
		<c:if test="${isTr }">
		  <input type="button" value="歷史測資" onclick="location.href='HistoryQuestions.jsp?hwId=${hwID}'"/>
		</c:if>
  </div>
  
</body>
</html>