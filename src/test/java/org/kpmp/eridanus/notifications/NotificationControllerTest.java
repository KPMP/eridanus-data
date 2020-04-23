package org.kpmp.eridanus.notifications;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotificationControllerTest {

	@Mock
	private PackageNotificationEventService packageEventService;
	private NotificationController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new NotificationController(packageEventService);
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
	}

	@Test
	public void testNotifyNewPackage() {
		StateChangeEvent initialEvent = new StateChangeEvent();
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getServerName()).thenReturn("test.kpmp.org");

		Boolean success = controller.notify(initialEvent, request);

		assertEquals(true, success);
	}

	@Test
	public void testNotifyNewPackage_whenException() throws Exception {
		StateChangeEvent initialEvent = new StateChangeEvent();
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getServerName()).thenReturn("test.kpmp.org");
		doThrow(new MessagingException()).when(packageEventService).sendNotifyEmail(initialEvent);

		Boolean success = controller.notify(initialEvent, request);

		assertEquals(false, success);
	}

}
