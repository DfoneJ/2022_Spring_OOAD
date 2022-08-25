package teacher;

import componment.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;
import javax.servlet.http.*;

import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.InputStream; 

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.*;
import com.oreilly.servlet.MultipartRequest;


public class addUserFromFile extends HttpServlet {

	private static final long serialVersionUID = -7194830234593297898L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter pw = res.getWriter();
		MyUtil.printTeacherHead(pw);
		// pw.println("<form action=addUser method=post name=form1>");
		pw.println("<form name=Form1 enctype=\"multipart/form-data\" method=post action=addUserFromFile>");
		pw.println("<p><h2>Add User</h2></p>");
		// pw.println("<INPUT type=radio name=addType value=0 checked> 個人" );
		// pw.println("<p>User Name:<input type=text name=id size=20></p>");
		// pw.println("<p>Passwd:<input type=text name=pass size=20></p>");
		// pw.println("<p>realName:<input type=text name=realName
		// size=20></p>");
		pw.println("<INPUT type=radio name=addType value=1 > 從文字檔<br>");
		pw.println("<INPUT type=radio name=addType value=2 checked> 從Excel檔<br>");
		pw.println("上傳檔案: <input type=file name=nameFile size=20 maxlength=20><br><br>");
		
		pw.println("Excel範例檔 : <a href=\"addUserSample\"> sample.xlsx </a><br><br>");
		pw.println("<input type=submit name=submit value=\"Ok\" >");
		// pw.println("<input type=submit name=submit value=\"Ok\"
		// onClick=\"return checkModify(form1.id.value);\">");
		pw.println("<input type=reset name=reset value=\"reset\">");
		pw.println("</form>");
		
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(true);
		Teacher tr = (Teacher) session.getAttribute("Teacher");
		if (tr == null) {
			res.sendRedirect("http://www.cc.ntut.edu.tw/~jykuo/");
			return;
		}
		String saveDirectory = req.getRealPath("/") + "/WEB-INF/temp/";
		int maxPostSize = 2 * 1024 * 1024;
		File directoryFile = new File(saveDirectory);
		if (!directoryFile.isDirectory()) {
			directoryFile.mkdir();
		}
		MultipartRequest multi = new MultipartRequest(req, saveDirectory, maxPostSize);
		String c = multi.getParameter("addType");
		if(c.equals("1")) {
			//multi.getParameter("addType");
			PrintWriter pw = res.getWriter();
			MyUtil.printTeacherHead(pw);
			String FileName = multi.getFilesystemName("nameFile");
			String[][] temp = LoadFile.getData(saveDirectory + FileName);
			
			for (int i = 0; i < temp.length; i++) {
				if (Student.addUser(tr.getDbName(), temp[i][0], temp[i][1], "0")) {
					pw.println("Add User " + temp[i][0] + " Success");
				} else {
					pw.println("<h3>  Users " + temp[i][0] + " already exist ! </h3>");
				}
			}
			pw.println("Add User File Success: " + saveDirectory + FileName);
			pw.println("</body></html>");
			pw.close();
		}else if(c.equals("2")) {
			PrintWriter pw = res.getWriter();
			MyUtil.printTeacherHead(pw);
			String FileName = multi.getFilesystemName("nameFile");
			File file =  multi.getFile("nameFile");
			
			String FN[] = FileName.split("[.]");
			if(file.isFile() && file.exists())
			if(FN[1].equals("xlsx")) {
				XSSFWorkbook xssf;
				try {
					xssf = new XSSFWorkbook(file);
					XSSFSheet sheet = xssf.getSheetAt(0);
					XSSFRow row = null;
					XSSFCell IDcell ,NameCell;
					int ID=0,Name=0;
					DataFormatter df = new DataFormatter();
	
					for(int i = sheet.getFirstRowNum();i < sheet.getPhysicalNumberOfRows();i++) {
						row = sheet.getRow(i);					
						if(i == 0) {
							System.out.println("inner " + row.getPhysicalNumberOfCells());
							for(int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells();j++) {
								if(row.getCell(j).toString().equals("學號")) 
									ID = j;
								else if(row.getCell(j).toString().equals("姓名")) 
									Name = j;
							}
						}else {
							IDcell = row.getCell(ID);
							NameCell = row.getCell(Name);
							System.out.println(df.formatCellValue(IDcell) + "\t" + df.formatCellValue(NameCell));
							
							if(df.formatCellValue(IDcell).equals("") || df.formatCellValue(NameCell).equals("")){
								System.out.println("學號或姓名欄位為空!");
							}else
								if(Student.addUser(tr.getDbName(), df.formatCellValue(IDcell), df.formatCellValue(NameCell), "0")) {
									pw.println("Add User " + df.formatCellValue(NameCell) + " Success<br>");
								}else{
									pw.println("<br><h3>  Users " + df.formatCellValue(NameCell) + " already exist ! </h3>");
								}
							}
					}	
					pw.println("\nAdd User File Success: " + saveDirectory + FileName);
					pw.println("</body></html>");
				} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}else  
			 try {
				Workbook work = Workbook.getWorkbook(file);
				Sheet sheet = work.getSheet(0);
				int rows = sheet.getRows();
				int columns = sheet.getColumns();
				Cell cellID,cellName;
				int ID=-1,Name=-1;
				
				for(int i = 0;i < columns;i++) {
					if(sheet.getCell(i,0).equals("學號")) 
						Name = i;
					else if(sheet.getCell(i,0).equals("姓名"))
						ID = i;
				}
				for(int i = 1;i < rows;i++) {
					System.out.print(sheet.getCell(2,i).getContents()+"\t");
					System.out.println(sheet.getCell(1,i).getContents());
				}
				
				for (int i = 1; i < rows; i++) {
					cellID = sheet.getCell(1,i);
					cellName = sheet.getCell(2,i);
					
					if(cellName.getContents().equals("") || cellName.getContents().equals("")){
						System.out.println("學號或姓名欄位為空!");
					}else
						if (Student.addUser(tr.getDbName(), cellID.getContents(), cellName.getContents(), "0")) {
							pw.println("Add User " + cellName.getContents() + " Success<br>");
						}else {
							pw.println("<br><h3>  Users " + cellName.getContents() + " already exist ! </h3>");
						}
				}
				pw.println("\nAdd User File Success: " + saveDirectory + FileName);
				pw.println("</body></html>");
				
				work.close();				
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
