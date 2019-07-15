package org.kpmp.eridanus.notifications;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PackageNotificationEventService {

	private PackageNotificationEventRepository eventRepo;
	private EmailSender emailer;
	@Value("#{'${notifications.mail.to}'.split(',')}")
	private List<String> toAddresses;

	@Autowired
	public PackageNotificationEventService(PackageNotificationEventRepository eventRepo, EmailSender emailer) {
		this.eventRepo = eventRepo;
		this.emailer = emailer;
	}

	public PackageNotificationEvent saveNotifyEvent(PackageNotificationEvent event) {
		event = eventRepo.save(event);

		return event;
	}

	public boolean sendNotifyEmail(PackageNotificationEvent packageEvent) {

		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		StringBuffer body = new StringBuffer();
		body.append("Hey ho curator!\n\n");
		body.append(
				"A new package has been uploaded to the data lake.  You might wanna take a look. Here's some info about it:\n\n");
		body.append("PACKAGE ID: " + packageEvent.getPackageId() + "\n\n");
		body.append("PACKAGE TYPE: " + packageEvent.getPackageType() + "\n\n");
		body.append("SPECIMEN ID: " + packageEvent.getSpecimenId() + "\n\n");
		body.append("DATE SUBMITTED: " + formatter.format(packageEvent.getDatePackageSubmitted()) + "\n\n");
		body.append("SUBMITTED BY: " + packageEvent.getSubmitter() + "\n\n");
		body.append("Link to data lake uploader: http://" + packageEvent.getOrigin() + "\n");
		body.append("\n\nThanks!\nYour friendly notification service.");

		return emailer.sendEmail("New package for your review from " + packageEvent.getOrigin(), body.toString(), toAddresses);
	}

}
