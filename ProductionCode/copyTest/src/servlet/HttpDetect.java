package servlet;

import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import WebGUI.CAndCppPlagiarismDetection;
import WebGUI.PlagiarismDetection;
import WebGUI.SettingInformation;
import tool.CompressTool;

import javax.servlet.*;
import java.io.*;
import java.util.Date;
/**
 * Servlet implementation class UploadAndCheckResult
 */
public class HttpDetect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("GET");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String zipFile = request.getParameter("zipFile");
		String weights = request.getParameter("weights");
		
		//String tempDirPath = request.getRealPath("/")+"/WEB-INF/temp/";
		String tempDirPath = request.getServletContext().getRealPath("./") + "/WEB-INF/temp/";
		
		// Parse the request
		if (!new File(tempDirPath).exists()) {
			new File(tempDirPath).mkdirs();
		}

		String[][] result = null;
		try {

			System.out.println("1");
			// 解壓縮 解完刪除壓縮檔
			CompressTool compressTool = new CompressTool();
			String dirName = "temp" + (new Date()).getTime();
			System.out.println(compressTool.decompressFile(zipFile, tempDirPath + dirName + "\\"));
			// System.out.println(compressTool.decompressFile(uploadedFile.getAbsolutePath(),
			// tempDirPath+"out\\"));
			System.out.println("tempDirPath:" + tempDirPath );
			System.out.println("DirPath:" + dirName + "\\");
			System.out.println("2");
			// 測試解壓完的檔案
			SettingInformation settingInformation = new SettingInformation();
			settingInformation.setPath(tempDirPath + dirName + "\\");
			PlagiarismDetection plagiarismDetection = new CAndCppPlagiarismDetection(settingInformation);
			result = plagiarismDetection.execute();
			System.out.println("3");
			// 刪除測試完的檔案
			File path = new File(tempDirPath + dirName + "\\");
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
			path.delete();
			System.out.println("4");

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		}
		File path = new File(tempDirPath);
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
		int ctl = 0;
		response.setStatus(HttpServletResponse.SC_OK);
		for(String[] row : result) {
		    if(Float.parseFloat(result[ctl][2]) >= Float.parseFloat(weights)) {
		    	System.out.println("fail "+weights+"     "+Float.parseFloat(result[ctl][2]));
		    	response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		    	break;
		    }
		    ctl++;
		}		
		
	}

	public void deleteAll(File path) {
		if (!path.exists()) {
			return;
		}
		if (path.isFile()) {
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteAll(files[i]);
		}
		path.delete();

	}

}
