<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" import="WebGUI.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="./lib/bootbox.min.js"></script>
	
	<script src="./lib/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="./lib/bootstrap/css/bootstrap.min.css" media="all" />
	<link rel="stylesheet" href="./lib/bootstrap/css/bootstrap-theme.min.css" media="all" />
<body>
	<%
	SettingInformation settingInformation = new SettingInformation();
	settingInformation.setPath("D:/c/test/");
	PlagiarismDetection plagiarismDetection = new CAndCppPlagiarismDetection(settingInformation);
	//String[][] result = plagiarismDetection.execute();
	 %>
	 <div style="margin-right:10px; position:fixed; right:20px; background-color:#AAAAAA;">
		<button type="button" class="btn btn-default btn-lg" onclick="showSetting()">
		  <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
		</button>
	 </div>
	 
	<div class="panel panel-default" style="width:70%; margin-right:auto; margin-left:auto; margin-top:20px;">
		  <!-- Default panel contents -->
		  <div class="panel-heading">報表</div>
		  <div class="panel-body">
		    <p>
		   		<form name="myform" action="UploadAndCheckResult" method="post" enctype="multipart/form-data">
				      File:<input type="file" name="file" id="file" style="display:inline; width: 150px;">
				      <button type="button" class="btn btn-primary"  style="display:inline; " onClick="ajaxUploadFile()">upload</button>
				 </form>
			</p>
		  </div>
		  <!-- Table -->
		  <div id="resultTable">
		  </div>
		</div>
		
		<div id="loading_div" style="display:none;">
		   <div style="position: fixed; top:0%; background-color:#888888; height:100%; width:100%; opacity:0.5;position: absolute;display: block;">
			  <span style="position: fixed;left:48%;top:40%;color:#000;opacity:1;">
				<img src="./img/loading75.gif">
			  </span>
			</div>
		</div>
	<script>
	var uploading = false;
		
	function ajaxUploadFile(){ 
		if(uploading==true){
			console.log("上傳中...請等待結果...");
			return;
		}
		$("#loading_div").show();
		
		uploading = true;
		var theUrl = 'UploadAndCheckResult';
		var data = new FormData();
		console.log(theUrl);
		jQuery.each($('#file')[0].files, function(i, file) {
			data.append('file', file);
		});
		console.log(theUrl);
	
		 $.ajax({
			url: theUrl,
			type: 'POST',
			data: data,
			cache: false,
			processData: false, // Don't process the files
			contentType: false, // Set content type to false as jQuery will tell the server its a query string request
			success: function(data, textStatus, jqXHR)
			{
				uploading = false;
				//alert(data);
				$('#resultTable').html(data);
				$("#loading_div").hide();
			},
			error: function(jqXHR, textStatus, errorThrown)
			{
				// Handle errors here
				uploading = false;
				console.log('ERRORS: ' + textStatus +"|"+ errorThrown);
				// STOP LOADING SPINNER
				$("#loading_div").hide();
			}
		});
		 return;
	}
	
	function showSetting(){
		var theUrl = 'Setting.jsp';
		$.ajax({
			url: theUrl,
			type: 'POST',
			data: {},
			success: function(data, textStatus, jqXHR)
			{
				showDialog(data);
				//bootbox.alert("1234");
			},
			error: function(jqXHR, textStatus, errorThrown)
			{
				console.log('ERRORS: ' + textStatus +"|"+ errorThrown);
			}
		});
	}
	
	function showDialog(data){
		bootbox.dialog({
			message: data,
			title: "設定",
			buttons: {
				success: {
					label: "確定",
					className: "btn-success",
					callback: function() {
						var theUrl = 'UpdateSetting';
						$.ajax({
							url: theUrl,
							type: 'POST',
							data: $("#settingForm").serialize(),
							success: function(data, textStatus, jqXHR)
							{
								console.log(data);
								//bootbox.alert("1234");
							},
							error: function(jqXHR, textStatus, errorThrown)
							{
								console.log('ERRORS: ' + textStatus +"|"+ errorThrown);
							}
						});
						console.log("great success");
					}
				},
				main: {
					label: "取消",
					className: "btn-primary",
					callback: function() {
						console.log("cancel");
					}
				}
			}
		});
	}
	
	</script>
</body>
</html>