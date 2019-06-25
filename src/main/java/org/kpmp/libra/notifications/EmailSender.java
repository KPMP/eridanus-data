package org.kpmp.libra.notifications;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private String fromAddress = "kpmpNotifications@gmail.com";
	private String host = "postfix";

	public boolean sendEmail(String subject, String body, String[] toAddresses) {

		boolean successful = false;
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(fromAddress);
			if (toAddresses.length > 0) {
				for (String to : toAddresses) {
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				}
			} else {
				log.error("No To adresses provided, unable to send message.");
				return false;
			}
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);
			successful = true;

		} catch (MessagingException exception) {
			System.err.println(exception.getMessage());
			log.error("Hit error sending email: {}", exception.getMessage());
		}

		return successful;
	}
}
