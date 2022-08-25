package student;

import java.io.*;
import javax.servlet.http.*;

import bean.DBcon;
import bean.StudentMistackDao;

import javax.servlet.*;
import componment.*;

public class showHomework extends HttpServlet {

	private static final long serialVersionUID = 5779368787840125364L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		Student st = (Student) session.getAttribute("Student");
		User user = User.getUser(tr, st);
		if (st == null && tr == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter pw = res.getWriter();
		MyUtil.printHtmlHead(pw);
		String hwId = req.getParameter("hwId");
		int i = Integer.parseInt(hwId);
		String HWID = String.valueOf(i);
		
		DBcon db = new DBcon();
		HomeworkQuestion hw = HomeworkQuestion.getHomework(user.getDbName(), hwId);
		StudentMistackDao hwmd = null;
		try {
			hwmd = db.selectMistack(user.getDbName(), null, HWID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		String content = hw.getContent();
		req.setAttribute("hwmd", hwmd);
		req.setAttribute("content", content);
		req.setAttribute("hwID", hwId);
		if(tr != null) {
			req.setAttribute("isTr", true);
		}else {
			req.setAttribute("isTr", false);
		}
		req.getRequestDispatcher("WEB-INF/student/ShowHomework.jsp").forward(req, res);
	}
}
