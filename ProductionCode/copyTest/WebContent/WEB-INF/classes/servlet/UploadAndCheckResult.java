package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import WebGUI.*;
import tool.CompressTool;

/**
 * Servlet implementation class UploadAndCheckResult
 */
public class UploadAndCheckResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadAndCheckResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("GET");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        Map parameterMap = new TreeMap<String, String>();
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        System.out.println("isMultipart=" + isMultipart + "<br>");

        // Create a factory for disk-based file items
        FileItemFactory factory = new DiskFileItemFactory();

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        List items;
        String[][] result = null;
        try {
            items = upload.parseRequest(request);
            // Process the uploaded items
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("BIG5");
                    System.out.println(name + "=" + value);
                    parameterMap.put(name, value);
                } else {
                    String fileName = item.getName();
                    if (fileName != null && !fileName.equals("")) {
                    	//上傳存檔
                    	File uploadedFile = new File("D:/c/Decompression/"+fileName);
                        System.out.println(uploadedFile.getAbsolutePath());
                        item.write(uploadedFile);
                        //解壓縮 解完刪除壓縮檔
                        CompressTool compressTool = new CompressTool();
                        String dirName = "temp"+(new Date()).getTime();
                        System.out.println(compressTool.decompressFile(uploadedFile.getAbsolutePath(), "D:/c/Decompression/"+dirName+"/"));
                        uploadedFile.delete();
                        //測試解壓完的檔案
                    	SettingInformation settingInformation = new SettingInformation();
                    	settingInformation.setPath("D:/c/Decompression/"+dirName+"/");
                    	PlagiarismDetection plagiarismDetection = new CAndCppPlagiarismDetection(settingInformation);
                    	result = plagiarismDetection.execute();
                    	//刪除測試完的檔案
                    	File path = new File("D:/c/Decompression/"+dirName+"/");
                        File[] files = path.listFiles();
                        for (int i = 0; i < files.length; i++) {
                        	files[i].delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("resultArray", result);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ShowTestResult.jsp");
        dispatcher.forward(request,response);
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
