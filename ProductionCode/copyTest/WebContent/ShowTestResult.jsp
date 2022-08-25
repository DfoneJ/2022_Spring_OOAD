<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% 
	String[][] result = (String[][])request.getAttribute("resultArray");
%>
			  <table class="table">
				<tr>
				   	<td style="width:30%;">HW1</td>
				   	<td style="width:30%;">HW2</td>
				   	<td>Similarity</td>
				</tr>
				<%for(int i=0;i<result.length;i++){
					%>
				   	<tr>
						<td><%=result[i][0]%></td>
						<td><%=result[i][1]%></td>
						<td>
							<div class="progress">
							  <div class="progress-bar" role="progressbar" aria-valuenow="<%=result[i][2]%>" aria-valuemin="0" aria-valuemax="100" style="width: <%=result[i][2]%>%;">
							    <%=result[i][2]%>%
							  </div>
							</div>
						</td>
					</tr>
					<%
				} %>
			  </table>