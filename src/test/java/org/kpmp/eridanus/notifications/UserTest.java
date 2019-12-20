package org.kpmp.eridanus.notifications;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private User user;

	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@After
	public void tearDown() throws Exception {
		user = null;
	}

	@Test
	public void testSetId() {
		user.setId("id");
		assertEquals("id", user.getId());
	}

	@Test
	public void testSetFirstName() {
		user.setFirstName("firstName");
		assertEquals("firstName", user.getFirstName());
	}

	@Test
	public void testSetLastName() {
		user.setLastName("lastName");
		assertEquals("lastName", user.getLastName());
	}

}
