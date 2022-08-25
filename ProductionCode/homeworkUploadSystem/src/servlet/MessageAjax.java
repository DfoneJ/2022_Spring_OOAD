package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DBcon;
import componment.Article;
import componment.DateBean;
import componment.Student;
import componment.Teacher;
import componment.User;

/**
 * Servlet implementation class MessageAjax
 */
@WebServlet("/MessageAjax")
public class MessageAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageAjax() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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

		try {
			String check = request.getParameter("case");
			if (check.equals("replySomeone")) {
				String masterTime = request.getParameter("masterTime");
				String replyContent = request.getParameter("replyContent");
				System.out.println(masterTime);
				System.out.println(replyContent);
				DateBean dateBean = new DateBean();
				String time = dateBean.getDateTime();
				time = time.substring(0, 10) + "_" + time.substring(11, 19);
//				System.out.println(masterTime);
				Article art = new Article(time, masterTime, replyContent, user.getName(), "1");
				art.writeDBforReply(user.getDbName());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
