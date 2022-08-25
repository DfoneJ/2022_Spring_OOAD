package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ExamDB.DbProxy;
import bean.DBcon;
import bean.Question;
import bean.TestData;
import componment.Teacher;
import componment.User;

/**
 * Servlet implementation class HistoryQuetion
 */
//@WebServlet("/HistoryQuestionAjax")
public class HistoryQuestionAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		User user = User.getUser(tr, null);
		
		
		PrintWriter pw = response.getWriter();
//		pw.print("abc");
		System.out.println("aaaa");

		try {
			String m = request.getParameter("do");
			System.out.println(m);
			
			if(m != null)
			if (m.equals("searchData")) {
				String chooseCourse = request.getParameter("chooseCourse");
				String chooseMethon = request.getParameter("chooseMethon");
				String search = request.getParameter("search");
				DBcon dbCon = new DBcon(); 
				ArrayList<TestData> testData = new ArrayList<>();
				session.setAttribute("chooseCourse", chooseCourse);
				System.out.println(chooseMethon);
				
				if(chooseMethon.equals("id")) {
					String course_sql = "SELECT * FROM Message where id = "+ search + ";";	

					List<Question> contents = new ArrayList<>();
					Question q = new Question();
					q.setDescription(DbProxy.getData(chooseCourse.toLowerCase(), course_sql,"content"));
					q.setId(Integer.parseInt(DbProxy.getData(chooseCourse.toLowerCase(), course_sql,"id")));
					contents.add(q);
				    testData = dbCon.getTestDatas(chooseCourse.toLowerCase(), Integer.parseInt(search));
	
					System.out.println(contents.size());
					System.out.println(testData.size());

					session.setAttribute("question", contents);
					session.setAttribute("testData", testData);
					
				}else if(chooseMethon.equals("content")) {
					List<Question> question = dbCon.searchQuestion(chooseCourse, search);
					List<Integer> hwId = new ArrayList<>();
					
					int num = -1;
					for(int i = 0;i< question.size();i++) {
						num = question.get(i).getId();
						if(!hwId.contains(num)) {
	
							hwId.add(num);
							testData = dbCon.getTestDatas(chooseCourse.toLowerCase(),num,testData);
						}	
					}	
					System.out.println(question.size());
					System.out.println(testData.size());
					session.setAttribute("question", question);
					session.setAttribute("testData", testData);
				}
				
				
			
//				request.setAttribute("content", contents);
//				request.getRequestDispatcher("HistoryQuetions.jsp").forward(request, response);
//				response.sendRedirect("HistoryQuetions.jsp");


				
//				dbCon.insertTestData(tr.getCourse(), questionID, input_data, true_result);
//				dbCon.deleteTestData(tr.getCourse(), testDataID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
