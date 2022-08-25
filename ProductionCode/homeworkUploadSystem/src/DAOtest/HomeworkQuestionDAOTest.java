package DAOtest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import componment.HomeworkQuestion;
import dao.HomeworkQuestionDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeworkQuestionDAOTest {
	
	HomeworkQuestionDAO homeworkQuestionDAO;
	
	@Before
	public void setUp() {
		homeworkQuestionDAO = new HomeworkQuestionDAO();
	}
	
	@Test
	public void testGetSimpleQuestionType() {
		HomeworkQuestion homeworkQuestion = homeworkQuestionDAO.getQuestion("testcourse", "999");
		assertEquals("hw", homeworkQuestion.getType());
	}
	
	@Test
	public void testGetSimpleQuestionId() {
		HomeworkQuestion homeworkQuestion = homeworkQuestionDAO.getQuestion("testcourse", "999");
		assertEquals("999", homeworkQuestion.getId());
	}
	
	@Test
	public void testGetSimpleQuestionContent() {
		HomeworkQuestion homeworkQuestion = homeworkQuestionDAO.getQuestion("testcourse", "999");
		assertEquals("123456", homeworkQuestion.getContent());
	}
	
	@Test
	public void testGetSimpleQuestionDeadline() {
		HomeworkQuestion homeworkQuestion = homeworkQuestionDAO.getQuestion("testcourse", "999");
		assertEquals("2023/02/28 23:59", homeworkQuestion.getDeadline());
	}
	
	@Test
	public void testCheckSimpleQuestionExpired() throws ParseException {
		HomeworkQuestion homeworkQuestion = homeworkQuestionDAO.getQuestion("testcourse", "999");
		String deadline = homeworkQuestion.getDeadline();
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd HH:mm");
		Date date1 = dateFormat.parse("2020/01/25 20:00");
		Date dl = dateFormat.parse(deadline);
		assertTrue(date1.before(dl));
	}
	
	@Test
	public void testGetSimpleQuestionLanguage() {
		HomeworkQuestion homeworkQuestion = homeworkQuestionDAO.getQuestion("testcourse", "999");
		assertEquals("C", homeworkQuestion.getlanguage());
	}
}
