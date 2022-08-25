package DAOtest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import componment.Course;
import dao.CourseDAO;
import java.util.ArrayList;

public class CourseDAOTest {
	
	CourseDAO courseDAO;
	@Before
	public void setUp() throws Exception {
		courseDAO = new CourseDAO();
	}
	 
	@Test
	public void testGetSimpleCourseId() {
		Course course = courseDAO.getCourse("OOAD");
		assertEquals("OOAD", course.getId());
	}
	 
	@Test
	public void testgetSimpleCourseName() {
		Course course = courseDAO.getCourse("OOAD");
		assertEquals("OOAD", course.getName());
	}
	 
	@Test
	public void testGetSimpleCourseTId() {
		Course course = courseDAO.getCourse("OOAD");
		assertEquals("teacher", course.getTeacherId());
	}
	 
	@Test
	public void testGetSimpleCourseTName() {
		Course course = courseDAO.getCourse("OOAD");
		assertEquals("teacher", course.getTeacherName());
	}
	 
	@Test
	public void testGetMultipleCourseId() {
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<String> IDs = new ArrayList<String>();
		IDs.add("110PD01");
		IDs.add("OOAD");
		IDs.add("TestCourse");
		IDs.add("testdb");
		courses = courseDAO.getAllCourse();
		for(int i=0; i<courses.size(); i++) {
			Course c=courses.get(i);
			assertEquals(IDs.get(i), c.getId());
		}
	}
		
	@Test
	public void testGetMultipleCourseName() {
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<String> IDs = new ArrayList<String>();
		IDs.add("110PD01");
		IDs.add("OOAD");
		IDs.add("TestCourse");
		IDs.add("testdb");
		courses = courseDAO.getAllCourse();
		for(int i=0; i<courses.size(); i++) {
			Course c=courses.get(i);
			assertEquals(IDs.get(i), c.getName());
		}
	}
	
	@Test
	public void testGetMultipleCourseTId() {
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<String> IDs = new ArrayList<String>();
		IDs.add("sysop");
		IDs.add("teacher");
		IDs.add("test");
		IDs.add("testdb");
		courses = courseDAO.getAllCourse();
		for(int i=0; i<courses.size(); i++) {
			Course c=courses.get(i);
			assertEquals(IDs.get(i), c.getTeacherId());
		}
	}
		
	@Test
	public void testGetMultipleCourseTName() {
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<String> IDs = new ArrayList<String>();
		IDs.add("sysop");
		IDs.add("teacher");
		IDs.add("test");
		IDs.add("testdb");
		courses = courseDAO.getAllCourse();
		for(int i=0; i<courses.size(); i++) {
			Course c=courses.get(i);
			assertEquals(IDs.get(i), c.getTeacherName());
		}
	}
}
