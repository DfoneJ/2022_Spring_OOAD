package tool.compiler;

import java.io.IOException;

public class CSCompiler implements Compiler {
	private String[] outputFile = null;
	private String inputPath = "";
	private String fileName = "";
	private String outputPath = "";

	
	public CSCompiler(String[] outputFile, String inputPath, String fileName, String outputPath){
		this.outputFile = outputFile;
		this.inputPath = inputPath;
		this.fileName = fileName;
		this.outputPath = outputPath;
	}
	

	@Override
	public Process compile(Runtime rt) throws IOException {
		// TODO Auto-generated method stub
		
		String[] cmd = new String[3];
		cmd[0] = "/bin/sh";
		cmd[1] = "-c";
		cmd[2] = "gcc " + inputPath + fileName + " -lm -o " + outputPath + outputFile[0];
				
		System.out.println("\nExecing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
		
		return rt.exec(cmd);
		
	}
}
