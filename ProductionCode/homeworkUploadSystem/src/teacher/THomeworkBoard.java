package teacher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import componment.HomeworkQuestion;
import componment.MyUtil;
import componment.Teacher;
import componment.UpLoadLockInfo;
import componment.ActiveHomeworkStateInfo;
import componment.Course;
import componment.Homework;

/**
 * Servlet implementation class THomeworkBoard
 */
@WebServlet("/THomeworkBoard")
public class THomeworkBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public THomeworkBoard() {
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
		Course course = (Course) session.getAttribute("Course");
		System.out.println(course.getTeacherId());
		System.out.println(course.getName());

		if (tr == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		String hwType = request.getParameter("hwType");
		if (hwType == null) {
			hwType = "課後作業";
		}
		
		ArrayList<?> hws = HomeworkQuestion.getAllHomeworks(tr.getDbName(), hwType);
		ArrayList<?> uhws = Homework.getUserHomework(tr.getDbName(), tr.getName());
		ArrayList<Boolean> lock = new ArrayList<Boolean>();
		ArrayList<Boolean> activeState = new ArrayList<Boolean>();
		ArrayList<Boolean> existHW = new ArrayList<Boolean>();
		boolean isShowActiveStateField = false;
		HomeworkQuestion hw;
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		isShowActiveStateField = ActiveHomeworkStateInfo.isColumnInDatabase(tr.getDbName());
		for (int i = 1; i <= hws.size(); i++) {
			hw = (HomeworkQuestion) hws.get(i - 1);
			lock.add(UpLoadLockInfo.isEnable(tr.getDbName(), hw.getId()));
			if (isShowActiveStateField)
				activeState.add(ActiveHomeworkStateInfo.isHWActive(tr.getDbName(), hw.getId()));
		}
		request.setAttribute("Homework", hws);
		request.setAttribute("Lock", lock);
		request.setAttribute("IsShowActiveStateField", isShowActiveStateField);
		request.setAttribute("ActiveStates", activeState);
		
		request.getRequestDispatcher("/WEB-INF/teacher/THomeworkBoard.jsp").forward(request, response);
	}	
}
