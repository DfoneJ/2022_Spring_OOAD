package teacher;

import componment.*;

//�W�[�@�~
import java.io.*;
import java.util.ArrayList;

import javax.servlet.http.*;

import ExamDB.DbProxy;

import javax.servlet.*;

public class AddHomework extends HttpServlet {

	private static final long serialVersionUID = 6026985782730539599L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
	
		String sql = "SELECT * FROM classification;";
		ArrayList<String[]> topic= DbProxy.selectData("programset", sql);
		req.setAttribute("topic", topic);
		
		req.getRequestDispatcher("/WEB-INF/teacher/AddHomeworkFrame.jsp").forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//要做修改題庫 新增題庫 不動的操作
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		String type = req.getParameter("type");
		String hwId = req.getParameter("hwId");
		String content = req.getParameter("content");
		String deadline = req.getParameter("deadline");
		String weights = req.getParameter("weights");
		String language = req.getParameter("language");
		String sandboxPath = "/home/";
		String outputPath = req.getServletContext().getRealPath("./") + "/WEB-INF/temp/" + hwId;	
		
		System.out.println(language);
		int index = 0;
		String srh = "\n";
		
		File temp = new File(outputPath);
		if (!temp.isDirectory()) {
			temp.mkdir();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		while ((index = content.indexOf(srh)) != -1) {
			content = content.substring(0, index) + " <BR> " + content.substring(index + srh.length());
		}
		if (ActiveHomeworkStateInfo.isColumnInDatabase(tr.getDbName()))
			HomeworkQuestion.addHomework(tr.getDbName(), hwId, type, content, deadline, weights,language,"0","0");
		else
			HomeworkQuestion.addHomework(tr.getDbName(), hwId, type, content, deadline, weights,language,"0");
		
		//create docker when adding homework ,
		//docker name is database name + hwid
		String createSandbox[] = {"/bin/sh","-c","docker run --runtime=runsc --name "+tr.getDbName() + hwId+" --restart=always -d -i --memory 250MB -v "+outputPath+":"+sandboxPath+" ubuntu /bin/bash"};
		System.out.println(createSandbox[0] + createSandbox[1] + createSandbox[2]);
		
		
		try {
			Process process = Runtime.getRuntime().exec(createSandbox);
			process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 以上出題完成，並啟動docker，
		// 接著判斷是否要新增到題庫 2020/02/03 - new function
		
		String option = req.getParameter("option");
		// 非只出一次的題目
		if(!option.equals("New")) {
			String QID = req.getParameter("QID");
			String title = req.getParameter("title");
			String[] t = req.getParameterValues("topic");
			String topic = t[0];
			// 將radio接收到的number用都點間隔開來
			for(int i = 1; i < t.length; i++) {
				topic = topic + "," + t[i];
			}
			
			// 聚集題庫相關資料
			String[] data = new String[3];
			data[0] = title; data[1] = content; data[2] = topic;
			
			if(option.equals("Modification")) {
				if(!QID.isEmpty()) {
					String sql = "UPDATE question SET title = ? , content = ? , classification_id = ? "
							   + "WHERE id = '"+ QID +"';";
					DbProxy.setData("programset", sql, data);
				}
			}else if(option.equals("Addition")) {
				String sql = "INSERT INTO question (title, content, classification_id) values (?, ?, ?);";
				DbProxy.addData("programset", sql, data);
			}
		}

		res.sendRedirect("THomeworkBoard");
	}
}