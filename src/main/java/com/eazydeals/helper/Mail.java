package com.eazydeals.helper;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Mail {

	public static void sendMail(String recipientMailId, String subject, String body) {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");

		String emailId = "nhatndpk02012@fpt.edu.vn"; // admin/company mail-id
		String password = "uulyxoxjiahvzksb"; // generated password

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailId, password);
			}
		});

		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(emailId));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientMailId));
			message.setSubject(subject);
			message.setContent(body, "text/html;charset=UTF-8");

			Transport.send(message);
			// System.out.println("Message Sent Successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
