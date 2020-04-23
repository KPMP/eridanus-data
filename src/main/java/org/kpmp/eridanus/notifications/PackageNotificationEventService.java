package org.kpmp.eridanus.notifications;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PackageNotificationEventService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private EmailSender emailer;
	@Value("#{'${notifications.mail.to}'.split(',')}")
	private List<String> toAddresses;
	@Value("${package.state.upload.succeeded}")
	private String uploadSuccess;
	@Value("${package.state.upload.failed}")
	private String uploadFail;
	private PackageRepository packageRepository;

	@Autowired
	public PackageNotificationEventService(EmailSender emailer, PackageRepository packageRepository) {
		this.emailer = emailer;
		this.packageRepository = packageRepository;
	}

	public void sendNotifyEmail(StateChangeEvent event) throws MessagingException {
		Package packageInfo = packageRepository.findByPackageId(event.getPackageId());

		String packageState = event.getState();
		if (packageState.equalsIgnoreCase(uploadSuccess)) {
			sendSuccessEmail(packageInfo, event);
		} else if (packageState.equalsIgnoreCase(uploadFail)) {
			sendFailureEmail(packageInfo, event);
		}
		log.info("URI: PackageNotificationEventService.sendNotifyEmail | PKGID: {} | MSG: {}", event.getPackageId(),
				"No notifications defined for this state: " + packageState);
	}

	private void sendSuccessEmail(Package packageInfo, StateChangeEvent event) throws MessagingException {
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		StringBuffer body = new StringBuffer();
		body.append("Hey ho curator!\n\n");
		body.append(
				"A new package has been uploaded to the data lake.  You might wanna take a look. Here's some info about it:\n\n");
		body.append("PACKAGE ID: " + packageInfo.getPackageId() + "\n\n");
		body.append("PACKAGE TYPE: " + packageInfo.getPackageType() + "\n\n");
		body.append("SPECIMEN ID: " + packageInfo.getSubjectId() + "\n\n");
		body.append("DATE SUBMITTED: " + formatter.format(packageInfo.getCreatedAt()) + "\n\n");
		body.append("SUBMITTED BY: " + packageInfo.getSubmitter().getFirstName() + " "
				+ packageInfo.getSubmitter().getLastName() + "\n\n");
		body.append("Link to data lake uploader: http://" + event.getOrigin() + "\n");
		body.append("\n\nThanks!\nYour friendly notification service.");

		emailer.sendEmail("New package for your review from " + event.getOrigin(), body.toString(), toAddresses);
	}

	private void sendFailureEmail(Package packageInfo, StateChangeEvent event) throws MessagingException {
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		StringBuffer body = new StringBuffer();
		body.append("Hey ho curator!\n\n");
		body.append("A new package has failed uploading.  You might wanna take a look. Here's some info about it:\n\n");
		body.append("FAILURE REASON: " + event.getCodicil() + "\n\n");
		body.append("PACKAGE ID: " + packageInfo.getPackageId() + "\n\n");
		body.append("PACKAGE TYPE: " + packageInfo.getPackageType() + "\n\n");
		body.append("SPECIMEN ID: " + packageInfo.getSubjectId() + "\n\n");
		body.append("DATE SUBMITTED: " + formatter.format(packageInfo.getCreatedAt()) + "\n\n");
		body.append("SUBMITTED BY: " + packageInfo.getSubmitter().getFirstName() + " "
				+ packageInfo.getSubmitter().getLastName() + "\n\n");
		body.append("Link to data lake uploader: http://" + event.getOrigin() + "\n");
		body.append("\n\nThanks!\nYour friendly notification service.");

		emailer.sendEmail("FAILED package for your review from " + event.getOrigin(), body.toString(), toAddresses);
	}

}
