package tester;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import componment.ActiveHomeworkStateInfo;
import componment.HomeworkQuestion;
import componment.Student;
import componment.Teacher;
import componment.UpLoadLockInfo;
import componment.Homework;


import bean.DBcon;
import bean.TestData;
import bean.TestResult;

/**
 * Servlet implementation class aHomeworkBoard
 */
@WebServlet("/HomeworkBoard")
public class HomeworkBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeworkBoard() {
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
		Student st = (Student) session.getAttribute("Student");
		if (st == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		
		ArrayList<?> hws;
		HomeworkQuestion hw;
		String hwType = request.getParameter("hwType");
		if (hwType == null) {
			hwType = "課後作業";
		}
		hws = HomeworkQuestion.getAllHomeworks(st.getDbName(), hwType);
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		ArrayList<?> uhws = Homework.getUserHomework(st.getDbName(), st.getName());
		ArrayList<Boolean> lock = new ArrayList<Boolean>();
		ArrayList<Boolean> actives = new ArrayList<Boolean>();
		ArrayList<Boolean> existHW = new ArrayList<Boolean>();
		
		DBcon dbCon = new DBcon();
		String dbName = "";
		int questionID = -1;
		String studentID = "";
		dbName = st.getDbName();
		ArrayList<TestData> testDataFromQuestion = new ArrayList<TestData>();
		ArrayList<TestResult> result = new ArrayList<TestResult>();
		ArrayList<Integer> passRate = new ArrayList<Integer>();
		int true_count = 0;
		boolean isActiveFieldExist = ActiveHomeworkStateInfo.isColumnInDatabase(st.getDbName());

		try {
			for (int i = 1; i <= hws.size(); i++) {
				hw = (HomeworkQuestion) hws.get(i - 1);
				lock.add(UpLoadLockInfo.isEnable(st.getDbName(), hw.getId()));
				actives.add((isActiveFieldExist == false) || (ActiveHomeworkStateInfo.isHWActive(st.getDbName(), hw.getId())));
				//System.out.println(st.getDbName()+hw.getId()); //former is subject name, and latter is question id.
				questionID = Integer.parseInt(hw.getId());
				studentID = st.getName();
				//System.out.println("questionID:"+questionID);
				existHW.add(Homework.isExistUserOneHomework(uhws, hw.getId()));
				result = dbCon.getTestResult(dbName, questionID, studentID); // All results that student's program has wrote to DB.
				testDataFromQuestion = dbCon.getTestDatas(dbName, questionID); // All test data that belongs to the question.
				true_count = 0; // number of correct results the student has w.r.t. the question.
				for(TestResult testR : result) {
					if (testR.isResult())
						true_count++;
				}
				// -3:No test data, -2:Testing , -1:Not pass , 1:Pass
				if (testDataFromQuestion.size()==0) {
					passRate.add(-3); // TA forgot to set test data for question.
				}
				else if (result.size() > 0 && result.get(0).isResult() == false && result.get(0).getTest_data_id() == -1) {
					passRate.add(-4); // compiler error or use system function
				}
				else if(result.size()!=testDataFromQuestion.size()) {
					passRate.add(-2); // still waiting for all test result.
				}
				else if (true_count!=testDataFromQuestion.size()) {
					passRate.add(-1); // number of correct result NOT EQUALS to the total number of test data.
				}
				else { passRate.add(1); } // pass all test.
					
			}
			//System.out.println("sizeOfPass:"+passRate.size());
		}catch (Exception e) {}
		
		/*
		DBcon dbCon = new DBcon();
		String dbName = "";
		int questionID = -1;
		String studentISystem.out.println("SID:"+D = "";
		dbName = st.getDbName();
		ArrayList<TestResult> result = new ArrayList<TestResult>();
		try {
			questionID = ;
			studentID = st.getName();
		} catch (Exception e) {
		}
		*/
		
		
//		ServletContext servletContext = getServletContext();
//		int percent = (Integer)servletContext.getAttribute("percent");
//		
//		request.setAttribute("percent", percent);
//		
		request.setAttribute("passRateList", passRate); // (List) homework with SPECIFIED id is (pass, no pass, in process, wait process, not have test data)
		request.setAttribute("Homework", hws); // (List) homework list
		request.setAttribute("ActiveStates", actives); // (List) homework with SPECIFIED id can show on student's homework board
		request.setAttribute("Lock", lock); // (List) homework with SPECIFIED id can upload
		request.setAttribute("ExistHW", existHW); // (List) homework with SPECIFIED id is uploaded
		

		request.getRequestDispatcher("/WEB-INF/student/HomeworkBoard.jsp").forward(request, response);
	}

}
