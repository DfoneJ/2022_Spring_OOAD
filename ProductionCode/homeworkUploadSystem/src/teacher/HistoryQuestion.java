package teacher;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ExamDB.DbProxy;
import bean.DBcon;
import bean.TestData;
import componment.Teacher;

/**
 * Servlet implementation class HistoryQuetion
 */
@WebServlet("/HistoryQuestion")
public class HistoryQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		
		try {	
		String oldQuestionID = request.getParameter("testInfor"); //取舊題目ID
		if(oldQuestionID == null) {
			response.sendRedirect("THomeworkBoard");
		}else {
			System.out.println("*8*8");
			String newQuestionID = request.getParameter("hwId");
			String Course = (String) session.getAttribute("chooseCourse");//取得選取的課程名
			session.removeAttribute("chooseCourse");
			String sql_get = "SELECT * FROM Message where id = "+ oldQuestionID + ";";	
			DBcon dbcon = new DBcon();
			ArrayList<TestData> testData = 
					dbcon.getTestDatas(Course, Integer.parseInt(oldQuestionID));//取得舊題目之測資
			
			String content = DbProxy.getData(Course, sql_get,"content");//取得舊題目內容
		
			String sql_set = "UPDATE Message SET content = '"+ content +"' WHERE type ='hw' and id = " + newQuestionID; 
			DbProxy.setData(tr.getCourse(),sql_set);//將選取舊題目內容覆蓋新題目上
		
			String[] hwId_buf = request.getParameterValues(oldQuestionID); //get checkbox
			ArrayList<Integer> hwId = new ArrayList<>();
		
			for(int i = 0; i < hwId_buf.length; i++) {
				hwId.add(Integer.parseInt(hwId_buf[i])); //為方便使用API將hwid轉為Arraylist
			}	
			for(int i = 0; i < testData.size(); i++) { //若所有測資中的ID與勾選ID相同則加入
				if(hwId.contains(testData.get(i).getId())) { 
					dbcon.insertTestData(tr.getCourse(), Integer.parseInt(newQuestionID), testData.get(i).getInputData(), testData.get(i).getExpectedOutput());
				}
			}
			response.sendRedirect("showHomework?hwId="+ newQuestionID);
		}
		}catch(Exception e) {
		  e.printStackTrace();
	    }
		
	}
}
