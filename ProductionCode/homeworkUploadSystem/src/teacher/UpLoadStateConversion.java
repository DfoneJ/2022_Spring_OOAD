package teacher;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ExamDB.DbProxy;
import componment.Teacher;
import componment.User;

public class UpLoadStateConversion extends HttpServlet {
	private static final long serialVersionUID = 854611L;

    public UpLoadStateConversion() {
        super();
    }


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		
			HttpSession session = request.getSession(true);
			Teacher tr = (Teacher) session.getAttribute("Teacher");
			if (tr == null) {
				response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
				return;
			}
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");

			String hwID = request.getParameter("hwID");
			User user = User.getUser(tr, null);
				
			String sql = "SELECT * FROM Message where id = "+ hwID + ";";		
			String enable = DbProxy.getData(user.getDbName(), sql,"enable");	
//			System.out.println(enable);
	
			if(enable.equals("0")) 
				enable = "1";	
			else if(enable.equals("1")) 
				enable = "0";
		
			sql = "UPDATE Message SET enable = "+ enable +" WHERE type ='hw' and id = " + hwID; 
			DbProxy.setData(user.getDbName(), sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("THomeworkBoard");
	}

}
