package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import bean.TestData;

public class TestDataTest {
	
	TestData testData;
	
	@Before
	public void setUp() {
		testData = new TestData();
	}
	
	@Test
	public void testId() {
		int id = 1;
		testData.setId(id);
		assertEquals(testData.getId(), id);
	}
	
	@Test
	public void testQuestionId() {
		int q_id = 1;
		testData.setQuestionID(q_id);
		assertEquals(testData.getQuestionID(), q_id);
	}
	
	@Test
	public void testInputData() {
		String inputData = "data";
		testData.setInput_data(inputData);
		assertEquals(testData.getInputData(), inputData);
	}

	// except output data
	@Test
	public void testTrueResult() {
		String trueResult = "result";
		testData.setTrue_result(trueResult);
		assertEquals(testData.getExpectedOutput(), trueResult);
	}
}