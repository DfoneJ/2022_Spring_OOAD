package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.TestData;
import bean.TestResult;
import componment.Course;
import componment.HomeworkQuestion;
import dao.CourseDAO;

public class CourseTest {

	CourseDAO courseDAO;
	String testCourse;
	@Before
	public void setUp() throws Exception {
		courseDAO = new CourseDAO();
		testCourse = "TestCourse";
	}
	
	@Test
	public void testCheckResult() {
		Course course = courseDAO.getCourse(testCourse);
		List<TestResult> tr = course.checkResult("0", 2);
		
		assertEquals(4, tr.size());
		assertEquals(true, tr.get(0).isPass());
		assertEquals(true, tr.get(1).isPass());
		assertEquals(true, tr.get(2).isPass());
		assertEquals(false, tr.get(3).isPass());
	}
	
	@Test
	public void testGetQuestionItem() {
		Course course = courseDAO.getCourse(testCourse);
		List<List<String>> questionItems = course.viewHomeworkQuestions("0");
		
		assertEquals(17, questionItems.size());
		assertEquals("1", questionItems.get(0).get(1));
		assertEquals("1", questionItems.get(1).get(1));
		assertEquals("1", questionItems.get(2).get(1));
		assertEquals("1", questionItems.get(3).get(1));
		assertEquals("10", questionItems.get(4).get(1));
	}
	
	@Test
	public void testAddTestData() {
		Course course = new Course("OOAD", "OOAD", "teacherID", "teacherID");
		HomeworkQuestion question = new HomeworkQuestion("hw", "001", "content", "2022/08/08 23:59", false, "C");
		List<HomeworkQuestion> HWQs = new ArrayList<>();
		HWQs.add(question);
		course.setHomeworkQuestions(HWQs);
		course.addTestData("001", "INPUT_1", "OUTPUT_1");
		HomeworkQuestion question_by_getter = course.getHomeworkQuestion("001");
		assertEquals(question_by_getter.getTestDatas().get(0).getInputData(), "INPUT_1");
		assertEquals(question_by_getter.getTestDatas().get(0).getExpectedOutput(), "OUTPUT_1");
	}
	
	@Test
	public void testDeleteTestData() {
		Course course = new Course("OOAD", "OOAD", "teacherID", "teacherID");
		HomeworkQuestion question = new HomeworkQuestion("hw", "001", "content", "2022/08/08 23:59", false, "C");
		TestData testdata1 = new TestData(1, 1, "INPUT_1", "OUTPUT_1");
		TestData testdata2 = new TestData(2, 1, "INPUT_2", "OUTPUT_2");
		List<TestData> testdatas = new ArrayList<>();
		testdatas.add(testdata1);
		testdatas.add(testdata2);
		question.setTestDatas(testdatas);
		List<HomeworkQuestion> HWQs = new ArrayList<>();
		HWQs.add(question);
		course.setHomeworkQuestions(HWQs);
		HomeworkQuestion question_by_getter = course.getHomeworkQuestion("001");
		assertEquals(2, question_by_getter.getTestDatas().size());
		course.deleteTestData("001", "2");
		assertEquals(1, question_by_getter.getTestDatas().size());
	}
}
