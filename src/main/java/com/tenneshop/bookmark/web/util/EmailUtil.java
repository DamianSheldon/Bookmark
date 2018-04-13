package com.tenneshop.bookmark.web.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	
	public static boolean email(String from, String to, String subject, String text) {
	      // Recipient's email ID needs to be mentioned.
//	      String to = "abcd@gmail.com";

	      // Sender's email ID needs to be mentioned
//	      String from = "web@gmail.com";

	      // Assuming you are sending email from localhost
//	      String host = "localhost";
	      String host = "smtp.sina.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);
	      properties.put("mail.smtp.auth", "true");
	      
	      // Get the default Session object.
//	      Session session = Session.getDefaultInstance(properties);
	      Session session = Session.getDefaultInstance(properties,
	              new javax.mail.Authenticator() {
	                  protected PasswordAuthentication getPasswordAuthentication() {
	                      return new PasswordAuthentication("xxx@xxx.com", "xxx");
	              }
	          });
	      
	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Now set the actual message
	         message.setText(text);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	         
	         return true;
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		
		return false;
	}
}
