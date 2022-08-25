<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Online Exam</title>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
<script
  src="https://code.jquery.com/jquery-3.1.1.min.js"
  integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
  crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>

<script type="text/javascript" src="javaScript/limitStudent.js"></script>

<link rel="stylesheet" href="style/TopMenuStyle.css"></link>
</head>
<body>

<div class="brown ui secondary pointing labeled icon menu" style="height:100%;">

   <a class="item" HREF=InitExam TARGET=DownMenu OnmouseOver="window.status='';return true;">
  	<i class ="info circle icon"></i>
  	<span class ="showtest" style="font-family: 標楷體; ">考試</span>
  </a>
  <a class="item" HREF=HwQuery TARGET=DownMenu OnmouseOver="window.status='';return true;">
  	<i class ="info circle icon"></i>
  	<span class ="showtest" style="font-family: 標楷體; ">觀看已交作業</span>
  </a>
  <a class="item" HREF=showScore TARGET=DownMenu OnmouseOver="window.status='';return true;">
  	<i class ="eye icon"></i>
  	<span class ="showtest" style="font-family: 標楷體; ">觀看成績</span>
  </a>
  <a class="item" HREF=HomeworkBoard TARGET=DownMenu OnmouseOver="window.status='';return true;">
  	<i class ="pencil alternate icon"></i>
  	<span class ="showtest" style="font-family: 標楷體; ">作業繳交</span>
  </a>
  <a class="item" HREF=MessageBoard TARGET=DownMenu OnmouseOver="window.status='';return true;">
  	<i class ="comment alternate icon"></i>
  	<span class ="showtest" style="font-family: 標楷體; ">留言版</span>
  </a>
  <a class="item" HREF=changePasswd TARGET=DownMenu OnmouseOver="window.status='';return true;">
 	<i class ="eraser icon"></i>
 	<span class ="showtest" style="font-family: 標楷體;">更改密碼</span>
  </a>
  
  
  <div class="right menu">
	  <div id = "studentInf" class="ui horizontal list" >
        <div class="item">
          <img class="ui mini circular image" src="image/school.png">
          <div class="content">
            <div class="ui sub header">${Name }</div>
              ${ID }
          </div>
        </div>
      </div>
   <a class="ui item"HREF=Logout TARGET=_parent OnmouseOver= "window.status='';return true;">
    <i class ="sign-out icon"></i>
    <span style="font-family: 標楷體;">登出</span>
    </a>
  </div>
</div>


</body>

<script>
$('.ui.secondary.pointing.labeled.icon.menu')
.on('click', '.item', function() {
  if(!$(this).hasClass('dropdown')) {
    $(this)
      .addClass('active')
      .siblings('.item')
        .removeClass('active');
  }
});
</script>
</html>