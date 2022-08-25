package tool.compiler;

import java.util.ArrayList;

public class PathData {
	private String[] packageName = null;
	private String[] outputFile = null;
	private ArrayList<String> error = null;
	private String inputPath = "";
	private String fileName = "";
	private String outputPath = "";
	private String oldfileName = "";
	
	
	public PathData(String[] packageName, String[] outputFile, ArrayList<String> error, String inputPath,
			String fileName, String outputPath, String oldfileName) {
		super();
		this.packageName = packageName;
		this.outputFile = outputFile;
		this.error = error;
		this.inputPath = inputPath;
		this.fileName = fileName;
		this.outputPath = outputPath;
		this.oldfileName = oldfileName;
	}



	public String[] getPackageName() {
		return packageName;
	}
	public void setPackageName(String[] packageName) {
		this.packageName = packageName;
	}
	public String[] getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String[] outputFile) {
		this.outputFile = outputFile;
	}
	public ArrayList<String> getError() {
		return error;
	}
	public void setError(ArrayList<String> error) {
		this.error = error;
	}
	public String getInputPath() {
		return inputPath;
	}
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	public String getOldfileName() {
		return oldfileName;
	}
	public void setOldfileName(String oldfileName) {
		this.oldfileName = oldfileName;
	}
	
	
	
	

}
