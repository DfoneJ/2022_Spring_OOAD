package student;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.*;

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
import javax.servlet.annotation.WebServlet;

import com.oreilly.servlet.MultipartRequest;

import ExamDB.DbProxy;
import ExamDB.HWID_RESERCH;
import bean.DBcon;
import bean.StudentMistackDao;
import bean.TestData;
import bean.TestResult;
import tool.*;
import componment.*;
import component.UploadFileHandler.*;

@WebServlet(name = "upLoadFile2", urlPatterns = { "/upLoadFile2" })
public class upLoadFile2 extends HttpServlet {

	private static final long serialVersionUID = -7853523789509342250L;
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		 
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int status = 0;
		TestThread testThread = null;
		HttpSession session = req.getSession(true);
		Student st = (Student) session.getAttribute("Student");
//		String hwId = (String) session.getAttribute("hwId");
		String hwId = "029";
		if (st == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}



		String name = st.getName();
		String FileName = "sss";
		
		String cLibHookPath = req.getServletContext().getRealPath("./") + "/WEB-INF/hook/";
		System.out.println("hwId = "+hwId+"  studentId = "+name);
		System.out.println("Library = "+cLibHookPath + "/n");	

		String FileDescription = "";
		
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter pw = res.getWriter();
		MyUtil.printHtmlHead(pw);
		
		
		String root = "/home/aaron/Desktop/108PD02/";
		//bye "021","022","026" "019","016",
		
		//"025","026","027","028","029","030","031","032","033","034","035","036","037","038","039"
		
		String[] hw = {"020","029","023","015","018"};
		File root_f = new File(root);
		
		//question id
		for(String r : hw) {
			hwId = r;
			String saveDirectory = req.getServletContext().getRealPath("./") + "/WEB-INF/uploadHW/" + hwId + "/";
			String newFileName = name + "_" + hwId + "_" + FileName;
			System.out.println("Path = "+saveDirectory);
			HWID_RESERCH.HWID = r;
			String ps = "/home/aaron/Desktop/108PD02/" + r + File.separator;
			File folder = new File(ps);
			int testError = 0 , allTestError = 0, programError = 0, program = 0, standardProgram = 0;
			//question id + std id
			System.out.println("question id + std id = "+ps);
		for(String foldername : folder.list()) {
//			name = foldername;
			
			System.out.println("hw+std+number: " + ps + foldername);
			File cfilefolder = new File(ps + foldername);
			//question id + std id + number
			if(cfilefolder.list() == null)
				continue;
			for(int i = 1; i <= cfilefolder.list().length; i++) {
				program ++;
				
				saveDirectory = ps + foldername + File.separator + i + File.separator;
				// question id + std id + number + c file
				File cfile = new File(saveDirectory);
				if(cfile.list().length == 0)
					continue;
				newFileName = cfile.list()[0] ;
				System.out.println("PP: "+saveDirectory + newFileName);
				Homework.delUserOneHomework(st.getDbName(), name, hwId);
			

			DateBean dateBean = new DateBean();
			String dateTime = dateBean.getDateTime();
			if ((FileName != null)) {
				//防止學生連點後重新寫入資料庫問題
//				if (UserHomework.isExistUserOneHomework(st.getDbName(), st.getName(), hwId)) {
//					req.setAttribute("hwid", hwId);
//					req.getRequestDispatcher("/WEB-INF/student/UpLoadFileDuplicate.jsp").forward(req, res);
//					return ;
//				}
				
				// 將使用者id,檔名,檔案描述,上傳日期,上傳路徑等..存到資料庫中
				// saveURL 似乎沒在用了 2020/1/14
				String saveURLDir = "<A HREF=http://140.124.3.101:8082/Java/" + hwId + "/" + newFileName + ">look</A>";
				Homework uhw = new Homework(name, dateTime, hwId, FileDescription, FileName, newFileName,
						saveURLDir);
				
				if (uhw.writeDB(st.getDbName())) {

					// 執行測試(執行緒)
					String tempDir = req.getServletContext().getRealPath("./") + "/WEB-INF/temp/";					
					File temp = new File(tempDir);
					if (!temp.isDirectory())
						temp.mkdir();

					// 測試比對 這裡是上傳程式編譯用的測試執行緒
					testThread = new TestThread(
							saveDirectory,
							tempDir + hwId + "/",
							newFileName,
							Integer.parseInt(hwId),
							name,
							st.getDbName(),
							cLibHookPath,
							hwId,
							FileName
						);
					testThread.start();
					
			
					/*  若不通過則刪掉上傳的檔案  */
				} else {
					pw.println("您沒有上傳檔案 請重新操作");
				}
			}
		
		//=========================================================//
		
		DBcon dbcon = new DBcon();
		ArrayList<TestResult> testResult = null;
		ArrayList<TestData> testData;
		
		Map<Integer, String> testID_testData = new HashMap<Integer, String>();
		Map<Integer, Boolean> testID_result = new HashMap<Integer, Boolean>();
		
		boolean result = true;
		boolean flag = true;
		List<Integer> id = null;
		int testError_r = 0;
		
		try {
			//等程式跑完測試再進行除錯
			testThread.join();
			
			//蒐集testID與測試結果
			testResult = dbcon.getTestResult(st.getDbName(), Integer.parseInt(hwId), st.getName());
			
			for(TestResult tr : testResult) {
				result = result & tr.isResult();
				testID_result.put(tr.getTest_data_id(), tr.isResult());
				if(!tr.isResult()) {
					allTestError++;
					testError_r++;
				}
					
			}
			
			//蒐集testID與測試資料
			testData = dbcon.getTestDatas(st.getDbName(), Integer.parseInt(hwId));
			for(TestData td : testData) {
				testID_testData.put(td.getId(), td.getInputData());
			}	
			
			id = new ArrayList<>(testID_result.keySet());
			Collections.sort(id);
			
			if(testID_result.get(id.get(0)) && testID_result.get(id.get(1))) {
				flag = true;
			}else {
				flag = false;
			}			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		if(result)
			programError ++;
			

		//如果學生程式中有錯誤的結果就執行除錯
//		if(!result) {
//		if(true) {
		if(!result && flag) {
			testError += testError_r;
			standardProgram ++;
			
			//--------------debugging system----------------
			System.out.println("-----------除錯開始--------------");
			
			
			Socket socket = new Socket("140.124.184.213",8080);
			connectToPDFS PDFS = new connectToPDFS(socket, saveDirectory, st.getDbName(), st.getName(), testResult);
			
			PDFS.sendMessage("Transfer Data Start");
			PDFS.sendMessage("#DBNAME=" + st.getDbName());
			PDFS.sendMessage("#HWID=" + hwId);
			PDFS.sendMessage("#STDID=" + st.getName());
			System.out.println(testID_result);
			if(testID_result == null || testID_result.size() < 7) {
				continue;
			}
			
			
			for(int testID : id) {
				PDFS.sendMessage( testID + " : " + testID_result.get(testID) + "==" + testID_testData.get(testID).replaceAll("\n", "<p>")) ;
			}
			
			System.out.println("#DBNAME=" + st.getDbName());
			System.out.println("#HWID=" + hwId);
			System.out.println("#STDID=" + st.getName());
			
			
			
				FileReader reader = new FileReader(saveDirectory + newFileName);
				BufferedReader br = new BufferedReader(reader);
				
				String code = "";
				while(br.ready()) {
					code += br.readLine() + "<p>";
				}
				PDFS.sendMessage("#CODE=" + newFileName + ";" + code);	
				
				System.out.println("#CODE=" + newFileName + ";" + code);
			
			
			PDFS.sendMessage("Close");
			PDFS.start();
		}else {
			System.out.println("----------------沒有滿足除錯條件---------------");
		}
		
		if(!result && flag) {
			System.out.println("wait");
			try {
				Thread.sleep(testError_r * 30100);
//				Thread.sleep(110000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
			
			
			
			}
		}
		
		//
		File report = new File(ps + "report");
		report.createNewFile();
		FileWriter fw = new FileWriter(ps + "report");
		fw.write("程式總數: " + program +"\n");
		fw.write("符合除錯標準程式數: " + standardProgram + "\n");
		fw.write("程式錯誤數: " + programError +"\n");
		fw.write("所有未通過測試數: " + allTestError + "\n");
		fw.write("未通過測試數: " + testError + "\n");
		fw.flush();
		fw.close();
		
		}
		
		
		System.out.println("END");
	}
}



