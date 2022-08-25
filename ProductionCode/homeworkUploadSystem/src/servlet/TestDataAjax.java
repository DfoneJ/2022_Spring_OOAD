package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.*;

import ExamDB.DbProxy;

import javax.servlet.*;

import bean.DBcon;
import bean.modificationNote;
import bean.reTestResult;
import componment.Course;
import componment.Teacher;
import tool.Language;
import tool.testResult.CompilationConfig;
import tool.testResult.TestResult;

/**
 * Servlet implementation class TestDataAjax
 */
class Worker extends Thread {
	final Process process;
	Integer exit;

	Worker(Process process) {
		this.process = process;
	}

	public void run() {
		try {
			exit = new Integer(process.waitFor());
		} catch (InterruptedException ignore) {
			return;
		}
	}
}


//
public class TestDataAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBcon db = new DBcon();
	//這裡是重新編譯用的測試執行續
	private void run(Process process, String true_result ,int testID, String dbName, String[] studentID) throws Exception {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		sb2.append(true_result);
		sb2.append("\n");
		System.out.println(studentID[0]);
		Worker worker = new Worker(process);
		worker.start();
		try {
			worker.join(8000);
			if (worker.exit != null) {
				InputStreamReader isr = new InputStreamReader(process.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null)
					sb.append(line + "\n");

				while (sb.indexOf(" \n") >= 0) {
					int s = sb.indexOf(" \n");
					sb.delete(s, s + 1);
				}
				while (sb.indexOf("\n\n") >= 0) {
					int s = sb.indexOf("\n\n");
					sb.delete(s, s + 1);
				}
				if (sb.length() > 0) {
					while (sb.substring(0, 1).equals("\n")) {
						sb.delete(0, 1);
					}
				}
				while (sb.indexOf(" \n") >= 0) {
					int s = sb.indexOf(" \n");
					sb.delete(s, s + 1);
				}
				while (sb2.indexOf(" \n") >= 0) {
					int s = sb2.indexOf(" \n");
					sb2.delete(s, s + 1);
				}
				while (sb2.indexOf("\n\n") >= 0) {
					int s = sb2.indexOf("\n\n");
					sb2.delete(s, s + 1);
				}
				if (sb2.length() > 0) {
					while (sb2.substring(0, 1).equals("\n")) {
						sb2.delete(0, 1);
					}
				}
				while (sb2.indexOf(" \n") >= 0) {
					int s = sb2.indexOf(" \n");
					sb2.delete(s, s + 1);
				}
				System.out.println("Excute Resutlt:\n" + sb.toString());
				System.out.println("True Result:\n" + sb2.toString());
	//
//				
				if (sb.toString().equals(sb2.toString()))
					db.updateCheckResult(dbName, testID, true, "通過測試", studentID[0]);
				else
					db.updateCheckResult(dbName, testID, false, "測試失敗", studentID[0]);
				
			} else {
				db.updateCheckResult(dbName, testID, false, "執行超過時間(8秒)", studentID[0]);
				throw new TimeoutException();
			}
		} catch (InterruptedException ex) {
			worker.interrupt();
			Thread.currentThread().interrupt();
			throw ex;
		} finally {
			modificationNote.table.add(studentID);
			process.destroy();
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestDataAjax() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GET");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			response.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		
		Course course = (Course) session.getAttribute("Course"); // --OOAD FINAL
		if(course == null) {
			System.out.println("NULL course");
		} else {
			System.out.println(course.getName());
		} // OOAD FINAL--
		
		DBcon dbCon = new DBcon();
		
		try {
			String m = request.getParameter("m");
			if (m.equals("addTestData")) {
				String homework_number = request.getParameter("questionID"); // OOAD FINAL
				int questionID = Integer.parseInt(homework_number);
				String input_data = request.getParameter("input_data");
				String true_result = request.getParameter("true_result");
				System.out.println(input_data);
				System.out.println(true_result);
				dbCon.insertTestData(tr.getCourse(), questionID, input_data, true_result);
				course.addTestData(homework_number, input_data, true_result); // OOAD FINAL
			}
			
			else if (m.equals("deleteTestData")) {
				String homework_number = request.getParameter("questionID"); // OOAD FINAL
				String testData_number = request.getParameter("testDataID"); // OOAD FINAL
				int testDataID = Integer.parseInt(testData_number);
				dbCon.deleteTestData(tr.getCourse(), testDataID);
				course.deleteTestData(homework_number, testData_number);// OOAD FINAL
			}
			
			else if (m.equals("updateInputData")) {
				int ID = Integer.parseInt(request.getParameter("testID"));
				String input_data = request.getParameter("input_data");
				String title = request.getParameter("title");
				String sql = "SELECT true_result FROM test_data where id = " + ID + ";";
				dbCon.updateInputData(tr.getCourse(), ID, input_data);
				
				if(request.getParameter("recompile").equals("1")) {
					DBcon db = new DBcon();
					List<reTestResult> result = db.runProgramAfterModification(tr.getDbName(), request.getParameter("testID"), title);
					String true_result = DbProxy.getData(tr.getDbName(), sql, "true_result");
					String [] language = null;
					Process process = null;

					String executionName = "";
					String outputPath = request.getServletContext().getRealPath("./") + "/WEB-INF/temp/" + title + "/";
					

					for(int i = 0;i < result.size();i++) {
						String studentID[] = new String[2];
						executionName = result.get(i).getExecutionName();
						studentID[0] = result.get(i).getStudentID();
						studentID[1] = title;
						language = executionName.split("\\.");
						
						CompilationConfig data = new CompilationConfig(studentID, language, null, input_data, outputPath, "", true_result, "", tr.getDbName());			
						Language choose = new Language(language[language.length - 1]);
						TestResult test = choose.setBuildConfig(data);
						
						process = test.runTestResult();
						run(process, true_result, ID, tr.getDbName(), studentID);
					}
				}
						
			}else if(m.equals("updateTureResult")) {

				int ID = Integer.parseInt(request.getParameter("testID"));
				String true_result = request.getParameter("true_result");
				String title = request.getParameter("title");
				String sql = "SELECT input_data FROM test_data where id = " + ID + ";";
				dbCon.updateTrueResult(tr.getCourse(), ID, true_result);
				
				if(request.getParameter("recompile").equals("1")) {
					DBcon db = new DBcon();
					List<reTestResult> result = db.runProgramAfterModification(tr.getDbName(), request.getParameter("testID"), title);
					String input_data = DbProxy.getData(tr.getDbName(), sql, "input_data");
					String [] language = null;
					Process process = null;

					String executionName = "";
					String outputPath = request.getServletContext().getRealPath("./") + "/WEB-INF/temp/" + title + "/";
					

					for(int i = 0;i < result.size();i++) {
						String studentID[] = new String[2];
						executionName = result.get(i).getExecutionName();
						studentID[0] = result.get(i).getStudentID();
						studentID[1] = title;
						language = executionName.split("\\.");
						
						CompilationConfig data = new CompilationConfig(studentID, language, null, input_data, outputPath, "", true_result, "", tr.getDbName());			
						Language choose = new Language(language[language.length - 1]);
						TestResult test = choose.setBuildConfig(data);
						
						process = test.runTestResult();
						run(process, true_result, ID, tr.getDbName(), studentID);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
