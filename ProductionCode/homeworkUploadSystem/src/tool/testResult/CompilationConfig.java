package tool.testResult;

public class CompilationConfig {
	String[] studentID = null;
	String[] outputFile = null;
	String[] packageName = null;
 	String inputData = "";
 	String outputPath = "";
 	String fileName = "";
	String true_result = "";
	String hook_path = "";
	String db = "";
	
	public CompilationConfig(String[] studentID, String[] outputFile, String[] packageName, String inputData,
			String outputPath, String fileName, String true_result, String hook_path, String db) {
		super();
		this.studentID = studentID;
		this.outputFile = outputFile;
		this.packageName = packageName;
		this.inputData = inputData;
		this.outputPath = outputPath;
		this.fileName = fileName;
		this.true_result = true_result;
		this.hook_path = hook_path;
		this.db = db;
		
	}
	
	public String[] getStudentID() {
		return studentID;
	}
	public void setStudentID(String[] studentID) {
		this.studentID = studentID;
	}
	public String[] getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String[] outputFile) {
		this.outputFile = outputFile;
	}
	public String[] getPackageName() {
		return packageName;
	}
	public void setPackageName(String[] packageName) {
		this.packageName = packageName;
	}
	public String getInputData() {
		return inputData;
	}
	public void setInputData(String inputData) {
		this.inputData = inputData;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTrue_result() {
		return true_result;
	}
	public void setTrue_result(String true_result) {
		this.true_result = true_result;
	}
	public String getHook_path() {
		return hook_path;
	}
	public void setHook_path(String hook_path) {
		this.hook_path = hook_path;
	}
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	

}
