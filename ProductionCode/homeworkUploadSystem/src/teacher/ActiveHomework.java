package teacher;

import componment.*;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

public class ActiveHomework extends HttpServlet {

	private static final long serialVersionUID = 309954331154098686L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(true);
		// Check if session expired.
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		String id = req.getParameter("hwId");
		String setActive = req.getParameter("setActive");
		HomeworkQuestion.activeHomework(tr.getDbName(), id, (setActive.equals("true")) ? "1" : "0");
		res.sendRedirect("THomeworkBoard");
	}
}