package bean;

public class reTestResult {
	private String studentID;
	private String executionName;
	public reTestResult(String studentID, String executionName) {
		super();
		this.studentID = studentID;
		this.executionName = executionName;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getExecutionName() {
		return executionName;
	}
	public void setExecutionName(String executionName) {
		this.executionName = executionName;
	}
	
}
