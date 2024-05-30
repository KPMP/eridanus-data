package org.kpmp.eridanus.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

	private User user;

	@BeforeEach
	public void setUp() throws Exception {
		user = new User();
	}

	@AfterEach
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
