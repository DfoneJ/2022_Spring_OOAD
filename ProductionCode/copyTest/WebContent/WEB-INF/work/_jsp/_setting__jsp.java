/*
 * JSP generated by Resin-4.0.41 (built Mon, 22 Sep 2014 09:54:25 PDT)
 */

package _jsp;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import WebGUI.*;

public class _setting__jsp extends com.caucho.jsp.JavaPage
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
    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((settingInformation.getPath()));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.print((settingInformation.isStructureAnalyzer()));
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((settingInformation.isVariableAnalyzer()));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((settingInformation.isTextAnalyzer()));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((settingInformation.getThresholdOfText()));
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((settingInformation.getThresholdOfStructure()));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((settingInformation.getThresholdOfVariable()));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((settingInformation.getThresholdOfTotal()));
    out.write(_jsp_string9, 0, _jsp_string9.length);
    out.print((settingInformation.getWeightOfText()));
    out.write(_jsp_string10, 0, _jsp_string10.length);
    out.print((settingInformation.getWeightOfStructure()));
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((settingInformation.getWeightOfVariable()));
    out.write(_jsp_string12, 0, _jsp_string12.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("Setting.jsp"), -354107554295219255L, false);
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

  private final static char []_jsp_string9;
  private final static char []_jsp_string11;
  private final static char []_jsp_string10;
  private final static char []_jsp_string8;
  private final static char []_jsp_string12;
  private final static char []_jsp_string1;
  private final static char []_jsp_string7;
  private final static char []_jsp_string3;
  private final static char []_jsp_string6;
  private final static char []_jsp_string5;
  private final static char []_jsp_string2;
  private final static char []_jsp_string4;
  private final static char []_jsp_string0;
  static {
    _jsp_string9 = "\"></input>\r\n					</div>\r\n				  </div>\r\n				</div>\r\n				\r\n				<div class=\"panel panel-primary\">\r\n				  <div class=\"panel-heading\">\r\n				    Weight\r\n				  </div>\r\n				  <div class=\"panel-body\">\r\n				  	<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Text:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"WeightOfText\" value=\"".toCharArray();
    _jsp_string11 = "\"></input>\r\n					</div>\r\n					<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Variable:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"WeightOfVariable\" value=\"".toCharArray();
    _jsp_string10 = "\"></input>\r\n					</div>\r\n					<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Structure:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"WeightOfStructure\" value=\"".toCharArray();
    _jsp_string8 = "\"></input>\r\n					</div>\r\n					<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Total:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"ThresholdOfTotal\" value=\"".toCharArray();
    _jsp_string12 = "\"></input>\r\n					</div>\r\n				  </div>\r\n				</div>\r\n			</form>	\r\n<script>\r\n\r\n\r\n</script>".toCharArray();
    _jsp_string1 = "\r\n			<form id=\"settingForm\">\r\n				<div class=\"panel panel-primary\">\r\n				  <div class=\"panel-heading\">\r\n				    Normal\r\n				  </div>\r\n				  <div class=\"panel-body\">\r\n				  	<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">path:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"path\" value=\"".toCharArray();
    _jsp_string7 = "\"></input>\r\n					</div>\r\n					<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Variable:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"ThresholdOfVariable\" value=\"".toCharArray();
    _jsp_string3 = "\"></input>\r\n					</div>\r\n					<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Variable:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"VariableAnalyzer\" value=\"".toCharArray();
    _jsp_string6 = "\"></input>\r\n					</div>\r\n					<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Structure:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"ThresholdOfStructure\" value=\"".toCharArray();
    _jsp_string5 = "\"></input>\r\n					</div>\r\n				  </div>\r\n				</div>\r\n				\r\n				<div class=\"panel panel-primary\">\r\n				  <div class=\"panel-heading\">\r\n				    Threshold\r\n				  </div>\r\n				  <div class=\"panel-body\">\r\n				  	<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Text:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"ThresholdOfText\" value=\"".toCharArray();
    _jsp_string2 = "\"></input>\r\n					</div>\r\n				  </div>\r\n				</div>\r\n				\r\n				<div class=\"panel panel-primary\">\r\n				  <div class=\"panel-heading\">\r\n				    Analyzer\r\n				  </div>\r\n				  <div class=\"panel-body\">\r\n				  	<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Structure:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"StructureAnalyzer\" value=\"".toCharArray();
    _jsp_string4 = "\"></input>\r\n					</div>\r\n					<div class=\"input-group\">\r\n					  <span class=\"input-group-addon\">Text:</span>\r\n					  <input type=\"text\" class=\"form-control\" name=\"TextAnalyzer\" value=\"".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}
