package org.kpmp.eridanus.notifications;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class PackageNotificationEventServiceTest {

	@Mock
	private EmailSender emailer;
	private PackageNotificationEventService service;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new PackageNotificationEventService(emailer);
		ReflectionTestUtils.setField(service, "toAddresses", Arrays.asList("rlreamy@umich.edu"));
	}

	@After
	public void tearDown() throws Exception {
		service = null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testSendNotifyEmail() throws Exception {
		PackageNotificationEvent packageEvent = new PackageNotificationEvent();
		Date dateSubmitted = new Date();
		packageEvent.setDatePackageSubmitted(dateSubmitted);
		packageEvent.setPackageId("packageId");
		packageEvent.setPackageType("package type");
		packageEvent.setSubmitter("submitter name");
		packageEvent.setSpecimenId("specimenId");
		packageEvent.setOrigin("upload.kpmp.org");
		when(emailer.sendEmail(any(String.class), any(String.class), any(List.class))).thenReturn(true);

		boolean result = service.sendNotifyEmail(packageEvent);

		assertEquals(true, result);
		ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> toAddressesCaptor = ArgumentCaptor.forClass(List.class);
		verify(emailer).sendEmail(subjectCaptor.capture(), bodyCaptor.capture(), toAddressesCaptor.capture());
		assertEquals("New package for your review from upload.kpmp.org", subjectCaptor.getValue());
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String date = formatter.format(dateSubmitted);
		assertEquals("Hey ho curator!\n" + "\n"
				+ "A new package has been uploaded to the data lake.  You might wanna take a look. Here's some info about it:\n\n"
				+ "PACKAGE ID: packageId\n\n" + "PACKAGE TYPE: package type\n\n" + "SPECIMEN ID: specimenId\n\n"
				+ "DATE SUBMITTED: " + date + "\n\n" + "SUBMITTED BY: submitter name\n\n"
				+ "Link to data lake uploader: http://upload.kpmp.org\n" + "\n" + "\n" + "Thanks!\n"
				+ "Your friendly notification service.", bodyCaptor.getValue());
		List<String> toAddresses = toAddressesCaptor.getValue();
		assertEquals(1, toAddresses.size());
		assertEquals("rlreamy@umich.edu", toAddresses.get(0));
	}

}
