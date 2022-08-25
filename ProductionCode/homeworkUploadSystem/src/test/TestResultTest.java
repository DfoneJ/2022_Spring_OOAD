package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bean.TestResult;

public class TestResultTest {

	TestResult testResult;
	
	@Before
	public void setUp() throws Exception {
		testResult = new TestResult();
	}

	@Test
	public void testGetExecuteResult() {
		String executeResult = "executeResult";
		testResult.setExecuteResult(executeResult);
		assertEquals(testResult.getExecuteResult(), executeResult);
	}

	@Test
	public void testGetId() {
		int id = 1;
		testResult.setId(id);
		assertEquals(testResult.getId(), id);
	}

	@Test
	public void testGetQuestionID() {
		int questionId = 1;
		testResult.setQuestionID(questionId);
		assertEquals(testResult.getQuestionID(), questionId);
	}

	@Test
	public void testIsResult() {
		boolean isResult = true;
		testResult.setResult(isResult);
		assertEquals(testResult.isResult(), true);
	}

	@Test
	public void testGetDescription() {
		String description = "description";
		testResult.setDescription(description);
		assertEquals(testResult.getDescription(), description);
	}

	@Test
	public void testGetTest_data_id() {
		int inputDataID = 2;
		testResult.setTest_data_id(inputDataID);
		assertEquals(testResult.getTest_data_id(), inputDataID);
	}
	
	@Test
	public void testIsPass () {
		testResult.setPass(true);
		assertEquals(testResult.isPass(), true);
	}

}
