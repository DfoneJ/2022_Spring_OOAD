package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.TestResult;
import bean.TestData;
import componment.Homework;
import componment.HomeworkQuestion;

public class HomeworkQuestionTest {

	HomeworkQuestion question;
	
	@Before
	public void setUp() throws Exception {
		question = new HomeworkQuestion("hw", "001", "content", "2022/08/08 23:59", false, "C");
	}

	@Test
	public void testGetType() {
		assertEquals(question.getType(), "hw");
	}

	@Test
	public void testGetId() {
		assertEquals(question.getId(), "001");
	}

	@Test
	public void testGetContent() {
		assertEquals(question.getContent(), "content");
	}

	@Test
	public void testGetDeadline() {
		assertEquals(question.getDeadline(), "2022/08/08 23:59");
	}

	@Test
	public void testIsExpired() {
		assertEquals(question.isExpired(), false);
	}

	@Test
	public void testGetlanguage() {
		assertEquals(question.getlanguage(), "C");
	}

	@Test
	public void testGetWeights() {
		question.setSimilarity(50);
		assertEquals(question.getSimilarity(), 50);
	}
	
	@Test
	public void testGetWeightsLess0() {
		question.setSimilarity(-50);
		assertEquals(question.getSimilarity(), 0);
	}
	
	@Test
	public void testGetWeightsGreater100() {
		question.setSimilarity(150);
		assertEquals(question.getSimilarity(), 100);
	}
	
	@Test
	public void testGetQuestionItemHandIn() throws Exception {
		Homework hw = new Homework("110598108", "001", "filePath");
		hw.addResult(new TestResult("123", false));
		
		question.addHomework(hw);
		List<String> result = question.getQuestionItem("110598108");
		assertEquals(result.get(0), "hw");
		assertEquals(result.get(1), "001");
		assertEquals(result.get(2), "2022/08/08 23:59");
		assertEquals(result.get(3), "繳交");
		assertEquals(result.get(4), "C");
		assertEquals(result.get(5), "測試失敗");
		assertEquals(result.get(6), "測試結果");
	}

	@Test
	public void testGetQuestionItemNotHandIn() throws Exception {
		question.addHomework(new Homework("110598108", "001", "filePath"));
		List<String> result = question.getQuestionItem("1105556666");
		assertEquals(result.get(0), "hw");
		assertEquals(result.get(1), "001");
		assertEquals(result.get(2), "2022/08/08 23:59");
		assertEquals(result.get(3), "繳交");
		assertEquals(result.get(4), "C");
		assertEquals(result.get(5), "未繳");
		assertEquals(result.get(6), "-");
	}

	@Test
	public void testCheckResult() throws Exception {
		Homework hw = new Homework("110598108", "001", "filePath");
		hw.addResult(new TestResult("123", true));
		hw.addResult(new TestResult("456", false));
		
		List<Homework> hws = new ArrayList<Homework>();
		hws.add(hw);
		question.setHomeworks(hws);
		List<TestResult> result = question.checkResult("110598108");
		assertEquals(result.get(0).getExecuteResult(), "123");
		assertEquals(result.get(0).isPass(), true);
		assertEquals(result.get(1).getExecuteResult(), "");
		assertEquals(result.get(1).isPass(), false);
	}
	
	@Test
	public void testAddTestData() throws Exception {
		question.addTestData("INPUT_1", "OUTPUT_1");
		assertEquals(question.getTestDatas().get(0).getInputData(), "INPUT_1");
		assertEquals(question.getTestDatas().get(0).getExpectedOutput(), "OUTPUT_1");
		assertEquals(question.getTestDatas().get(0).getQuestionID(), 1);
	}
	
	@Test
	public void testDeleteTestData() throws Exception {
		TestData testdata1 = new TestData(1, 1, "INPUT_1", "OUTPUT_1");
		TestData testdata2 = new TestData(2, 1, "INPUT_2", "OUTPUT_2");
		List<TestData> testdatas = new ArrayList<>();
		testdatas.add(testdata1);
		testdatas.add(testdata2);
		question.setTestDatas(testdatas);
		assertEquals(2, question.getTestDatas().size());
		question.deleteTestData("2");
		assertEquals(1, question.getTestDatas().size());
	}
}
