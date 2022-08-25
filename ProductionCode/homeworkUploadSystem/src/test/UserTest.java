package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import componment.User;

public class UserTest {

	User user;
	
	@Before
	public void setUp() throws Exception {
		user = new User("110PD02", "110598108", "test001", "0", "110PD02", "");
	}

	@Test
	public void testGetUser() {
		User user2 = new User("106C", "106590035", "testuser2", "0", "106C", "");
		User getUser = User.getUser(null, user2);
		assertEquals(getUser.getName(), "106590035");
	}

	@Test
	public void testGetName() {
		assertEquals(user.getName(), "110598108");
	}

	@Test
	public void testGetRealName() {
		assertEquals(user.getRealName(), "test001");
	}

	@Test
	public void testGetPasswd() {
		assertEquals(user.getPasswd(), "0");
	}

	@Test
	public void testGetCourse() {
		assertEquals(user.getCourse(), "110PD02");
	}

	@Test
	public void testGetCoursePath() {
		assertEquals(user.getCoursePath(), "");
	}

	@Test
	public void testGetDbName() {
		assertEquals(user.getDbName(), "110PD02");
	}

}
