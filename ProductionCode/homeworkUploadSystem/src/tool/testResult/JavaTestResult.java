package tool.testResult;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class JavaTestResult implements TestResult{

	String[] outputFile = null;
	String[] packageName = null;
	String inputData = ""; 
	String outputPath = "";
	
	
	public JavaTestResult(String[] outputFile, String[] packageName, String inputData, String outputPath) {
		super();
		this.outputFile = outputFile;
		this.packageName = packageName;
		this.inputData = inputData;
		this.outputPath = outputPath;
	}

	@Override
	public Process runTestResult() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String[] cmd = new String[3];
		cmd[0] = "/bin/sh";
		cmd[1] = "-c";
		if(packageName[0]==null)
			cmd[2] = "java -cp " + outputPath + " " + outputFile[0];
		else 
			cmd[2] = "java -cp " + outputPath + " " + packageName[0]+"."+outputFile[0];
		
		System.out.println("\nExecing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
		Process process = Runtime.getRuntime().exec(cmd);
		
		OutputStreamWriter osr = new OutputStreamWriter(process.getOutputStream());
		osr.write(inputData + "\n");
		osr.flush();
		
		return process;
		
	}
	
}
