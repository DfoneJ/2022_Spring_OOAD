package teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.javafx.collections.MappingChange.Map;

import ExamDB.DbProxy;
import componment.Teacher;

/**
 * Servlet implementation class SelectQuentionsFromBank
 */
@WebServlet("/SelectQuestionsFromBank")
public class SelectQuestionsFromBank extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectQuestionsFromBank() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
	
		String sql = "SELECT * FROM classification;";
		ArrayList<String[]> topic= DbProxy.selectData("programset", sql);
		request.setAttribute("topic", topic);
		
		sql = "SELECT id, title FROM question;";
		ArrayList<String[]> questions= DbProxy.selectData("programset", sql);
		request.setAttribute("questions", questions);
		
		request.getRequestDispatcher("/WEB-INF/teacher/SelectQuestionsFromBank.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		
/*-----------------------------------------------------------------------*/	

		
		String[] t = request.getParameterValues("type");   // checkbox
		String questions = request.getParameter("questions");    //select
		String choose = request.getParameter("choose");
		ArrayList<String[]> data = new ArrayList<String[]>();
		ArrayList<String[]> allData = new ArrayList<String[]>();
		HashMap<String, String> map = new HashMap<>();
		String sql = "";
			
		//this is send the history Question to addHomework
		if(choose != null) {
			//某個題庫的所有資訊包含 ID title contents tags
			sql = "SELECT * FROM question where id ='" + choose +"';" ;
			allData = DbProxy.selectData("programset", sql);
			String[] showQuestion = allData.get(0);
			showQuestion[2] = showQuestion[2].replaceAll("<BR>", "");
			request.setAttribute("data", showQuestion);
			
			//目前所提供的分類種類
			sql = "SELECT * FROM classification;";
			ArrayList<String[]> topic= DbProxy.selectData("programset", sql);
			request.setAttribute("topic", topic);

			request.getRequestDispatcher("/WEB-INF/teacher/AddHomeworkFrame.jsp").forward(request, response);
			return;
		}
		
		if(t == null && questions.equals("0")) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		
		// map classification id and topic name
		sql = "SELECT id, topic FROM classification;";	
		allData = DbProxy.selectData("programset", sql);
		for(int i = 0; i < allData.size(); i++) {
			map.put(allData.get(i)[0], allData.get(i)[1]);
		}
			
		if(t != null) {  //checkbox

			//get all information of questions
			sql = "SELECT id, title, content, classification_id FROM question;";
			allData = DbProxy.selectData("programset", sql);
			ArrayList<String> type = new ArrayList<String>();   
			for(String s:t) {
				type.add(s);
			}
			boolean flag = false;
			for(int i = 0; i < allData.size(); i++) {
				String[] S = allData.get(i)[3].split(",");      // split classificationID kind of one up!
				//接下來要將切好的分類編號跟從前端收到要查詢的分類號做比對 有覆蓋道就是代表要選取
				flag = false;
				for(String s:S) {
					if(type.contains(s)) {
						flag = true;
					}
				}		
				if(flag)
					data.add(allData.get(i));
			}
			
			// change typename of data from id of data
			for(int i = 0; i < data.size(); i++) {
				String[] s = data.get(i)[3].split(",");
				String typeName = "";
				for(int j = 0; j < s.length; j++) {
					typeName = typeName + map.get(s[j]) +"<br>";	
				}
				data.get(i)[3] = typeName;
			}
			
		}else if(!questions.equals("0")) { //select
			sql = "SELECT id, title, content, classification_id FROM question where id = '"+ questions + "';";
			data = DbProxy.selectData("programset", sql);
			
			String[] s = data.get(0)[3].split(",");
			String typeName = "";
			for(int j = 0; j < s.length; j++) {
				typeName = typeName + map.get(s[j]) +"<br>";	
			}
			data.get(0)[3] = typeName;
		}		
		
		sql = "SELECT * FROM classification;";
		ArrayList<String[]> topic= DbProxy.selectData("programset", sql);
		request.setAttribute("topic", topic);
		
		sql = "SELECT id, title FROM question;";
		ArrayList<String[]> question= DbProxy.selectData("programset", sql);
		System.out.println(data.size());
		request.setAttribute("questions", question);
		request.setAttribute("data", data);
		request.getRequestDispatcher("/WEB-INF/teacher/SelectQuestionsFromBank.jsp").forward(request, response);
	}

}
