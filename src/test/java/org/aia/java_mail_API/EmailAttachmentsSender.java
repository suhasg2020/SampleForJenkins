/**
 * @author Pallavi Aggrawal
 */

/***************************************************/

package org.aia.java_mail_API;


import javax.mail.*;
import javax.mail.internet.*;

import org.aia.utility.Constants;
import org.aia.utility.DataProviderFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailAttachmentsSender {

	/**
	 * 1) Send N no. of attachments 2) Format set for TC count 3) Send mail to N no.
	 * of Users
	 * @throws  
	 */
	public static void sendEmailWithAttachments(String host, String port, final String userName, final String password,
			String[] toAddress, String subject, String message, String... attachFiles)
			throws AddressException, MessagingException {
		// sets SMTP server properties

		// Create object of Property file
				Properties props = new Properties();
		 
				// this will set host of server- you can change based on your requirement 
				props.put("mail.smtp.host", host);
		 
				// set the port of socket factory 
				props.put("mail.smtp.socketFactory.port", host);
		 
				// set socket factory
				props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		 
				// set the authentication to true
				props.put("mail.smtp.auth", "true");
		 
				// set the port of SMTP server
				props.put("mail.smtp.port", port);
		 
				// This will handle the complete authentication
				Session session = Session.getDefaultInstance(props,
		 
						new javax.mail.Authenticator() {
		 
							protected PasswordAuthentication getPasswordAuthentication() {
		 
							return new PasswordAuthentication(userName, password);
		 
							}
		 
						});
		 
				try {
		 
					// Create object of MimeMessage class
					Message message1 = new MimeMessage(session);
		 
					// Set the from address
					message1.setFrom(new InternetAddress(userName));
		 
					// Set the recipient address
					
					InternetAddress[] addressTo = new InternetAddress[toAddress.length]; 
					for (int i = 0; i < toAddress.length; i++) 
					  addressTo[i] = new InternetAddress(toAddress[i]);
					message1.setRecipients(Message.RecipientType.TO, addressTo);
					 
					//message1.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddress[0]));
		            
                    // Add the subject link
					message1.setSubject("Automation Execution Results!"+DataProviderFactory.getConfig().getValue("app_undertest"));
				    
					// Create object to add multimedia type content
					MimeMessage messageBodyPart1 = new MimeMessage( session );
										
					Multipart multipart = new MimeMultipart();
					
					// Set the body of email
					MimeBodyPart textPart = new MimeBodyPart();
					textPart.setText("Hello, We just got a new build to test" + "\n\nWe have run a quick test to verify the Application is smooth and up. Please check the reports. Here is a quick summary : ");
					
					MimeBodyPart htmlPart = new MimeBodyPart();
					htmlPart.setContent(message, "text/html");
					multipart.addBodyPart(htmlPart);
					
					// Create another object to add another content
					MimeBodyPart messageBodyPart = new MimeBodyPart();
		 
					// Mention the file which you want to send
					String filename = Constants.GENERATE_REPORT_PATH;
		 
					// Create data source and pass the filename
					DataSource source = new FileDataSource(filename);
		 
					// set the handler
					messageBodyPart.setDataHandler(new DataHandler(source));
		 
					// set the file
					try {
						messageBodyPart.attachFile(filename);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 
					// add body part 1
				    multipart.addBodyPart(textPart);
					multipart.addBodyPart(messageBodyPart);
					
					// set the content
					message1.setContent(multipart);
		 
					// finally send the email
					Transport.send(message1);
		 
					System.out.println("=====Email Sent=====");
		 
				} catch (MessagingException e) {
		 
					throw new RuntimeException(e);
		 
				}
		 

	}

}
