package student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ExamDB.DbProxy;
import componment.Student;

/**
 * Servlet implementation class errorReport
 */
@WebServlet("/errorReport")
public class errorReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public errorReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
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
		
		String sql = "SELECT title FROM homework where title =" + request.getParameter("questionID")+";";
		String HWID = DbProxy.getData(st.getDbName(), sql, "title");
		String path = request.getServletContext().getRealPath("./") + "/WEB-INF/ProgramSet/" + st.getDbName() + File.separator
             	+ HWID + File.separator + st.getName() + File.separator;
		
		ArrayList<String> testID = new ArrayList<>();
		ArrayList<String> type = new ArrayList<>();			//錯誤類型
		ArrayList<String> numLine = new ArrayList<>();			//錯誤行數
		ArrayList<String> errorReport = new ArrayList<>();	//錯誤內容
		
		File file = new File(path);
		
		if(file.listFiles() != null) {
			int max = 1;
			for(String s : file.list()) {
				if(max < Integer.parseInt(s))
					max = Integer.parseInt(s);
			}				
			path = path + Integer.toString(max) + File.separator;
		
			//no read report
			if(!(new File(path + "report")).exists()) {
				
				request.getRequestDispatcher("/WEB-INF/student/Loading.jsp").forward(request, response);
//				response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
				return;
			}
			
			FileReader fr = new FileReader(path + "report");
			BufferedReader br = new BufferedReader(fr);
			
			
			String line = "";
			String content = "";
			boolean isContent = false;
			int seq = 0;
			while((line = br.readLine()) != null) {
				line = line.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

//				System.out.println(line);
				if(line.contains("#")) {
					if(isContent) {
						errorReport.add(content);
						seq = 0;
					}
					
					testID.add(line.substring(1, line.length()));
					content = "";
					isContent = false;
					seq++;
				}else if(seq >= 1){
					if(seq == 1) {
						type.add(line);
					}else if(seq == 2) {
						numLine.add(line);
					}else {
						content += line + "<br>";
						isContent = true;
					}
					seq++;
				}
			}
			//final report
			errorReport.add(content);
			
					
			
//			String line = "";
//			String statement = "";
//				
//			while((line = br.readLine()) != null) {
//				statement = line;
//				if(line.indexOf(":") > -1)
//					statement = line.substring(line.indexOf(":")+3, line.length()).replaceAll(" ", "&nbsp")
//					.replaceAll("\t", "&nbsp&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//				
//				
//				statements.add(statement);
//				if(line.indexOf("%") != -1) 
//					degree.add(Double.parseDouble(line.substring(0, line.indexOf("%"))));
//				else
//					degree.add(0.0);
//
//			}
		}
			
//		request.setAttribute("degree", degree);
//		request.setAttribute("statements", statements);
		
		request.setAttribute("testID", testID);
		request.setAttribute("errorMessage", errorReport);
		request.setAttribute("numLine", numLine);
		request.setAttribute("type", type);
		
		request.getRequestDispatcher("/WEB-INF/student/ErrorReport.jsp").forward(request, response);
	}

}
