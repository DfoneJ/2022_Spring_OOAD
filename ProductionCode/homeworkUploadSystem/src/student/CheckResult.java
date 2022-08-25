package student;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DBcon;
import bean.TestData;
import bean.TestResult;
import componment.Student;
import componment.Teacher;

/**
 * Servlet implementation class CheckResult
 */
@WebServlet("/CheckResult")
public class CheckResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		Student st = (Student) session.getAttribute("Student");
		if (st == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		
		HttpSession session = request.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		Student st = (Student) session.getAttribute("Student");
		
		
		
		if(session.getAttribute("listOfStudentInfo") != null){
			java.util.ArrayList<String[]> list = (java.util.ArrayList<String[]>) session.getAttribute("listOfStudentInfo");
			for(int i = 0; i < list.size(); i++){
				if(list.get(i)[1].equals(request.getParameter("questionID"))){
					list.remove(i);
					session.removeAttribute("listOfStudentInfo");
				}
			}
		}

		String dbName = "";
		int questionID = -1;
		String studentID = "";
		
		String test ="";
		
		ArrayList<TestResult> result = new ArrayList<TestResult>();
		ArrayList<TestData> expectResults = new ArrayList<TestData>();
		if (st == null && tr == null) {
			return;
		} else {
			if (st != null)
				dbName = st.getDbName();
			else if (tr != null)
				dbName = tr.getDbName();

			DBcon dbCon = new DBcon();

			try {
				questionID = Integer.parseInt(request.getParameter("questionID"));
				studentID = request.getParameter("studentID").toString();
				test = st.getName();
				System.out.println(questionID+" || "+studentID+" || "+test);
			} catch (Exception e) {
			}

			if (questionID == -1)
				return;

			try {
				result = dbCon.getTestResult(dbName, questionID, studentID);
				expectResults = dbCon.getTestDatas(dbName, questionID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		ArrayList<Integer> TestID = new ArrayList<Integer>();
		ArrayList<Boolean> isPass = new ArrayList<Boolean>();
		ArrayList<String> errorMessage = new ArrayList<String>();
		ArrayList<String> executeResult = new ArrayList<String>();
		
		int percent = -1,true_count = 0;
		for(TestResult testR : result) {
			TestID.add(testR.getTest_data_id());
			isPass.add(testR.isResult());
			errorMessage.add(testR.getDescription());
			executeResult.add(testR.getExecuteResult());
			if (testR.isResult())
				true_count++;
			
		}
		
		if (result.size() != 0) 
			percent = 100 * true_count / result.size();
			
		request.setAttribute("TestID", TestID);
		request.setAttribute("isPass", isPass);
		request.setAttribute("percent", percent);
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("questionID", questionID);
		request.setAttribute("executeResult", executeResult);
		
	//	getServletContext().setAttribute("percent", percent);
		
		request.getRequestDispatcher("/WEB-INF/student/CheckResult.jsp").include(request, response);
		
	}
	

}
