package org.kpmp.eridanus.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StateChangeEventTest extends StateChangeEvent {

	private StateChangeEvent event;

	@BeforeEach
	public void setUp() throws Exception {
		event = new StateChangeEvent();
	}

	@AfterEach
	public void tearDown() throws Exception {
		event = null;
	}

	@Test
	public void testSetState() {
		event.setState("new state");
		assertEquals("new state", event.getState());
	}

	@Test
	public void testSetPackageId() throws Exception {
		event.setPackageId("packageId");
		assertEquals("packageId", event.getPackageId());
	}

	@Test
	public void testSetOrigin() throws Exception {
		event.setOrigin("origin-dev_upload_com");
		assertEquals("origin-dev.upload.com", event.getOrigin());
	}

	@Test
	public void testSetCodicil() throws Exception {
		event.setCodicil("codicil");
		assertEquals("codicil", event.getCodicil());
	}
}
