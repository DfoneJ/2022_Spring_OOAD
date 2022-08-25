package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.TestResult;
import componment.Homework;

public class HomeworkTest {
	
	Homework hw;
	
	@Before
	public void setUp() throws Exception {
		hw = new Homework("001", "2022/02/28 23:59", "title", "description", "110598108_23.c", "systemName", "saveDirectory");
	}

	@Test
	public void testGetId() {
		assertEquals(hw.getId(), "001");
	}

	@Test
	public void testGetTime() {
		assertEquals(hw.getTime(), "2022/02/28 23:59");
	}

	@Test
	public void testGetTitle() {
		assertEquals(hw.getTitle(), "title");
	}

	@Test
	public void testGetFileDesc() {
		assertEquals(hw.getFileDesc(), "description");
	}

	@Test
	public void testGetFileName() {
		assertEquals(hw.getFileName(), "110598108_23.c");
	}
	
	@Test
	public void testGetTestResult() {
		assertEquals(hw.getTestResults().size(), 0);
		hw.addResult(new TestResult("test123", true));
		assertEquals(hw.getTestResults().size(), 1);
		assertEquals(hw.getTestResults().get(0).getExecuteResult(), "test123");
		assertEquals(hw.getTestResults().get(0).isPass(), true);
	}
	
	@Test
	public void testGetPassState() {
		Homework hw = new Homework("110598108", "001", "filePath");
		hw.addResult(new TestResult("123", true));
		hw.addResult(new TestResult("456", false));
		
		List<TestResult> result = hw.getResult("110598108");
		assertEquals(result.get(0).getExecuteResult(), "123");
		assertEquals(result.get(0).isPass(), true);
		assertEquals(result.get(1).getExecuteResult(), "");
		assertEquals(result.get(1).isPass(), false);
	}
	
	@Test
	public void testCheckResult() {
		Homework hw = new Homework("110598108", "001", "filePath");
		hw.addResult(new TestResult("123", true));
		hw.addResult(new TestResult("456", false));
		
		List<TestResult> result = hw.getResult("110598108");
		assertEquals(result.get(0).getExecuteResult(), "123");
		assertEquals(result.get(0).isPass(), true);
		assertEquals(result.get(1).getExecuteResult(), "");
		assertEquals(result.get(1).isPass(), false);
	}

}
