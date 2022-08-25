package bean;

public class TestData {
	private int id;
	private int questionID;
	private String input_data;
	private String excepted_output;

	public TestData() {
		
	}
	
	public TestData(int id, int questionID, String inputData, String exceptedOutput) {
		this.id = id;
		this.questionID = questionID;
		this.input_data = inputData;
		this.excepted_output = exceptedOutput;
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

	public String getInputData() {
		return input_data;
	}

	public void setInput_data(String input_data) {
		this.input_data = input_data;
	}

	public String getExpectedOutput() {
		return excepted_output;
	}

	public void setTrue_result(String exceptedOutput) {
		this.excepted_output = exceptedOutput;
	}
}
