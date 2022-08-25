package component.UploadFileHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ApacheFileUpload implements UploadFileHandler {
	private HttpServletRequest Request;
	private int SizeThreshold;
	private int FileSizeMax;
	private int SizeMax;
	private List<FileItem> Files;
	private String MainFunctionName;
	private String NumberOfFile = "";
	private String NewFileName;

	public ApacheFileUpload(HttpServletRequest request) {
		Request = request;
		SizeThreshold = 2 * 1024 * 1024; // 上傳檔案的buffer
		FileSizeMax = 2 * 1024 * 1024; // 一個檔案的大小
		SizeMax = 2 * 1024 * 1024; // 總共檔案的大小
	}

	public ApacheFileUpload(HttpServletRequest request, int sizeThreshold, int fileSizeMax, int sizeMax) {
		super();
		Request = request;
		SizeThreshold = sizeThreshold;
		FileSizeMax = fileSizeMax;
		SizeMax = sizeMax;
	}

	public void init() throws FileUploadException {
		setFileItem();
	}

	public String getMainFuntionName() {
		return MainFunctionName;
	}

	public HttpServletRequest getRequest() {
		return Request;
	}

	public void setRequest(HttpServletRequest request) {
		Request = request;
	}

	public int getSizeThreshold() {
		return SizeThreshold;
	}

	public void setSizeThreshold(int sizeThreshold) {
		SizeThreshold = sizeThreshold;
	}

	public int getFileSizeMax() {
		return FileSizeMax;
	}

	public void setFileSizeMax(int fileSizeMax) {
		FileSizeMax = fileSizeMax;
	}

	public int getSizeMax() {
		return SizeMax;
	}

	public void setSizeMax(int sizeMax) {
		SizeMax = sizeMax;
	}

	public String getNumberOfFile() {
		return NumberOfFile;
	}

	public String getNewFileName() {
		return NewFileName;
	}

	public boolean isMultipartContent() {
		return ServletFileUpload.isMultipartContent(Request);
	}

	// 用原始的檔名儲存檔案
	public boolean writeFile(String PSpath, String uploadHWPath, String rename, String dbName) throws Exception {

		if (isMultipartContent()) {
			// 判斷檔案中的檔名有沒有特殊字元
			if (Files != null && Files.size() > 0)
				for (FileItem item : Files)
					if (!item.isFormField()) {
						String name = new File(item.getName()).getName();
						if (name.length() < 1) // 檔名太短則不儲存
							continue;
						else if (detectFileNameIsInvalid(name) == true)
							return false;
//	                	System.out.println(name);
					}
			// 建立一個資料夾(學號)
			File directoryFile = new File(PSpath);
			if (!directoryFile.isDirectory()) {
				directoryFile.mkdirs();
			}
			// 根據學號下的檔案數再建立一個資料夾(檔名為檔案數)
			File[] f = directoryFile.listFiles();
			PSpath = PSpath + (f.length + 1) + File.separator;
			directoryFile = new File(PSpath);
			if (!directoryFile.isDirectory()) {
				directoryFile.mkdirs();
			}

			if (Files != null && Files.size() > 0) {
				for (FileItem item : Files) {
					if (!item.isFormField()) {
						String name = new File(item.getName()).getName();
						if (name.length() < 1) // 檔名太短則不儲存
							continue;
						name = name.replaceAll("\\s+", "");
						System.out.println(PSpath + name);

						// 將所有程式存入ProgramSet
						File store = new File(PSpath + name);
						item.write(store);

						if (item.getFieldName().equals("hwFile")) {
							MainFunctionName = name;
						} else {
							NumberOfFile = "," + name + NumberOfFile;
						}
					}
				}
				f = directoryFile.listFiles();
				if (f.length > 1) {
					// 判斷是不是為多個檔案，如果是要標記@ (為了在編譯時，選取資料夾)
					// 下面那行關係到C_Compiler路徑與upLoadFile的newFileName!!!
					NewFileName = "@" + dbName + "@" + rename + MainFunctionName;

					// 如果為主程式，複製一份到uploadhw
					Runtime rt = Runtime.getRuntime();
					String[] cmd = new String[3];
					cmd[0] = "cp";
					cmd[1] = PSpath + MainFunctionName;
					cmd[2] = uploadHWPath + NewFileName;
					Process p = rt.exec(cmd);
					p.waitFor();
				} else if (f.length == 1) {
					NewFileName = rename + MainFunctionName;

					// 如果為主程式，複製一份到uploadhw
					Runtime rt = Runtime.getRuntime();
					String[] cmd = new String[3];
					cmd[0] = "cp";
					cmd[1] = PSpath + MainFunctionName;
					cmd[2] = uploadHWPath + NewFileName;
					Process p = rt.exec(cmd);
					p.waitFor();
				}
			}
			return true;
		} else {
			System.out.println("Files are non-processable, You should check that whether for MultipartContent");
			return false;
		}

	}

	private void setFileItem() throws FileUploadException {
		if (isMultipartContent()) {
			System.out.println("Files are processable");
			DiskFileItemFactory factory = new DiskFileItemFactory();

			factory.setSizeThreshold(SizeThreshold);

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(FileSizeMax);
			upload.setSizeMax(SizeMax);
			List<FileItem> formItems = upload.parseRequest(Request);
			System.out.println("formItems size:" + formItems.size());
			for (FileItem t : formItems) {
				System.out.println("formItems:" + t);
			}
			Files = formItems;
		}
	}

	private boolean detectFileNameIsInvalid(String fileName) {
		boolean ban = false;
		
		if (isFileTypeValid(fileName) == false) {
			ban = true;
		}
		// 判斷有沒有分號
		if (fileName.matches(".*;.*") || fileName.matches(".*@.*")) {
			ban = true;
		}
		
		if (isFileNameValid(fileName) == false) {
			ban = true;
		}

		return ban;
	}

	private boolean isFileTypeValid(String fileName) {
		boolean result = false;
		String[] supportLanguage = { "c", "py", "cs", "java", "h" };
		int index = fileName.lastIndexOf(".") + 1;
		String uploadedFileType = fileName.substring(index, fileName.length()).toLowerCase();
		for (String ch : supportLanguage) {
			if (ch.equals(uploadedFileType)) {
				result = true;
				break;
			}
		}
		
		return result;

	}

	private boolean isFileNameValid(String fileName) {
		// check fail,the filename doesn't match the filename rule
		boolean result = false;
		String reOfFileNameCheck = "\\w+[.](py|c|java){1}";
		Pattern p = Pattern.compile(reOfFileNameCheck);
		Matcher m = p.matcher(fileName);

		if (m.groupCount() != 1) {
			result = false;
		} else {
			while (m.find()) {
				if (!(m.group().equals(fileName))) {
					break;
				} else {
					result = true;
				}

			}

		}
		return result;

	}

}
