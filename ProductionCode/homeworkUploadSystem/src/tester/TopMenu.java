package tester;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import componment.MyUtil;
import componment.Student;

/**
 * Servlet implementation class TopMenu
 */
@WebServlet(name = "TopMenu", urlPatterns = { "/TopMenu" })
public class TopMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		Student st = (Student) session.getAttribute("Student");
		if (st == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		session.getAttribute("CourseName");
		
		request.setAttribute("Name", st.getRealName());
		request.setAttribute("ID", st.getName());
		
//		pw.println(st.getCourse() + ":" + st.getName() + "_" + st.getRealName());
		request.getRequestDispatcher("/WEB-INF/student/TopMenu.jsp").forward(request, response);
	
	}

}
