/**
 * 
 */

function checkDel(url) {
	if (confirm('Sure, y/n ?')==1) {
		location.href=url; 
	} 
}

function checkString(s1,s2) {  
	if ((s1=="")||(s2=="")) {
		alert("請填完整"); 
		return false;  
	} 
}  

function look(){
	if(event.altKey) {
		alert("禁止按Alt鍵!");	
	}
	document.onkeydown=look; 
}

function scroll(){
	window.status="hello"; 
	setTimeout("scroll()",100); 
}

function checkModify(f) {
	if (f.length == 0) { alert("請填完整");
		return false;  
	}
	if (confirm('Sure, y/n ?')==1)   {  
		return true; 
	}
	return false;
}











