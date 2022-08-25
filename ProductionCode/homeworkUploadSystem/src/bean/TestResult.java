package bean;

public class TestResult {
	private int id;
	private String studentID;
	private int questionID;
	private int test_data_id;
	private boolean result;
	private String description;
	private String executeResult;
	private boolean isPass;

	public TestResult() {
		
	}
	
	public TestResult(String exeResult, boolean isPass) {
		this.executeResult = exeResult;
		this.isPass = isPass;
	}
	
	public String getExecuteResult() {
		return executeResult;
	}

	public void setExecuteResult(String executeResult) {
		this.executeResult = executeResult;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTest_data_id() {
		return test_data_id;
	}

	public void setTest_data_id(int test_data_id) {
		this.test_data_id = test_data_id;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	public String getStudentID() {
		return this.studentID;
	}
}
