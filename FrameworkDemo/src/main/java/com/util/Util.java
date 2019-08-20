package com.util;

import java.io.File;


import java.io.FileInputStream;
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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.testbase.Baseclass;


public class Util extends Baseclass{
	public static File propfile;
	public static FileInputStream fileInput;
	public static Properties prop;
	String propfilepath = System.getProperty("user.dir") + "\\src\\main\\resources\\demo.properties";

	public void click(By locator) {
		driver.findElement(locator).click();
	}
	
	public void sendkeys(By locator,String value) {
		driver.findElement(locator).sendKeys(value);
	}

	public void waittillElementvisible(WebDriver driver,By element) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}
	
	public String getPropertydata(String property) throws Exception {
		propfile = new File(propfilepath);

		fileInput = new FileInputStream(propfile);

		prop = new Properties();
		// load properties file
		prop.load(fileInput);
		
		return prop.getProperty(property);
	}
	public void sendEmail() {

		// Create object of Property file
		Properties props = new Properties();

		// this will set host of server- you can change based on your requirement
		props.put("mail.smtp.host", "smtp.gmail.com");

		// set the port of socket factory
		props.put("mail.smtp.socketFactory.port", "465");

		// set socket factory
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		// set the authentication to true
		props.put("mail.smtp.auth", "true");

		// set the port of SMTP server
		props.put("mail.smtp.port", "465");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,

				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication("travrogersmith@gmail.com", "Simform.123");

					}

				});

		try {

			// Create object of MimeMessage class
			Message message = new MimeMessage(session);

			// Set the from address
			message.setFrom(new InternetAddress("travrogersmith@gmail.com"));

			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("yarajput1996@gmail.com"));

			// Add the subject link
			message.setSubject("Automation Test Report");

			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();

			// Set the body of email
			messageBodyPart1.setText("Please find automation report in attachment");

			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			// Mention the file which you want to send
			String filename = System.getProperty("user.dir") + "\\extentreport.html";

			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);

			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));

			// set the file
			messageBodyPart2.setFileName(filename);

			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();

			// add body part 1
			multipart.addBodyPart(messageBodyPart2);

			// add body part 2
			multipart.addBodyPart(messageBodyPart1);

			// set the content
			message.setContent(multipart);

			// finally send the email
			Transport.send(message);

			System.out.println("=====Email Sent=====");

		} catch (MessagingException e) {

			throw new RuntimeException(e);

		}
	}
	
	
	
	
}