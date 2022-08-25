// ============================== Annotation Since 2022/03/10 ==============================
package student;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import componment.*;

public class HomeworkBoard extends HttpServlet {

	private static final long serialVersionUID = -6828757103170180760L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		Student st = (Student) session.getAttribute("Student");
		if (st == null && tr == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		User user = User.getUser(tr, st);
		ArrayList<?> hws;
		HomeworkQuestion hw;
		String hwType = req.getParameter("hwType");
		if (hwType == null) {
			hwType = "課後作業";
		}

		hws = HomeworkQuestion.getAllHomeworks(user.getDbName(), hwType);
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter pw = res.getWriter();
		MyUtil.printHtmlHead(pw);
		ArrayList<?> uhws = Homework.getUserHomework(user.getDbName(), user.getName());
		pw.println(" <a href=HomeworkBoard?hwType=" + "課後作業" + "> 課後作業 </a> ");
		pw.println(" <a href=HomeworkBoard?hwType=" + "隨堂練習" + "> 隨堂練習</a> ");
		pw.println(" <a href=HomeworkBoard?hwType=" + "實習練習" + "> 實習練習 </a>  請注意繳交期限");
		pw.println("<TABLE BORDER=1>");
		pw.println("<TR> <TD>編號</TD>  <TD>種類</TD> <TD>題號</TD> <TD>繳交期限</TD> <TD>繳交</TD><TD>語言</TD><TD>備註</TD><TD>測試</TD></TR>");
				//
		//System.out.println(hws.size());
				//
		boolean isActiveFieldExist = ActiveHomeworkStateInfo.isColumnInDatabase(user.getDbName());
		for (int i = 1; i <= hws.size(); i++) {
			hw = (HomeworkQuestion) hws.get(i - 1);
			
			if ((isActiveFieldExist == true) && (ActiveHomeworkStateInfo.isHWActive(user.getDbName(), hw.getId()) == false))
				continue;
			pw.println("<TR>");
			pw.println("<TD>" + i + "</TD><TD>" + hw.getType() + "</TD>");
			
			pw.println("<TD> <a href=showHomework?hwId=" + hw.getId() + ">" + hw.getId() + "</a></TD>");
			if (hw.isExpired()) {
				pw.println("<TD>" + hw.getDeadline() + "</TD>");
				pw.println("<TD> 期限已過 </TD>");
			} else {
				pw.println("<TD>" + hw.getDeadline() + "</TD>");
				
				if(UpLoadLockInfo.isEnable(user.getDbName(), hw.getId()))
					pw.println("<TD> <a href=upLoadHw?hwId=" + hw.getId() + ">  繳交 </a> </TD>");
				else
					pw.println("<TD>準備中</TD>");
			}
			pw.println("<TD>" + hw.getlanguage() + "</TD>");
			if (st != null){
				if (Homework.isExistUserOneHomework(uhws, hw.getId())) {
					pw.println("<TD><font color=#FF0000>  已繳 </font></TD>");
					pw.println("<TD><a href='CheckResult.jsp?questionID=" + hw.getId() + "&studentID=" + st.getName()
							+ "' target='_blank'>查看結果</a></TD>");
					pw.println("<TD><a href='success.jsp?HW_ID=" + hw.getId() + "' target='_blank'>通過測試名單</a></TD>");
				} else {
					pw.println("<TD>  未繳 </TD><TD>-</TD>");
					pw.println("<TD><a href='success.jsp?HW_ID=" + hw.getId() + "' target='_blank'>通過測試名單</a></TD>");
				}
			}
			pw.println("</TR>");
		}
		pw.println("</TABLE>");
		
		String[] v = { "課後作業" };
		String t[]= {"C","Java","Python","C#"};

		pw.println("</body></html>");
		pw.close();
	}
}