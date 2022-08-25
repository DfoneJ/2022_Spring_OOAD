<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UPLOAD HOMEWORKS</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>


<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>

<link rel="stylesheet" href="style/DownBackgroud.css"></link>
<link rel="stylesheet" href="style/UploadHWSytle.css"></link>

<script type="text/javascript" src="javaScript/limitStudent.js"></script>

</head>
<body>
	<div class="ui container">
		<div class="ui message brown" style="margin: 50px 0px 15px 0px">
			<p>
				<span
					style="font-family: 標楷體; color: black; behavior: slide; word-wrap: break-word; word-break: normal; font-weight: bold;">作業上傳：檔名請使用英文，上傳檔案大小請在2M以下</span>
			</p>

			<!--   <form id ="uploadForm" name=Form1 enctype="multipart/form-data" method=post> -->
			<!--<form enctype="multipart/form-data" method=post action="upLoadFile">-->
			<span
				style="font-family: 標楷體; color: black; behavior: slide; word-wrap: break-word; word-break: normal; font-weight: bold;">上傳你的主程式:
			</span> <input type=file id="hwFileUploader" name=hwFile size=20
				maxlength=20 required="required"> <br> <br>

			<c:if test="${not empty language}">
				<span
					style="font-family: 標楷體; color: black; behavior: slide; word-wrap: break-word; word-break: normal; font-weight: bold;">上傳副程式(支援上傳多個):
				</span>
				<input type="file" name="header" size="10" multiple />
				<p>
				<p>
					<!--  <button id="upload" onClick="uploadForm()"> upload</button> -->
					<b onclick="pop()" class="btn">貼心叮嚀</b>
			</c:if>
			<button id="uploadHWToServer">upload</button>
			<!--  <input type="submit" value=upload id="uploadHWToServer"></input> -->
			<input type=reset value=reset>
			</p>

			<!-- </form> -->
		</div>


		<div id="box">
			<span class="ion-information-circled"></span>

			<h3>最近"${FeedbackBox.typeIn1st }"的錯誤比例佔${Math.round(FeedbackBox.numberOfMistacksIn1st /  FeedbackBox.total * 100)}%</h3>
			<h3>${FeedbackBox.feedbackIn1st }</h3>
			<hr>
			<h3>最近"${FeedbackBox.typeIn2nd }"的錯誤比例佔${Math.round(FeedbackBox.numberOfMistacksIn2nd /  FeedbackBox.total * 100)}%</h3>
			<h3>${FeedbackBox.feedbackIn2nd }</h3>

			<c onclick="pop()" class="close">Close</c>
		</div>
		<script type="text/javascript">
			var c = 0;
			var errorMsg ;
			const fileUploader = document.querySelector('#hwFileUploader');

			function pop() {
				if (c == 0) {
					document.getElementById("box").style.display = "block";
					c = 1;
				} else {
					document.getElementById("box").style.display = "none";
					c = 0;
				}
			}
			document.getElementById('uploadHWToServer').addEventListener('click', (e)=>{
				const file = document.getElementById('hwFileUploader').files[0];
				
				if(file == undefined){
					alert('請先上傳作業檔案');
					return;
				}
		
				if(isHwFileValid(file.name)) {
					let formdata = transformUploadFileName(file);
					uploadFile(formdata);					
				} else {
					errorMsg = '作業請上傳有效檔名!';
					alert(errorMsg);
					location.reload();
				}
			});
			function isHwFileValid(filename) {
				let fileType = `${fileType}`;
		        let reOfFileNameCheck = /(\w+)(\.)(${fileType}){1}/g;
				let reOfFileTypeCheck = /(\.)(${fileType}){1}/g;
				let filetype = filename.match(reOfFileTypeCheck);
		        if(filetype == undefined || filetype.length!=1) {
					alert('作業檔案類型不符'); //Invalid file type, should get '.py' or '.c' or '.java''
					return false;
				}
						
				let checkFileName = filename.match(reOfFileNameCheck);
		        
		        if(checkFileName != null){
		            if(checkFileName.length !=1 || checkFileName[0].length!=filename.length ) {
		            	//check fail,the filename doesn't match the filename rule
		                alert('作業檔名不符命名規則');
		                return false;
		          	}
		        }
		        else {
		            alert('作業檔名不符命名規則');
		            return false
		        }	
				return true
				
			}
			function transformUploadFileName(file) {
				let fileType = `${fileType}`;
				const timestamp = new Date().getTime();
				let reOfFileTypeCheck = /(\.)(${fileType}){1}/g;
				
				let filetype = file.name.match(reOfFileTypeCheck);
			 	if(filetype.length!=1) {
					alert('作業檔案類型不符'); //Invalid file type, should get '.py' or '.c' or '.java'		
					return false;
				}
			 	if(fileType=="java"){
			 		var formdata = new FormData();
					formdata.append('hwFile', file, file.name);
					return formdata;
			 	}
			 	else {
					let newfilename =  '${name}'+"_" +  '${hwId}' + "_" + timestamp + filetype[0]; // new file name
					var formdata = new FormData();
					formdata.append('hwFile', file, newfilename);
					return formdata;
			 	}
			}
			function uploadFile(formdata) {
				$.ajax({
					  type: 'POST',
					  enctype: 'multipart/form-data',
					  url: 'upLoadFile',
					  data: formdata,
					  processData: false,
					  contentType : false,
					  success: function(data) {
						  var returnedData = JSON.parse(data)
						  console.log(returnedData.location)
						  
					      window.location = returnedData.location;//redirect
				
						 
					  }
					});
			}
			
		</script>
	</div>

</body>
</html>