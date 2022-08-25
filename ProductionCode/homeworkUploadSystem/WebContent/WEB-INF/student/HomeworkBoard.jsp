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
		    crossorigin="anonymous">
		</script>
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
		<link rel="stylesheet" href="style/DownBackgroud.css"></link>
		<script type="text/javascript" src="javaScript/limitStudent.js"></script>		
	</head>

    <body>
        <div class="ui container">
            <div class="ui container" style="margin:50px 0px 15px 0px">
                <a class="ui teal tag label" href="HomeworkBoard?hwType=課後作業"> 課後作業 </a>
            </div>
			<table class="ui selectable inverted table">
			    <thead>
			    	<tr>
				        <th>編號</th>
				        <th>作業類型</th>
				        <th>題號</th>
				        <th>繳交期限</th>
				        <th>繳交</th>
				        <th>語言</th>
				        <th>作業狀態</th>
				        <th>測試結果</th>
				        <th class="right aligned"></th>
			    	</tr>
			    </thead>
			    <tbody>
					<c:forEach var="i" begin = "1" end = "${fn:length(Homework)}">
						<c:choose>
							<c:when test="${ActiveStates[i-1]}">
							    <tr>
							        <!-- 編號 -->
							        <td>${i }</td>
							        <!-- 作業類型 -->
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
												    	<a href="upLoadHw?hwId=${Homework[i-1].id }&l=${Homework[i-1].language }" style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:rgba(54,241,54)">  繳交 </a>
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
								    <!-- XX作業狀態XX -->
								    <c:choose>
								      	<c:when test="${ExistHW[i-1] == true}">
								            <c:if test = "${passRateList[i-1] == 1}">
								            	<!-- 作業狀態 -->
								            	<td>
								            		<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:green">
														通過
											    	</span>
								            	</td>
									       	    <!-- 測試結果 -->
									       	    <td class="selectable">
								      				<a class="selectable" href="CheckResult?questionID=${Homework[i-1].id }&studentID=${Student.name}"  style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:rgba(159,162,255);">  查看結果 </a>
										    	</td>
								      	    </c:if>
								      	    <c:if test = "${passRateList[i-1] == -1}">
								            	<!-- 作業狀態 -->
								            	<td>
								            		<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:red">
														未通過
											    	</span>
								            	</td>
									       	    <!-- 測試結果 -->
									       	    <td class="selectable">
								      				<a class="selectable" href="CheckResult?questionID=${Homework[i-1].id }&studentID=${Student.name}"  style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:rgba(159,162,255);">  查看結果 </a>
										    	</td>
								      	    </c:if>
								      	    <c:if test = "${passRateList[i-1] == -4}">
								            	<!-- 作業狀態 -->
								            	<td>
								            		<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:red">
														編譯失敗
											    	</span>
								            	</td>
									       	    <!-- 測試結果 -->
									       	    <td class="selectable">
								      				<a class="selectable" href="CheckResult?questionID=${Homework[i-1].id }&studentID=${Student.name}"  style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:rgba(159,162,255);">  查看結果 </a>
										    	</td>
								      	    </c:if>
								      	    <c:if test = "${passRateList[i-1] == -2}">
								            	<!-- 作業狀態 -->
								            	<td>
								            		<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:grey">
														批改中
											    	</span>
								            	</td>
									       	    <!-- 測試結果 -->
									       	    <td>-</td>
								      	    </c:if>
								      	    <c:if test = "${passRateList[i-1] == -3}">
								            	<!-- 作業狀態 -->
								            	<td>
								            		<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:lightgrey">
														待批改
											    	</span>
								            	</td>
									       	    <!-- 測試結果 -->
									       	    <td>
									       	    	<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:tomato">
														尚未設定測資
											    	</span>
									       	    </td>
								      	    </c:if>
									  	</c:when>
									  	<c:otherwise>
									  	    <!-- 作業狀態 -->
									  	    <td>
									  	    	<span style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold;">
													未繳
												</span>
									        </td>
									        <!-- 測試結果 -->
									        <td>-</td>
									    </c:otherwise>
								    </c:choose>
								    <!-- 通過測試名單 -->
							        <td class="right aligned selectable">
							        	<a class="selectable" href="success.jsp?HW_ID=${Homework[i-1].id }" style="font-family:標楷體; font-size:medium; word-wrap:break-word; word-break:normal; font-weight:bold; color:rgba(159,162,255);">  通過測試名單 </a>
								    </td>
							    </tr>
						    </c:when>
					    </c:choose>
				    </c:forEach>
			    </tbody>
			</table>
        </div>
    </body>

</html>