package tool.testResult;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class CSTestResult implements TestResult {
	
	String[] outputFile = null;
	String inputData = ""; 
	String outputPath = "";
	
	public CSTestResult(String[] outputFile, String inputData, String outputPath) {
		super();
		this.outputFile = outputFile;
		this.inputData = inputData;
		this.outputPath = outputPath;
	}

	@Override
	public Process runTestResult() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Process process = Runtime.getRuntime().exec("mono "+outputPath + outputFile[0]+".exe");
		
		OutputStreamWriter osr = new OutputStreamWriter(process.getOutputStream());
		osr.write(inputData + "\n");
		osr.flush();
		
		return process;
	}
}
