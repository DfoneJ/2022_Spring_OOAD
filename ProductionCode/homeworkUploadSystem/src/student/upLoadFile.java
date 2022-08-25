package student;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.apache.log4j.Logger;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import javax.servlet.*;

import com.oreilly.servlet.MultipartRequest;

import ExamDB.DbProxy;
import bean.DBcon;
import bean.StudentMistackDao;
import bean.TestData;
import bean.TestResult;
import tool.*;
import componment.*;
import component.UploadFileHandler.*;

public class upLoadFile extends HttpServlet {

	private static final long serialVersionUID = -7853523789509342250L;
//	private Logger logger = Logger.getLogger(this.getClass());   
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {

		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(true);

		Student st = (Student) session.getAttribute("Student");
		if (st == null) {
			resp.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}

		String status = (String) session.getAttribute("hwUploadStatusMsg");
		req.setAttribute("status", status);
		req.getRequestDispatcher("/WEB-INF/student/UploadFile.jsp").forward(req, resp);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int status = 0;
		TestThread testThread = null;
		HttpSession session = req.getSession(true);
		Student st = (Student) session.getAttribute("Student");
		String hwId = (String) session.getAttribute("hwId");
		System.out.println(st.toString());
		System.out.println(hwId);

		Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
		if (st == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		
		Map<String, Object> returnObj = new HashMap<>();
		// 檢查作業有沒有上傳過
		if (Homework.isExistUserOneHomework(st.getDbName(), st.getName(), hwId)) {
			req.setAttribute("hwid", hwId);
			returnObj.put("success", false);
			returnObj.put("location", "UpLoadFileDuplicate");
			res.getWriter().write(gson.toJson(returnObj));
			return;
		}
		
		HomeworkQuestion q = HomeworkQuestion.getHomework(st.getDbName(), hwId);
		if (q != null && q.isExpired()) {
			// 錯誤報告sendDirect,檔案上傳有問題，要聯絡助教
			session.setAttribute("errorTitle", "超過繳交期限");
			session.setAttribute("errorContents", "超過繳交期限，請向助教要求延長繳交期限");
			System.out.println("錯誤報告sendRedirect2");
			// req.getRequestDispatcher("/WEB-INF/student/UploadFileError.jsp").forward(req,
			// res);
			returnObj.put("success", false);
			returnObj.put("location", "UploadFileError");
			res.getWriter().write(gson.toJson(returnObj));
			return;
		}
		
		// 初始化檔案儲存目錄 etc...
		int maxPostSize = 2 * 1024 * 1024;
		int sizeThreshold = 2 * 1024 * 1024; // 上傳檔案的buffer
		int fileSizeMax = 2 * 1024 * 1024; // 一個檔案的大小
		int sizeMax = 10 * 1024 * 1024; // 總共檔案的大小
		String name = st.getName();
		String FileName = "";
		String saveDirectory = req.getServletContext().getRealPath("./") + "/WEB-INF/uploadHW/" + hwId + "/";
		String cLibHookPath = req.getServletContext().getRealPath("./") + "/WEB-INF/hook/";
		System.out.println("hwId = " + hwId + "  studentId = " + name);
		System.out.println("Path = " + saveDirectory);
		System.out.println("Library = " + cLibHookPath + "/n");

		String FileDescription = "";
		String newFileName = name + "_" + hwId + "_" + FileName;
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
//		PrintWriter pw = res.getWriter();
//		MyUtil.printHtmlHead(pw);

		// ------- 從 input type = file 讀取檔案，上傳到 server's saveDirectory -----------

		if (hwId != null) {
			File directoryFile = new File(saveDirectory);
			if (!directoryFile.isDirectory()) {
				directoryFile.mkdirs();
			}

			// 儲存學生程式到本地端
			try {
				// define
				String PSpath = req.getServletContext().getRealPath("./") + "/WEB-INF/ProgramSet/" + st.getDbName()
						+ File.separator + hwId + File.separator + st.getName() + File.separator;
				String HWNameHead = name + "_" + hwId + "_";

				// 使用apache API處理檔案上傳
				ApacheFileUpload uploadFile = new ApacheFileUpload(req, sizeThreshold, fileSizeMax, sizeMax);
				// 使用前一定要先初始化
				uploadFile.init();
				// 執行寫入檔案
				if (!uploadFile.writeFile(PSpath, saveDirectory, HWNameHead, st.getDbName())) {
					// 錯誤報告sendDirect,檔案中有分號或是富檔名不正確
					session.setAttribute("errorTitle", "不正確檔名");
					session.setAttribute("errorContents", "請檢視檔名是否有特殊字符，<br>或副檔名不正確(副檔名請使用小寫)");
					System.out.println("錯誤報告sendRedirect1");
					// req.getRequestDispatcher("/WEB-INF/student/UploadFileError.jsp").forward(req,
					// res);

					returnObj.put("success", false);
					returnObj.put("location", "UploadFileError");
					res.getWriter().write(gson.toJson(returnObj));
					return;
				}
				System.out.println("uploadFile:" + uploadFile);
				// 將寫入完的相關資訊回caller
				FileDescription = uploadFile.getNumberOfFile();
				FileDescription = FileDescription.replaceFirst(",", "");
				FileName = uploadFile.getMainFuntionName();
				newFileName = uploadFile.getNewFileName();

				System.out.println("FileDescription:" + FileDescription);
				System.out.println("FileName:" + FileName);
				System.out.println("newFileName:" + newFileName);
			} catch (Exception ex) {
				// 錯誤報告sendDirect,檔案上傳有問題，要聯絡助教
				session.setAttribute("errorTitle", "檔案上傳失敗");
				session.setAttribute("errorContents", "系統發生錯誤，請與助教聯繫");
				System.out.println("錯誤報告sendRedirect2");
				// req.getRequestDispatcher("/WEB-INF/student/UploadFileError.jsp").forward(req,
				// res);
				returnObj.put("success", false);
				returnObj.put("location", "UploadFileError");
				res.getWriter().write(gson.toJson(returnObj));
				return;
			}

			DateBean dateBean = new DateBean();
			String dateTime = dateBean.getDateTime();
			System.out.println("FileName:" + FileName);
			if ((FileName != null)) {

				// 防止學生連點後重新寫入資料庫問題
				if (Homework.isExistUserOneHomework(st.getDbName(), st.getName(), hwId)) {
					req.setAttribute("hwid", hwId);
//					req.getRequestDispatcher("/WEB-INF/student/UpLoadFileDuplicate.jsp").forward(req, res);
					returnObj.put("success", false);
					returnObj.put("location", "UpLoadFileDuplicate");
					res.getWriter().write(gson.toJson(returnObj));
					return;
				}

				// 將使用者id,檔名,檔案描述,上傳日期,上傳路徑等..存到資料庫中
				// saveURL 似乎沒在用了 2020/1/14
				String saveURLDir = "<A HREF=http://140.124.3.101:8082/Java/" + hwId + "/" + newFileName + ">look</A>";
				Homework uhw = new Homework(name, dateTime, hwId, FileDescription, FileName, newFileName,
						saveURLDir);
				System.out.println("saveURLDir:" + saveURLDir);
				if (uhw.writeDB(st.getDbName())) {
					System.out.println("checkCode!");
					// 執行測試(執行緒)
					String tempDir = req.getServletContext().getRealPath("./") + "/WEB-INF/temp/";
					File temp = new File(tempDir);
					if (!temp.isDirectory())
						temp.mkdir();

					// 測試比對 這裡是上傳程式編譯用的測試執行緒
					testThread = new TestThread(saveDirectory, tempDir + hwId + "/", newFileName,
							Integer.parseInt(hwId), name, st.getDbName(), cLibHookPath, hwId, FileName);
					testThread.start();

					/* 開始進行抄襲檢查 */

					req.setAttribute("name", name);
					req.setAttribute("hwId", hwId);
					req.setAttribute("FileName", FileName);
					req.setAttribute("FileDescription", FileDescription);

					try {
						System.out.println("dbName=" + st.getDbName() + "\n");
						System.out.println("-----------------相似度分析開始--------------------");
						HomeworkQuestion hw = HomeworkQuestion.getHomework(st.getDbName(), hwId);
						int weights = hw.getSimilarity();
						System.out.println(hw.toString());
//						//壓縮.c作業成zip檔
						String zipFile = servlet.ExportStudentHW.export(req, res, 1, st.getDbName(), hwId);


						URL oracle = new URL("http://localhost:8080/copyTest/HttpDetect");

//						trustAllHosts();
//						HttpsURLConnection connection = (HttpsURLConnection)oracle.openConnection();
//						connection.setHostnameVerifier(DO_NOT_VERIFY);

//						System.out.println(oracle.getFile());
							
						HttpURLConnection connection = null;
						connection = (HttpURLConnection) oracle.openConnection();
						connection.setRequestMethod("POST");
						connection.setDoOutput(true);
						connection.setDoInput(true);

						String parameters = "zipFile=" + zipFile + "&weights=" + weights;
						System.out.println("parameters:" + parameters);
						DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
						wr.writeBytes(parameters);
						wr.flush();
						wr.close();
						
						
						
//						try {
							status = 200;
//						status = connection.getResponseCode();
//						}catch(java.io.IOException e ) {
//							System.out.println("connect to copyTest fail");
//						}
						// status = 200;
						System.out.println("------------------------Response = " + status);
//						System.out.println("------------------------URL = " + connection.getURL());
						
					
						
						String statusMsg = this.getStatusMsg(status);

						File path = new File(zipFile); // 刪除zip檔
						path.delete(); // 刪除zip檔e
						// req.getRequestDispatcher("/WEB-INF/student/UploadFile.jsp").forward(req,
						// res);
						returnObj.put("success", true);
						session.setAttribute("hwUploadStatusMsg", statusMsg);
						returnObj.put("location", "upLoadFile");
						req.setAttribute("status", statusMsg);

					} catch (Exception e) {
						returnObj.put("success", false);
						returnObj.put("errorMSg", "系統資料解析狀態錯誤，請重新上傳");
						returnObj.put("location", "UploadFileError");
						System.out.println("status Exception");
						e.printStackTrace();
					}
					
					/* 若不通過則刪掉上傳的檔案 */
				} else {
					System.out.println("231noFileException");
					returnObj.put("success", false);
					returnObj.put("errorMSg", "您沒有上傳檔案 請重新操作");
					returnObj.put("location", "UploadFileError");
				}
			}
			// no homework id
		} else {
			System.out.println("237noFileException");
			returnObj.put("success", false);
			returnObj.put("errorMSg", "您沒有上傳檔案 請重新操作");
			returnObj.put("location", "UploadFileError");
		}

		if (status == 303)
			returnObj.put("errorMSg", "作業判定抄襲 已刪除作業");

		res.getWriter().write(gson.toJson(returnObj));
		if (status == 303) {
			try {
				testThread.join(); // 等程式跑完測試，再刪除抄襲作業
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Homework.delUserOneHomework(st.getDbName(), st.getName(), hwId);
			System.out.println("---------------delete homework because of cheating-------------");

			try {
				int i = Integer.parseInt(hwId);
				String HWID = String.valueOf(i);

				DBcon db = new DBcon();
				StudentMistackDao smd = db.selectMistack(st.getDbName(), st.getName(), HWID);
				StudentMistackDao hwmd = db.selectMistack(st.getDbName(), null, HWID);

				// 當前資料庫裏面學生有錯誤資料, 將抄襲行為記錄起來
				if (smd != null) {
					smd.setType6(smd.getType6() + 1);
					db.updateMistake(st.getDbName(), st.getName(), HWID, smd);
				} else {
					smd = new StudentMistackDao(st.getName(), hwId, 0, 0, 0, 0, 0, 0, 1);
					db.insertMistake(st.getDbName(), st.getName(), HWID, smd);
				}

				// 當前資料庫裏面作業有錯誤資料, 將抄襲行為記錄起來
				if (hwmd != null) {
					hwmd.setType6(hwmd.getType6() + 1);
					db.updateMistake(st.getDbName(), null, HWID, hwmd);
				} else {
					hwmd = new StudentMistackDao(st.getName(), HWID, 0, 0, 0, 0, 0, 0, 1);
					db.insertMistake(st.getDbName(), null, HWID, hwmd);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		// =========================================================//

		DBcon dbcon = new DBcon();
		ArrayList<TestResult> testResult = null;
		ArrayList<TestData> testData;

		Map<Integer, String> testID_testData = new HashMap<Integer, String>();
		Map<Integer, Boolean> testID_result = new HashMap<Integer, Boolean>();

		boolean result = true;
		boolean flag = true;
		List<Integer> id = null;

		try {
			// 等程式跑完測試再進行除錯

			testThread.join();
			// 蒐集testID與測試結果
			testResult = dbcon.getTestResult(st.getDbName(), Integer.parseInt(hwId), st.getName());
			for (TestResult tr : testResult) {
				result = result & tr.isResult();
				testID_result.put(tr.getTest_data_id(), tr.isResult());
			}

			// 蒐集testID與測試資料
			testData = dbcon.getTestDatas(st.getDbName(), Integer.parseInt(hwId));
			for (TestData td : testData) {
				testID_testData.put(td.getId(), td.getInputData());
			}

			id = new ArrayList<>(testID_result.keySet());
			Collections.sort(id);

			if (testID_result.get(id.get(0)) && testID_result.get(id.get(1))) {
				flag = true;
			} else {
				flag = false;
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 如果學生程式中有錯誤的結果就執行除錯

//		if(true) {
		String ProgramPath = req.getServletContext().getRealPath("./") + "/WEB-INF/ProgramSet/" + st.getDbName()
				+ File.separator + hwId + File.separator + st.getName() + File.separator;
		// if there is any error in student's code, start debugging
		System.out.println("result:" + result + ";flag:" + flag);
		
		
		
		if (!result && flag) {

			// --------------debugging system----------------
			System.out.println("-----------start debugging--------------");
			File f = new File(ProgramPath);
			int max = 1;
			for (String s : f.list()) {
				if (max < Integer.parseInt(s))
					max = Integer.parseInt(s);
			}
			// Socket socket = new Socket("140.124.184.213",8080); //now

			// Socket socket = new Socket("140.124.184.214",8008); //now
			Socket socket = new Socket("140.124.181.37", 8080);// test
			// Socket dummySocket = new Socket("140.124.184.214",8080);
			connectToPDFS PDFS = new connectToPDFS(socket, ProgramPath + max, st.getDbName(), st.getName(), testResult);
			// connectToPDFS PDFS = new connectToPDFS(dummySocket, ProgramPath + max,
			// st.getDbName(), st.getName(), testResult);

			PDFS.sendMessage("Transfer Data Start");
			PDFS.sendMessage("#DBNAME=" + st.getDbName());
			PDFS.sendMessage("#HWID=" + hwId);
			PDFS.sendMessage("#STDID=" + st.getName());

			for (int testID : id) {
				PDFS.sendMessage(testID + " : " + testID_result.get(testID) + "=="
						+ testID_testData.get(testID).replaceAll("\n", "<p>"));
			}

			f = new File(ProgramPath + File.separator + max);
			for (String fileName : f.list()) {
				FileReader reader = new FileReader(ProgramPath + File.separator + max + File.separator + fileName);
				BufferedReader br = new BufferedReader(reader);

				String code = "";
				while (br.ready()) {
					code += br.readLine() + "<p>";
				}
				PDFS.sendMessage("#CODE=" + fileName + ";" + code);
			}

			PDFS.sendMessage("Close");
			PDFS.start();
		} else {
			System.out.println("----------------does not achieve debug condition---------------");
			// there's no requirement to generate the report
		
		}
		
	}

	private String getStatusMsg(int responseCode) {
		String statusMsg = "";
		if (responseCode == 200) {
			statusMsg = "上傳成功";
		} else if (responseCode == 303) {
			statusMsg = "抄襲";
		} else {
			statusMsg = "系統發生例外";
		}
		return statusMsg;
	}
	
	public void handinHomework(String courseName, String studentID, String questionID, String file) throws Exception {

	}
}

class connectToPDFS extends Thread { // PDFS-Programming Debugging System
	private Socket socket;
	private InputStream is;
	private PrintWriter out;

	private CountNumberOfMistakes cnom;
	private String path;

	public connectToPDFS(Socket socket, String path, String DBName, String StdName, ArrayList<TestResult> testResult)
			throws IOException {
		this.socket = socket;
		is = socket.getInputStream();
		out = new PrintWriter(socket.getOutputStream(), true);

		this.path = path;
		cnom = new CountNumberOfMistakes(path, DBName, StdName, testResult);
	}

	public void run() {
		System.out.println(path);
		byte b[] = new byte[1024000];

		try {
			Thread.currentThread().sleep(10000);
			// 讀取檔案
			System.out.println("read number : " + is.read(b, 0, b.length));

			// write
			FileOutputStream fos = new FileOutputStream(path + File.separator + "report");
			fos.write(b, 0, b.length);
			System.out.println("OK");

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				cnom.execution();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String data) throws IOException {
		out.println(data);
	}

}
