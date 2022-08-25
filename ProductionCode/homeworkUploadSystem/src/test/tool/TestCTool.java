package test.tool;

import org.junit.Before;
import org.junit.Test;

import tool.Tester;
import tool.Language;
import tool.TestThread;
import tool.testResult.CompilationConfig;
import tool.testResult.PythonTestResult;
import tool.testResult.TestResult;

import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCTool {

	@Before
	public void setUp() {

	}

	@Test
	public void testCleanUpExecuteResult() {
		StringBuffer sbWithBlankAndLF = new StringBuffer();
		StringBuffer sbWithLFAndLF = new StringBuffer();
		StringBuffer sbStartWithLF = new StringBuffer();
		String correctsb = "Player1 -10\n" + "Player2 +10\n" + "Bank 0";

		sbWithBlankAndLF.append("Player1 -10 \n" + "Player2 +10 \n" + "Bank 0");
		sbWithLFAndLF.append("Player1 -10\n\n" + "Player2 +10\n\n" + "Bank 0");
		sbStartWithLF.append("\nPlayer1 -10\n" + "Player2 +10\n" + "Bank 0");

		Assert.assertEquals(correctsb, this.formatResult(sbWithBlankAndLF.toString()));
		Assert.assertEquals(correctsb, this.formatResult(sbWithLFAndLF.toString()));
		Assert.assertEquals(correctsb, this.formatResult(sbStartWithLF.toString()));



	}
	
	@Test
	public void TestgetExecuteResult() {
		
//		String hwId = "003";
//		String studentId = "0";//name
//		String FileName = "";
//		
//		String newFileName = "/home/aaron/6-7work/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/homeworkUploadSystem//WEB-INF/uploadHW/003/";
//		String cLibHookPath = "/home/aaron/6-7work/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/homeworkUploadSystem";//WEB-INF/hook///req.getServletContext().getRealPath("./") + "/WEB-INF/hook/";
//		String oldfileName = "0_003_1638593422449.py";
//		String packageName[]= new String [1];
		
//		String inputTestData = "3\n" + 
//				"2 10 5\n" + 
//				"Q 3 10\n" + 
//				"Y\n" + 
//				"2\n" + 
//				"Y\n" + 
//				"7\n" + 
//				"N\n" + 
//				"Y\n" + 
//				"8\n" + 
//				"Y\n" + 
//				"Q\n" + 
//				"8\n" + 
//				"Y\n" + 
//				"2\n" + 
//				"N"; //inputTestData should fetch from db. 
//		String fileName = "0_003_1638593422449.py";
//		try {
//			String executeResult = this.getPyExecuteResult(fileName,inputTestData);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
				
	}
	
	private String formatResult(String inputResult) {
//		String regexOfsbCheck = "(.+)(\\S+)(\\n{1})";
		String regexOfsbStartWithLFCheck = "^(\\n+)"; // start with LF
		String regexOfsbWithWrongEndCheck = "(\\s+)(\\n{1})"; // blank+LF or LFLF is wrong.

		String resultStartFormatted = inputResult.replaceAll(regexOfsbStartWithLFCheck, "");
		String resultEndFormatted = resultStartFormatted.replaceAll(regexOfsbWithWrongEndCheck, "\n");

		return resultEndFormatted;
	}
	
	private class CompilationConfigChild extends CompilationConfig{

		String[] studentID = null;
		String[] filetype = null;
	 	String inputTestData = "";
	 	String fileName = "";
		public CompilationConfigChild(String[] studentID, String[] filetype, String inputTestData, String fileName) {
			super(studentID,filetype,null,inputTestData,null,fileName,null,null,null);
		}
	
	}
	public String getPyExecuteResult(String fileName, String inputTestData)
			throws IOException, InterruptedException, TimeoutException {
	
		
		String[] studentID = fileName.split("_"); 
		String[] filetype = fileName.split("\\."); //py
		CompilationConfig config = new CompilationConfigChild(studentID, filetype, inputTestData,fileName);
		
		long timeout = 8000;
		StringBuffer executeResultBuffer = new StringBuffer();
		Process process = null;
		String executeResultFormatted = ""; 
		
		Language language = new Language(filetype[1]);
		TestResult result = language.setBuildConfig(config);
		

		process = result.runTestResult();
		if(process == null) {
			return "Compose process fail"; // file not found
		}
		
		Worker worker = new Worker(process);
		worker.start();

		try {
			worker.join(timeout);
			if (worker.exit != null) {
				

				InputStreamReader isr = new InputStreamReader(process.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				if(br.ready())
				while ((line = br.readLine()) != null)
					executeResultBuffer.append(line + "\n");
				
				executeResultFormatted =  this.formatResult(executeResultBuffer.toString());
				System.out.println("Excute Resutlt:\n" + executeResultFormatted);

			} else {
				throw new TimeoutException();
			}
		} catch (InterruptedException ex) {
			worker.interrupt();
			Thread.currentThread().interrupt();
			throw ex;
		} finally {
			process.destroy();
		}
		return executeResultFormatted;
	}
	
	
	private class Worker extends Thread {
		private final Process process;
		private Integer exit;

		private Worker(Process process) {
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
	
	
	
	
}
