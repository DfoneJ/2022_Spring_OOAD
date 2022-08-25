package DAOtest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.TestData;
import dao.TestDataDAO;

public class TestDataDAOTest {

	TestDataDAO testDataDAO;
	String testCourseName;
	
	@Before
	public void setUp() throws Exception {
		testDataDAO = new TestDataDAO();
		testCourseName = "TestCourse";
	}
	
	@Test
	public void testGetTestDataInputData() {
		List<TestData> tds = testDataDAO.getTestDatas(testCourseName, 1);
		
		assertEquals(2, tds.size());
		assertEquals("1", tds.get(0).getInputData());
		assertEquals("2", tds.get(1).getInputData());
	}
	
	@Test
	public void testGetTestDataExpectOutput() {
		List<TestData> tds = testDataDAO.getTestDatas(testCourseName, 1);
		
		assertEquals(2, tds.size());
		assertEquals("1", tds.get(0).getExpectedOutput());
		assertEquals("2", tds.get(1).getExpectedOutput());
	}
}
