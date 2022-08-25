package tester;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ExamDB.DbProxy;
import componment.Article;
import componment.Student;
import componment.Teacher;
import componment.User;

/**
 * Servlet implementation class MessageBoard
 */
@WebServlet("/MessageBoard")
public class MessageBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageBoard() {
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
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		Student st = (Student) session.getAttribute("Student");
		if ((tr == null) && (st == null)) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		User user = User.getUser(tr, st);
		String sql = "select teacherId from course where id = '" + user.getDbName() + "';";
		String teacherID = DbProxy.getData("course", sql, "teacherId");
		boolean isTeacher = false;
		if (user instanceof Teacher) {
			isTeacher = true;
		}
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		
		ArrayList<?> arts = Article.getAllArticle(user.getDbName());
		Article art = null;
		
		for (int k = 0; k < arts.size(); k++) {
			art = (Article) arts.get(k);
		
			String modi = art.getBody().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			art.setBody(modi.trim());
			
			modi = art.getTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			art.setTitle(modi.trim());		
		}
		
		
		request.setAttribute("Articles", arts);
		request.setAttribute("teacherID", teacherID);
		request.setAttribute("isTeacher", isTeacher);
		
		request.getRequestDispatcher("/WEB-INF/component/MessageBoard.jsp").forward(request, response);
	}

}
