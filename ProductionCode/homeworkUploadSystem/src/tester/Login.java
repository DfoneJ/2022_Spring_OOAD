package tester;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ExamDB.DbProxy;
import bean.DBcon;
import componment.Course;
import componment.DateBean;
import componment.MyUtil;
import componment.Student;
import componment.User;

/**
 * Servlet implementation class Login
 */
@WebServlet(name = "Login", urlPatterns = { "/Login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String man = "1";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		System.out.println(request.getRemoteAddr().toString());
		String sql = "";
		String[] optName = null;
		Course c = null;
		sql = "SELECT time FROM people WHERE id ='1';";
		man = DbProxy.getData("course", sql, "time");
		ArrayList<?> courses = Course.getAllCourse();
		optName = new String[courses.size()];
		for (int i = 0; i < courses.size(); i++) {
			c = (Course) courses.get(i);
			optName[i] = c.getName();
		}

		request.setAttribute("man", man);
		request.setAttribute("optName", optName);
		request.getRequestDispatcher("/WEB-INF/student/Login.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		DBcon dbcon = new DBcon();
		ArrayList<String> ip;
		System.out.print(request.getRemoteAddr().toString());
		
		//被黑名單的IP
		try {
			ip = dbcon.checkBlockedIP();
			if(ip.contains(request.getRemoteAddr())) {
				response.sendRedirect("https://www.youtube.com/watch?v=F0hsuegHV1I");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String sql = "SELECT time FROM people WHERE id ='1';";
		man = DbProxy.getData("course", sql, "time");
		String strSQL = "";
		DateBean dateBean = new DateBean();
		String loginTime = dateBean.getDateTime();
		HttpSession session = request.getSession(true);
		String userName = request.getParameter("name");
		String userPasswd = request.getParameter("passwd");
		String i_course = request.getParameter("rdoCourse");
		int i = Integer.parseInt(i_course);
		ArrayList<?> courses = Course.getAllCourse();
		String[] opt = new String[courses.size()];
		String[] optName = new String[courses.size()];
		Course c = null;
		for (int k = 0; k < courses.size(); k++) {
			c = (Course) courses.get(k);
			opt[k] = c.getId();
			optName[k] = c.getName();
		}
		String course = opt[i - 1];
		String[] score = { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" };
		String dbName = opt[i - 1];
		String coursePath = "d:\\resin\\doc\\" + opt[i - 1] + "\\";
		//
		//System.out.println(dbName);
		//
		String realName = User.checkLogin(dbName, "Login", userName, userPasswd); //dbName=c
		//String realName = User.checkLogin(c, "Login", userName, userPasswd);
		//c C sysop jykuo1449
		Student student = null;

		if (realName.equals("密碼錯誤") || realName.equals("查無此人") || realName.equals("非法入侵")) {
			session.setAttribute("Student", student);
			session.setAttribute("Teacher", null);
			if (realName.equals("非法入侵")) {
				String message = " 非法入侵 你已被追蹤 ";
				request.setAttribute("message", message);
				request.setAttribute("status", "1");
			} else {
				request.setAttribute("message", realName);
				request.setAttribute("status", "2");
			}
			request.getRequestDispatcher("/WEB-INF/student/LoginError.jsp").forward(request, response);
			
		} else {
			student = new Student(dbName, userName, realName, userPasswd, course, coursePath, score);
			session.setAttribute("Student", student);
			session.setAttribute("Teacher", null);
			i = (Integer.parseInt(man) + 1);
			strSQL = "UPDATE people SET time = ? WHERE id = '1'";
			String[] param = new String[1];
			param[0] = Integer.toString(i);
			DbProxy.setData("course", strSQL, param);
			param = new String[2];
			param[0] = userName;
			param[1] = loginTime;
			strSQL = "insert into people values(?, ?)";
			DbProxy.setData("course", strSQL, param);
			response.sendRedirect("MainMenu");
		}
	
	}

}
