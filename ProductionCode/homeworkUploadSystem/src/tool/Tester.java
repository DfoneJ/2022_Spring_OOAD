package tool;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeoutException;

import org.apache.poi.util.SystemOutLogger;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;

import ExamDB.HWID_RESERCH;
import bean.DBcon;
import bean.StudentMistackDao;
import tool.compiler.Compiler;
import tool.compiler.PathData;
import tool.testResult.CompilationConfig;
import tool.testResult.TestResult;


public class Tester {
	// String gccPath = "C:/MinGW/bin/";
	private String db = "";
	String gccPath = "/usr/bin/";
	String inputPath = "";
	String outputPath = "";
	String[] hook_list = {
		"system.so",
		"fopen.so"
	};

	public Tester(String ipath, String opath) {
		inputPath = ipath;
		outputPath = opath;
	}
	public void setdb(String db) {
		this.db = db;
	}
	private String getdb() {
		return db;
	}

	@SuppressWarnings("null")
	public boolean complierFile(String fileName,String language,String oldfileName,ArrayList<String> error,String packageName[]) {
		Runtime rt = Runtime.getRuntime();
		Process proc = null;
		
		try {
			System.getProperty("os.name");
//			String[] cmd = new String[3];
			// cmd[0] = "cmd.exe";
			// cmd[1] = "/C";
			// cmd[2] = gccPath+"gcc.exe \""+inputPath+fileName+"\" -o
			// \""+outputPath+fileName+".exe\"";
			// cmd[2] = gccPath + "gcc -o \"" + inputPath + fileName + "\" \"" +
			// outputPath + fileName + ".c\"";
			
			String[] outputFile = fileName.split("\\.");
			PathData path = new PathData(packageName, outputFile, error, inputPath,fileName, outputPath, oldfileName);
			System.out.println("language="+language);
			
			Language selectLanguage = new Language(language);
			Compiler operation = selectLanguage.BuildCompiler(path);
			proc = operation.compile(rt);
			
			// any error???
			int exitVal = proc.waitFor();
			if (exitVal == 0) {
				if(outputFile[1].equals("java"))
				{
					String s[]=fileName.split("_");
					File newFile = new File(inputPath + fileName);
//					File abstractFile = new File(inputPath + s[2]);
					File abstractFile = new File(inputPath + oldfileName);
					boolean done = abstractFile.renameTo(newFile);
					System.out.println(done);
				}
				else if(language.equals("C#"))
				{
//					System.out.println(outputFile[0]);
					String s="mv "+inputPath+outputFile[0]+".exe "+outputPath;
					proc = rt.exec(s);
//					System.out.println(s);
					proc.waitFor();
				}
				return true;
			}
			else
			{
			//編譯失敗讀取錯誤訊息
				StringBuilder sb =new StringBuilder();
				InputStreamReader isr = new InputStreamReader(proc.getErrorStream());
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				int count=0;
				try {
					 while((line=br.readLine())!=null /*&& count<5*/)
			         {
						 System.out.println(line);
						 if(line.indexOf("opt/")==-1 && line.indexOf("home/")==-1)
						 {
//							 System.out.println(line.indexOf("home/")+" "+line.indexOf("home/"));
							 sb.append(line+"\n");
							 count++;
						 }
			         }
					 String content=sb.toString();
					 int index = 0;
					 while ((index = content.indexOf("\n")) != -1) { // 處理公佈欄的換行字元
					 content = content.substring(0, index) + " <BR> " + content.substring(index + "\n".length());
					 }
					 error.add(content);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("cant read");
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (proc != null) {
				proc.destroy();
			}
			rt.freeMemory();
		}
		String[] outputFile = fileName.split("\\.");
		if(outputFile[1].equals("java"))
		{
			String s[]=fileName.split("_");
			File newFile = new File(inputPath + fileName);
			File abstractFile = new File(inputPath + s[2]);
			boolean done = abstractFile.renameTo(newFile);
			System.out.println(done);
		}
		return false;
	}

	public boolean checkResult(String fileName, String inputData, final String true_result)
			throws IOException, InterruptedException, TimeoutException {
		return this.checkResult(fileName, inputData, true_result, "./",new String[1],null);
	}
	public boolean checkResult(String fileName, String inputData, final String true_result, String hook_path,String oldFileName,String packageName[],StringBuffer executeResult)
			throws IOException, InterruptedException, TimeoutException {
		String[] outputFile = fileName.split("\\.");
		if(outputFile[1].equals("java"))return this.checkResult(oldFileName, inputData, true_result, "./",packageName,executeResult);
		return this.checkResult(fileName, inputData, true_result, "./",packageName,executeResult);
	}
	
	public boolean createTestFile(String ID,String inputData) throws IOException {
		File testFile = new File(outputPath + ID +".txt");
		File shell = new File(outputPath + "shell.sh");
		if(!shell.exists()) {
			shell.createNewFile();
			BufferedWriter shellWriter = new BufferedWriter(new FileWriter(shell));
			shellWriter.write("$1 < $2");
			shellWriter.flush();
			shellWriter.close();
		}
		
		if(testFile.createNewFile()) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
			writer.write(inputData);
			writer.flush();
			writer.close();	
			return true;
		}
		return false;
	}
	
	public boolean deleteTestFile(String ID) throws IOException {
		File testFile = new File(outputPath + ID +".txt");
		if(testFile.delete()) {
			return true;
		}else {
			return false;
		}

	}

	public boolean checkResult(String fileName, String inputData, final String true_result, String hook_path,String packageName[],StringBuffer executeResult)
			throws IOException, InterruptedException, TimeoutException {
		boolean finalResult = false;
		long timeout = 8000;
		StringBuffer executeResultBuffer = new StringBuffer();
		StringBuffer trueResultBuffer = new StringBuffer();
		Process process = null;
		trueResultBuffer.append(true_result);
		trueResultBuffer.append("\n");
		
		String studentID[] = fileName.split("_");
		String[] outputFile = fileName.split("\\.");
		System.out.println("fileNameIncheckResult : "+fileName);
		for(String item : outputFile) {
			System.out.println("outputFile : "+item);
		}
		CompilationConfig configs = new CompilationConfig(studentID, outputFile, packageName, inputData, outputPath, fileName, true_result, hook_path, getdb());
		Language language = new Language(outputFile[1]);
		TestResult result = language.setBuildConfig(configs);
		
		// hook C standard library to prevent from executing danger function
		String[] envp = { "LD_PRELOAD=" };
		for (String hook : hook_list) envp[0] += hook_path + hook + ":";
//		System.out.println("env : "+envp[0]);
		

		process = result.runTestResult();
		if(process == null) {
			return false; // file not found
		}
		Worker worker = new Worker(process);
		worker.start();
		worker.join(8000);

		// get the output data of process
		InputStreamReader isr = new InputStreamReader(process.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		if(br.ready())
		while ((line = br.readLine()) != null)
			executeResultBuffer.append(line + "\n");
		
		String executeResultFormatted =  this.formatResult(executeResultBuffer.toString());
		String trueResultFormatted =  this.formatResult(trueResultBuffer.toString());

		System.out.println("Excute Resutlt:\n" + executeResultFormatted);
		System.out.println("True Result:\n" + trueResultFormatted);
		executeResult.append(executeResultFormatted);
		
		if (executeResultFormatted.equals(trueResultFormatted))
			finalResult =  true;
		else
			finalResult =  false;
		
		//after get the result , kill the thread which is still working.
		
		if (process.isAlive() == true) {
			process.destroy();
			System.out.println("Program Execution Timeout,over 8 seconds");
//			try {
//				worker.interrupt();
//		    	process.destroyForcibly();
//			}
//			catch(Exception ignore){
//				
//			}
			throw new TimeoutException();
		} 
		
		return finalResult;
			
		
	}

	private class Worker extends Thread {
//		private final Process process;
		private Process process;
		private boolean lock = true;
		
		private Worker(Process process) {
			this.process = process;
		}

		public void run() {
		
			try {
				process.waitFor();
				lock = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Thread.currentThread().interrupt();
				
			}
		}
		public boolean getLockStatus() {
			return this.lock;
		}
	}

	public int checkSourceCodeContext(String fileName) {
		FileReader fr;
		int result = 0;
		try {
			fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("system(") >= 0) {
					//System.out.println("C_TOOL system");
					result = 1;
					break;
				} else if (line.indexOf("process.h") >= 0) {
					//System.out.println("C_TOOL process.h");
					result = 1;
					break;
				} else if (line.indexOf("fopen") >= 0) {
					//System.out.println("C_TOOL fopen");
					result = 1;
					break;
					/*
					 * int s = 0,e; while(line.indexOf(",",s)>=0){ s =
					 * line.indexOf(",",s)+1; } e = line.indexOf(")",s); line =
					 * line.substring(s,e); line = line.replace(" ", "");
					 * if(!line.equals("\"rb\"")&&!line.equals("\"r\"")){ result
					 * = 2; break; }
					 */
				} else if (line.indexOf("popen") >= 0) {
				//	System.out.println("C_TOOL popen");
					result = 1;
					break;
				} else if (line.indexOf("wpopen") >= 0) {
				//	System.out.println("C_TOOL wopen");
					result = 1;
					break;
				} else if (line.indexOf("open(") >= 0) {
					//System.out.println("C_TOOL open");
					result = 1;
					break;
				} else if (line.indexOf("resin") >= 0) {
					//System.out.println("C_TOOL resin");
					result = 1;
					break;
				} else if (line.indexOf("webapps") >= 0) {
					//System.out.println("C_TOOL webapps");
					result = 1;
					break;
				} else if (line.indexOf("fork(") >= 0) {
					//System.out.println("C_TOOL fork");
					result = 1;
					break;
				} else if (line.indexOf("import os") >= 0) {
					//System.out.println("C_TOOL os");
					result = 1;
					break;
				} else if (line.indexOf("<unistd") >= 0) {
					//System.out.println("C_TOOL unistd");
					result = 1;
					break;
				} 
//				else if (line.indexOf("eval(") >= 0) {
//					result = 1;
//					break;
//				}
//				} else if(line.indexOf("import")>=0) {
//					result=1;
//					break;
//				} else if(line.indexOf("write")>=0) {
//					result=1;
//					break;
//				}
			}
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public String versionOfStrcmp(String fileName, String dbName, String stdID, int hwID) {
		FileReader fr;
		String errorMessage = "測試失敗";
		try {
			fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String words = "", left= "", right = "" ;
			int leftCount = 0, rightCount = 0, leftIndex = 0, rightIndex = 0, lineCount = 1;
			boolean start = false;
			
			while ((words = br.readLine()) != null) {

				words = words.replaceAll(" ","");
				while((leftIndex = words.indexOf("strcmp(")) >= 0){
					leftCount = 0; rightCount = 0;
					for(int i = leftIndex + 6; i < words.length(); i++) {
						if(words.charAt(i) == '(') {						
							leftCount++;
							start = true;
						}else if(words.charAt(i) == ')') {
							rightCount++;
						}
						
						if(start && (rightCount - leftCount == 0)) {
							rightIndex = i;
							break;
						}
					}
					
					//沒有條件判斷式就退出
					if(!( (words.indexOf("<") > -1) || (words.indexOf(">") > -1) || (words.indexOf("==") > -1))) {
						break;
					}

					//切開str左邊
					if(leftIndex - 3 < 0)
						left = words.substring(0, leftIndex);
					else
						left = words.substring(leftIndex - 3, leftIndex);
					
					//切開str()右邊
					System.out.println(words);
//					System.out.println(words.length() - rightIndex + 1);
					if(words.length() - rightIndex + 1 < 5) {
						right = words.substring(rightIndex +1, words.length());
					}else {
						right = words.substring(rightIndex +1, rightIndex +4);
					}
						
						
					words = words.substring(rightIndex +1, words.length());	//update
						
						
						if((left.contains("!=") || left.contains("==")) && (left.contains("1") || left.contains("-1"))) {
							errorMessage = "你在程式第" + lineCount + "行錯誤的使用 'strcmp' 請參考以下提示<br>" +
									   "if Return value < 0 then it indicates str1 is less than str2.<br>" + 
									   "if Return value > 0 then it indicates str2 is less than str1.<br>" + 
								       "if Return value = 0 then it indicates str1 is equal to str2.";
							intoDatabase(dbName, stdID, hwID);
							return errorMessage;
						}
						if((right.contains("!=") || right.contains("==")) && (right.contains("1") || right.contains("-1"))) {
							errorMessage = "你在程式第" + lineCount + "行錯誤的使用 'strcmp' 請參考以下提示<br><br>" +
									   "if Return value < 0 then it indicates str1 is less than str2.<br>" + 
									   "if Return value > 0 then it indicates str2 is less than str1.<br>" + 
								       "if Return value = 0 then it indicates str1 is equal to str2.";
							intoDatabase(dbName, stdID, hwID);
							return errorMessage;
						}
						
						
				}
				lineCount++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorMessage;
	}
	
	private void intoDatabase(String dbName, String stdID, int hwID) {
		try {
//			int i = Integer.parseInt(hwID);// 010 to 10
			String HWID = String.valueOf(hwID);
			
			DBcon db = new DBcon();
			StudentMistackDao smd = db.selectMistack(dbName, stdID, HWID);
			StudentMistackDao hwmd = db.selectMistack(dbName, null, HWID);
			
			//當前資料庫裏面學生有錯誤資料, 將抄襲行為記錄起來
			if(smd != null) {
				smd.setType3(smd.getType3() + 1);
				db.updateMistake(dbName, stdID, HWID, smd);
			}else {
				smd = new StudentMistackDao(stdID, HWID, 0, 0, 0, 1, 0, 0, 0);
				db.insertMistake(dbName,stdID, HWID, smd);
			}
			
			//當前資料庫裏面作業有錯誤資料, 將抄襲行為記錄起來
			if(hwmd != null) {
				hwmd.setType3(hwmd.getType3() + 1);
				db.updateMistake(dbName, null, HWID, hwmd);
			}else {
				hwmd = new StudentMistackDao(stdID, HWID, 0, 0, 0, 1, 0, 0, 0);
				db.insertMistake(dbName, null, HWID, hwmd);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String formatResult(String inputResult) {
		String regexOfsbStartWithLFCheck = "^(\\n+)"; // start with LF
		String regexOfsbWithWrongEndCheck = "(\\s+)(\\n{1})"; // blank+LF or LFLF is wrong.

		String resultStartFormatted = inputResult.replaceAll(regexOfsbStartWithLFCheck, "");
		String resultEndFormatted = resultStartFormatted.replaceAll(regexOfsbWithWrongEndCheck, "\n");
		return resultEndFormatted;
	}
	
		
}