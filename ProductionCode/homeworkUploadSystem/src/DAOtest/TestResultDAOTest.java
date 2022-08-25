package DAOtest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.TestResult;
import componment.Course;
import dao.TestResultDAO;

public class TestResultDAOTest {
	
	TestResultDAO testResultDAO;
	String testCourseName;
	
	@Before
	public void setUp() throws Exception {
		testResultDAO = new TestResultDAO();
		testCourseName = "TestCourse";
	}
	
	@Test
	public void testGetTestResultsIsPass() {
		List<TestResult> trs = testResultDAO.getTestResults(testCourseName, "0", 2);
		
		assertEquals(4, trs.size());
		assertEquals(true, trs.get(0).isPass());
		assertEquals(true, trs.get(1).isPass());
		assertEquals(true, trs.get(2).isPass());
		assertEquals(false, trs.get(3).isPass());
	}
	
	@Test
	public void testGetTestResultsGetDescription() {
		List<TestResult> trs = testResultDAO.getTestResults(testCourseName, "0", 2);
		
		assertEquals(4, trs.size());
		assertEquals("通過測試", trs.get(0).getDescription());
		assertEquals("通過測試", trs.get(1).getDescription());
		assertEquals("通過測試", trs.get(2).getDescription());
		assertEquals("測試失敗", trs.get(3).getDescription());
	}
}
