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
		String username = reader.readLine();
		System.out.println(username);
		String password = reader.readLine();
		System.out.println(password);

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
		System.out.println("from :");
		message.setFrom(new InternetAddress(reader.readLine()));
		System.out.println("to :");
		message.setRecipients(
				Message.RecipientType.TO, InternetAddress.parse(reader.readLine()));
		System.out.println("mail subject :");
		message.setSubject(reader.readLine());

		System.out.println("Mail content : \n");
		String msg = reader.readLine();

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);

		Transport.send(message);
	}

}
