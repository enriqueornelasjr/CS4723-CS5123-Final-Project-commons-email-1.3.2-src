package org.apache.commons.mail;

import static org.junit.Assert.assertThrows;

import java.util.HashMap;

import javax.mail.Session;
import javax.mail.internet.MimeMultipart;

import junit.framework.TestCase;

import java.util.Date;
import org.junit.BeforeClass;


public class ExtraEmailTest extends TestCase {

	private static final String HOST = "smtp.gmail.com";
	private static final int PORT = 465;
	private static final boolean SSL_FLAG = true;
	private static boolean printedDescription = false;
	SimpleEmailTester email = new SimpleEmailTester();

	@Override
	protected void setUp() throws EmailException {
        System.out.println("setup from extra");
		if(!printedDescription) {	
			System.out.println("This is class ExtraEmailTest, and it is aimed at testing class SimpleEmail with extra tests");
			System.out.println("gml___");
			printedDescription = true;
		}
		String userName = "username@gmail.com";
		String password = "password";
		email.setHostName(HOST);
		email.setSmtpPort(PORT);

		email.setAuthenticator(new DefaultAuthenticator(userName, password));
		email.setSSLOnConnect(SSL_FLAG);
		email.setFrom("user1@gmail.com");
	}
	
	public void testAddHeader() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			email.addHeader("Header", "");
		});
		String exceptionMessage = exception.getMessage();
		String expectedMessage = "value can not be null or empty";
		assertEquals(expectedMessage, exceptionMessage);

	}
	
	public void testUpdateContentType() throws EmailException {
		String contentType = "multipart/alternative";
		email.updateContentType(contentType);
		assertEquals(contentType, email.contentType);
	}

	public void tearDown() {
		email = null;
	}

}
