package tester;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.modificationNote;
import componment.Article;
import componment.Student;

/**
 * Servlet implementation class DownMenu
 */
@WebServlet(name = "DownMenu", urlPatterns = { "/DownMenu" })
public class DownMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /** 
     * @see HttpServlet#HttpServlet()
     */
    public DownMenu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		String message = "";
		Student st = (Student) session.getAttribute("Student");
		if (st == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		//MyUtil.printHtmlHead(pw);
		message = Article.getNewsArticle(st.getDbName(), st.getCourse());
		request.setAttribute("message", message);
		request.setAttribute("ID", st.getName());
		request.setAttribute("Name", st.getRealName());
		request.setAttribute("Course", st.getCourse());
		request.setAttribute("Note", modificationNote.table);

		request.getRequestDispatcher("/WEB-INF/student/DownMenu.jsp").forward(request, response);
	}

}
