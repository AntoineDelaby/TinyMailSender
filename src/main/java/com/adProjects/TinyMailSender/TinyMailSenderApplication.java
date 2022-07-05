package com.adProjects.TinyMailSender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@SpringBootApplication
public class TinyMailSenderApplication {

	public static void main(String[] args) throws IOException, MessagingException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String username = "from@gmail.com";
		String password = "*****";

		String host = "smtp.gmail.com";
		Properties prop = new Properties();
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("from@gmail.com"));
		message.setRecipients(
				Message.RecipientType.TO, InternetAddress.parse("to@gmail.com"));
		message.setSubject("Mail subject");

		String msg = "Mail content";

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);

		Transport.send(message);

		SpringApplication.run(TinyMailSenderApplication.class, args);
	}

}
