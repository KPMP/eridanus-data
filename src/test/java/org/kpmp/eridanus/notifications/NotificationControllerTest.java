package org.kpmp.eridanus.notifications;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kpmp.eridanus.notifications.NotificationController;
import org.kpmp.eridanus.notifications.PackageNotificationEvent;
import org.kpmp.eridanus.notifications.PackageNotificationEventService;
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
		PackageNotificationEvent initialEvent = new PackageNotificationEvent();
		PackageNotificationEvent expectedEvent = mock(PackageNotificationEvent.class);
		when(packageEventService.saveNotifyEvent(any(PackageNotificationEvent.class))).thenReturn(expectedEvent);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getServerName()).thenReturn("test.kpmp.org");
		when(packageEventService.sendNotifyEmail(expectedEvent)).thenReturn(true);

		Boolean success = controller.notifyNewPackage(initialEvent, request);

		verify(packageEventService).saveNotifyEvent(initialEvent);
		verify(packageEventService).sendNotifyEmail(expectedEvent);
		assertEquals(true, success);
	}

}
