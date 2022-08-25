package tool.testResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpSession;

public class PythonTestResult implements TestResult{

	String[] studentID = null;
	String[] outputFile = null;
	String inputData = ""; 
	String outputPath = "";
	String db = "";
	
	public PythonTestResult(String[] studentID, String[] outputFile, String inputData, String outputPath, String db) {
		super();
		this.studentID = studentID;
		this.outputFile = outputFile;
		this.inputData = inputData;
		this.outputPath = outputPath;
		this.db = db;
		
	}
	public Process runTestResult() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Process process;
		String sandboxPath = "/home/";
		String execSandbox[] = {"/bin/sh","-c","docker exec "+ db + studentID[1]+ " /bin/sh "+ sandboxPath + "shell.sh " + sandboxPath + outputFile[0] +" "+ sandboxPath + studentID[0]+".txt"};

		if(createTestFile(studentID[0],inputData)) {
			process = Runtime.getRuntime().exec(execSandbox);
			
			ExecutionTestingThread mt = new ExecutionTestingThread(process);
			mt.start();
			mt.join(8000);
			if (mt.lock) {	//程式無限迴圈, 或沒有輸入滿而退出
				mt.interrupt();
		    	process.destroyForcibly();
			}	
			process = mt.getProcess();

			System.out.println(execSandbox[2]);
			deleteTestFile(studentID[0]);	
			return process;

		}else {
			System.out.println("fail! existed file");
			deleteTestFile(studentID[0]);	
		}
		
		return null;
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
}
