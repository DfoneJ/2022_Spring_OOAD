/*
 * JSP generated by Resin-4.0.41 (built Mon, 22 Sep 2014 09:54:25 PDT)
 */

package _jsp;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import WebGUI.*;

public class _index__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  private boolean _caucho_isNotModified;
  private com.caucho.jsp.PageManager _jsp_pageManager;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    com.caucho.jsp.PageContextImpl pageContext = _jsp_pageManager.allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);

    TagState _jsp_state = null;

    try {
      _jspService(request, response, pageContext, _jsp_application, session, _jsp_state);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_pageManager.freePageContext(pageContext);
    }
  }
  
  private void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response,
              com.caucho.jsp.PageContextImpl pageContext,
              javax.servlet.ServletContext application,
              javax.servlet.http.HttpSession session,
              TagState _jsp_state)
    throws Throwable
  {
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    javax.servlet.jsp.tagext.JspTag _jsp_parent_tag = null;
    com.caucho.jsp.PageContextImpl _jsp_parentContext = pageContext;
    response.setContentType("text/html; charset=UTF-8");

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
	SettingInformation settingInformation = new SettingInformation();
	settingInformation.setPath("D:/c/test/");
	PlagiarismDetection plagiarismDetection = new CAndCppPlagiarismDetection(settingInformation);
	//String[][] result = plagiarismDetection.execute();
	 
    out.write(_jsp_string1, 0, _jsp_string1.length);
  }

  private com.caucho.make.DependencyContainer _caucho_depends
    = new com.caucho.make.DependencyContainer();

  public java.util.ArrayList<com.caucho.vfs.Dependency> _caucho_getDependList()
  {
    return _caucho_depends.getDependencies();
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    _caucho_depends.add(depend);
  }

  protected void _caucho_setNeverModified(boolean isNotModified)
  {
    _caucho_isNotModified = true;
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;

    if (_caucho_isNotModified)
      return false;

    if (com.caucho.server.util.CauchoSystem.getVersionId() != -5778918481123896327L)
      return true;

    return _caucho_depends.isModified();
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
    TagState tagState;
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("index.jsp"), 150650186714848213L, false);
    _caucho_depends.add(depend);
  }

  final static class TagState {

    void release()
    {
    }
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void caucho_init(ServletConfig config)
  {
    try {
      com.caucho.server.webapp.WebApp webApp
        = (com.caucho.server.webapp.WebApp) config.getServletContext();
      init(config);
      if (com.caucho.jsp.JspManager.getCheckInterval() >= 0)
        _caucho_depends.setCheckInterval(com.caucho.jsp.JspManager.getCheckInterval());
      _jsp_pageManager = webApp.getJspApplicationContext().getPageManager();
      com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
      com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.InitPageContextImpl(webApp, this);
    } catch (Exception e) {
      throw com.caucho.config.ConfigException.create(e);
    }
  }

  private final static char []_jsp_string1;
  private final static char []_jsp_string0;
  static {
    _jsp_string1 = "\r\n	 <div style=\"margin-right:10px; position:fixed; right:20px; background-color:#AAAAAA;\">\r\n		<button type=\"button\" class=\"btn btn-default btn-lg\" onclick=\"showSetting()\">\r\n		  <span class=\"glyphicon glyphicon-cog\" aria-hidden=\"true\"></span>\r\n		</button>\r\n	 </div>\r\n	 \r\n	<div class=\"panel panel-default\" style=\"width:70%; margin-right:auto; margin-left:auto; margin-top:20px;\">\r\n		  <!-- Default panel contents -->\r\n		  <div class=\"panel-heading\">\u5831\u8868</div>\r\n		  <div class=\"panel-body\">\r\n		    <p>\r\n		   		<form name=\"myform\" action=\"UploadAndCheckResult\" method=\"post\" enctype=\"multipart/form-data\">\r\n				      File:<input type=\"file\" name=\"file\" id=\"file\" style=\"display:inline; width: 150px;\">\r\n				      <button type=\"button\" class=\"btn btn-primary\"  style=\"display:inline; \" onClick=\"ajaxUploadFile()\">upload</button>\r\n				 </form>\r\n			</p>\r\n		  </div>\r\n		  <!-- Table -->\r\n		  <div id=\"resultTable\">\r\n		  </div>\r\n		</div>\r\n		\r\n		<div id=\"loading_div\" style=\"display:none;\">\r\n		   <div style=\"position: fixed; top:0%; background-color:#888888; height:100%; width:100%; opacity:0.5;position: absolute;display: block;\">\r\n			  <span style=\"position: fixed;left:48%;top:40%;color:#000;opacity:1;\">\r\n				<img src=\"./img/loading75.gif\">\r\n			  </span>\r\n			</div>\r\n		</div>\r\n	<script>\r\n	var uploading = false;\r\n		\r\n	function ajaxUploadFile(){ \r\n		if(uploading==true){\r\n			console.log(\"\u4e0a\u50b3\u4e2d...\u8acb\u7b49\u5f85\u7d50\u679c...\");\r\n			return;\r\n		}\r\n		$(\"#loading_div\").show();\r\n		\r\n		uploading = true;\r\n		var theUrl = 'UploadAndCheckResult';\r\n		var data = new FormData();\r\n		console.log(theUrl);\r\n		jQuery.each($('#file')[0].files, function(i, file) {\r\n			data.append('file', file);\r\n		});\r\n		console.log(theUrl);\r\n	\r\n		 $.ajax({\r\n			url: theUrl,\r\n			type: 'POST',\r\n			data: data,\r\n			cache: false,\r\n			processData: false, // Don't process the files\r\n			contentType: false, // Set content type to false as jQuery will tell the server its a query string request\r\n			success: function(data, textStatus, jqXHR)\r\n			{\r\n				uploading = false;\r\n				//alert(data);\r\n				$('#resultTable').html(data);\r\n				$(\"#loading_div\").hide();\r\n			},\r\n			error: function(jqXHR, textStatus, errorThrown)\r\n			{\r\n				// Handle errors here\r\n				uploading = false;\r\n				console.log('ERRORS: ' + textStatus +\"|\"+ errorThrown);\r\n				// STOP LOADING SPINNER\r\n				$(\"#loading_div\").hide();\r\n			}\r\n		});\r\n		 return;\r\n	}\r\n	\r\n	function showSetting(){\r\n		var theUrl = 'Setting.jsp';\r\n		$.ajax({\r\n			url: theUrl,\r\n			type: 'POST',\r\n			data: {},\r\n			success: function(data, textStatus, jqXHR)\r\n			{\r\n				showDialog(data);\r\n				//bootbox.alert(\"1234\");\r\n			},\r\n			error: function(jqXHR, textStatus, errorThrown)\r\n			{\r\n				console.log('ERRORS: ' + textStatus +\"|\"+ errorThrown);\r\n			}\r\n		});\r\n	}\r\n	\r\n	function showDialog(data){\r\n		bootbox.dialog({\r\n			message: data,\r\n			title: \"\u8a2d\u5b9a\",\r\n			buttons: {\r\n				success: {\r\n					label: \"\u78ba\u5b9a\",\r\n					className: \"btn-success\",\r\n					callback: function() {\r\n						var theUrl = 'UpdateSetting';\r\n						$.ajax({\r\n							url: theUrl,\r\n							type: 'POST',\r\n							data: $(\"#settingForm\").serialize(),\r\n							success: function(data, textStatus, jqXHR)\r\n							{\r\n								console.log(data);\r\n								//bootbox.alert(\"1234\");\r\n							},\r\n							error: function(jqXHR, textStatus, errorThrown)\r\n							{\r\n								console.log('ERRORS: ' + textStatus +\"|\"+ errorThrown);\r\n							}\r\n						});\r\n						console.log(\"great success\");\r\n					}\r\n				},\r\n				main: {\r\n					label: \"\u53d6\u6d88\",\r\n					className: \"btn-primary\",\r\n					callback: function() {\r\n						console.log(\"cancel\");\r\n					}\r\n				}\r\n			}\r\n		});\r\n	}\r\n	\r\n	</script>\r\n</body>\r\n</html>".toCharArray();
    _jsp_string0 = "\r\n\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n<title>Insert title here</title>\r\n</head>\r\n	<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js\"></script>\r\n	<script src=\"./lib/bootbox.min.js\"></script>\r\n	\r\n	<script src=\"./lib/bootstrap/js/bootstrap.min.js\"></script>\r\n	<link rel=\"stylesheet\" href=\"./lib/bootstrap/css/bootstrap.min.css\" media=\"all\" />\r\n	<link rel=\"stylesheet\" href=\"./lib/bootstrap/css/bootstrap-theme.min.css\" media=\"all\" />\r\n<body>\r\n	".toCharArray();
  }
}
