package org.kpmp.eridanus.notifications;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Value("${notifications.mail.from}")
	private String fromAddress;
	@Value("${mail.host}")
	private String host;

	public void sendEmail(String subject, String body, List<String> toAddresses) throws MessagingException {

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(fromAddress);
		if (toAddresses.size() > 0) {
			for (String to : toAddresses) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
		} else {
			throw new MessagingException("No To address provided. Unable to send message.");
		}
		message.setSubject(subject);
		message.setText(body);

		Transport.send(message);

	}
}
