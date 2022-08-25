package student;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.http.*;

import bean.DBcon;
import bean.StudentMistackDao;

import javax.servlet.*;
import componment.*;
import componment.UploadHW.FeedbackBox;
import componment.UploadHW.FeedbackForStudent;

public class upLoadHw extends HttpServlet {

	private static final long serialVersionUID = 581155036630424356L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		String hwId = req.getParameter("hwId");
		Student st = (Student) session.getAttribute("Student");
		if (st == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter pw = res.getWriter();
		MyUtil.printHtmlHead(pw);
		pw.println("<!DOCTYPE html>");
		pw.println("<meta charset='UTF-8'>");
		HomeworkQuestion hw = HomeworkQuestion.getHomework(st.getDbName(), hwId);
		if (hw == null) {
			pw.println("<h2>請依正常程序繳交</h2>");
			pw.println("<br><input type=\"button\" value=\"上一頁\" onclick=\"history.go( -1 );return true;\"> ");
		} else if (hwId == null) {
			pw.println("<h2>請依正常程序繳交</h2>");
			pw.println("<br><input type=\"button\" value=\"上一頁\" onclick=\"history.go( -1 );return true;\"> ");
		} else if (hw.isExpired()) {
			pw.println("<h2>作業繳交期限已過</h2>");
			pw.println("<br><input type=\"button\" value=\"上一頁\" onclick=\"history.go( -1 );return true;\"> ");
		} else if (Homework.isExistUserOneHomework(st.getDbName(), st.getName(), hwId)) {
			req.setAttribute("hwid", hwId);
			req.getRequestDispatcher("/WEB-INF/student/UpLoadFileDuplicate.jsp").forward(req, res);
		} else {
			session.setAttribute("hwId", hwId);
			System.out.println("stname:" + st.getName());
			req.setAttribute("hwId", hwId);
			req.setAttribute("name", st.getName());
			
			String language = req.getParameter("l");
			language = language.toLowerCase();
			if(language.equals("c"))
				req.setAttribute("language", language);
			
			req.setAttribute("fileType", this.getFileType(language));
			DBcon db = new DBcon();
			StudentMistackDao stmd = null;
			
			try {
				stmd = db.getStudentWholeMistakes(st.getDbName(), st.getName());
				if(stmd != null) {
					FeedbackForStudent ffs = new FeedbackForStudent(stmd);
					FeedbackBox fb = ffs.getFeedback();
					req.setAttribute("FeedbackBox", fb);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			req.getRequestDispatcher("/WEB-INF/student/UploadHW.jsp").forward(req, res);
			
			return;
//			pw.println("<p> 作業上傳：檔名請使用英文，上傳檔案大小請在2M以下</p>");
//			pw.println("<form name=Form1 enctype=\"multipart/form-data\" method=post action=upLoadFile>");
//			pw.println("上傳檔案: <input type=file name=hwFile size=20 maxlength=20>");
//			pw.println("<br><br>檔案敘述: <input type=text name=FileDesc size=30 maxlength=50></p><p>");
//			pw.println("<input type=submit value=upload>");
//			pw.println("<input type=reset value=reset></p>");
//			pw.println("</form></center>");
		}

		pw.println("</center></body></html>");
		pw.close();
	}
	private String getFileType(String lang) {
		if(lang.equals("c"))
			return "c";
		else if(lang.equals("python"))
			return "py";
		else if(lang.equals("java"))
			return "java";
		else
			return "cs";
	}
}