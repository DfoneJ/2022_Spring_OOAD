package DAOtest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import componment.Homework;
import dao.HomeworkDAO;

public class HomeworkDAOTest {
	
	HomeworkDAO homeworkDAO;
	String testCourseName;
	
	@Before
	public void setUp() throws Exception {
		homeworkDAO = new HomeworkDAO();
		testCourseName = "TestCourse";
	}
	
	@Test
	public void testGetTestDataInputData() {
		List<Homework> hws = homeworkDAO.getAllQuestionByStudent(testCourseName, "1");

		assertEquals(1, hws.size());
		assertEquals("003", hws.get(0).getTitle());
	}
}
