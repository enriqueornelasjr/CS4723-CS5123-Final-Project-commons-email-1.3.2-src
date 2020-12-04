package org.apache.commons.mail;

import static org.junit.Assert.assertThrows;

import java.util.HashMap;

import javax.mail.Session;
import javax.mail.internet.MimeMultipart;

import junit.framework.TestCase;

import java.util.Date;
import org.junit.BeforeClass;
/*
 * This class and method were created to overwrite Email.java's sendMimeMessage() and
 * making believe the email was sent and returned an ID for it
 * 
 */
class SimpleEmailTester extends SimpleEmail {

	public String sendMimeMessage() {
		return "QlebYH";
	}
}

public class EmailTest extends TestCase {

	private static final String HOST = "smtp.gmail.com";
	private static final int PORT = 465;
	private static final boolean SSL_FLAG = true;
	private static boolean printed = false;
	// email and emailNoSender are the SimpleEmail object used throughout the tests,
	// but emailNoSender does not have a from property
	SimpleEmailTester email = new SimpleEmailTester();
	SimpleEmailTester emailNoSender = new SimpleEmailTester();

	@Override
	/*
	 * setUp() initializes the email and emailNoSender to their default values to
	 * begin testing
	 */
	protected void setUp() throws EmailException {
		if(!printed) {	
			System.out.println("This is class EmailTest, and it is aimed at testing class SimpleEmail");
			System.out.println("gml___");
			printed = true;
		}
		String userName = "username@gmail.com";
		String password = "password";
		email.setHostName(HOST);
		emailNoSender.setHostName(HOST);
		email.setSmtpPort(PORT);
		emailNoSender.setSmtpPort(PORT);

		email.setAuthenticator(new DefaultAuthenticator(userName, password));
		emailNoSender.setAuthenticator(new DefaultAuthenticator(userName, password));
		email.setSSLOnConnect(SSL_FLAG);
		emailNoSender.setSSLOnConnect(SSL_FLAG);
		email.setFrom("user1@gmail.com");
	}

	/*
	 * testAddBcc1() ensures an exception is thrown when providing an empty list of
	 * emails to addBcc()
	 */
	public void testAddBcc1() throws EmailException {
		String[] sendTo = null;
		Exception exception = assertThrows(EmailException.class, () -> {
			email.addBcc(sendTo);
		});
		String expectedMessage = "Address List provided was invalid";
		String exceptionMessage = exception.getMessage();
		assertEquals(expectedMessage, exceptionMessage);
	}

	/*
	 * testAddBcc2() ensures an Email Exception is thrown when providing unformatted
	 * emails to addBcc()
	 */
	public void testAddBcc2() throws EmailException {
		String[] sendTo = { "notvalid" };
		Exception exception = assertThrows(EmailException.class, () -> {
			email.addBcc(sendTo);
		});
		String expectedMessage = "javax.mail.internet.AddressException: Missing final '@domain' in string ``notvalid''";
		String exceptionMessage = exception.getMessage();
		assertEquals(expectedMessage, exceptionMessage);
	}

	/*
	 * testAddBcc3() ensures an Email Exception is thrown when providing unformatted
	 * emails to addBcc()
	 */
	public void testAddBcc3() throws EmailException {
		String[] sendTo = { "" };
		Exception exception = assertThrows(EmailException.class, () -> {
			email.addBcc(sendTo);
		});
		String expectedMessage = "javax.mail.internet.AddressException: Illegal address in string ``''";
		String exceptionMessage = exception.getMessage();
		assertEquals(expectedMessage, exceptionMessage);
	}

	/*
	 * testAddBcc4() provides a valid list of emails to addBcc() and ensures they
	 * were added successfully
	 */
	public void testAddBcc4() throws EmailException {
		String[] sendTo = { "user1@gmail.com", "user2@gmail.com", "user3@gmail.com ", " user4@gmail.com" };
		email.addBcc(sendTo);
		assertEquals(sendTo.length, email.getBccAddresses().size());
		for (int i = 0; i < sendTo.length; i++) {
			String to = sendTo[i].trim();
			assertEquals(to, email.getBccAddresses().get(i).toString());
			assertEquals(to, email.getBccAddresses().get(i).toString());
			assertEquals(to.trim(), email.getBccAddresses().get(i).toString());
		}
	}

	/*
	 * testAddCc1() ensures an exception is thrown when providing an empty list of
	 * emails to addCc()
	 */
	public void testAddCc1() throws EmailException {
		String sendTo = null;
		Exception exception = assertThrows(NullPointerException.class, () -> {
			email.addCc(sendTo);
		});
		String exceptionMessage = exception.getMessage();
		assertNull(exceptionMessage);
	}

	/*
	 * testAddCc3() ensures an Email Exception is thrown when providing unformatted
	 * emails to addCc()
	 */
	public void testAddCc2() throws EmailException {
		String sendTo = "";
		Exception exception = assertThrows(EmailException.class, () -> {
			email.addCc(sendTo);
		});
		String expectedMessage = "javax.mail.internet.AddressException: Illegal address in string ``''";
		String exceptionMessage = exception.getMessage();
		assertEquals(expectedMessage, exceptionMessage);
	}

	/*
	 * testAddCc4() adds valid email addresses to addCc() and ensures they were
	 * added successfully
	 */
	public void testAddCc3() throws EmailException {
		String[] sendTo = { "user1@gmail.com", "user2@gmail.com", " user3@gmail.com", "user4@gmail.com " };
		email.addCc(sendTo);
		assertEquals(sendTo.length, email.getCcAddresses().size());
		for (int i = 0; i < sendTo.length; i++) {
			String to = sendTo[i].trim();
			assertEquals(to, email.getCcAddresses().get(i).toString());
			assertEquals(to, email.getCcAddresses().get(i).toString());
			assertEquals(to, email.getCcAddresses().get(i).toString());
		}
	}

	/*
	 * testAddHeader1() ensures an IllegalArgumentException is thrown when providing
	 * an empty header to addHeader()
	 */
	public void testAddHeader1() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			email.addHeader("", "");
		});
		String exceptionMessage = exception.getMessage();
		String expectedMessage = "name can not be null or empty";
		assertEquals(expectedMessage, exceptionMessage);

	}

	/*
	 * testAddHeader2() ensures an IllegalArgumentException is thrown when providing
	 * a null header to addHeader()
	 */
	public void testAddHeader2() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			email.addHeader(null, null);
		});
		String exceptionMessage = exception.getMessage();
		String expectedMessage = "name can not be null or empty";
		assertEquals(expectedMessage, exceptionMessage);

	}

	/*
	 * testAddHeader3() passes valid headers to addHeader() and ensures they were
	 * added successfully
	 */
	public void testAddHeader3() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("HeaderName1", "HeaderValue1");
		headers.put("HeaderName 2", "HeaderValue2");
		for (String key : headers.keySet()) {
			email.addHeader(key, headers.get(key));
		}
		assertEquals(headers.size(), email.headers.size());
		for (String key : headers.keySet()) {
			assertEquals(headers.get(key), email.headers.get(key));
		}
	}

	/*
	 * testAddReplyTo1() ensures an exception is thrown when supplying null as a
	 * parameter to addReplyTo()
	 */
	public void testAddReplyTo1() throws EmailException {
		Exception exception = assertThrows(NullPointerException.class, () -> {
			email.addReplyTo(null, null);
		});
		String exceptionMessage = exception.getMessage();
		assertNull(exceptionMessage);
	}

	/*
	 * testAddReplyTo2() ensures an exception is thrown when supplying an empty
	 * string as a parameter to addReplyTo()
	 */
	public void testAddReplyTo2() throws EmailException {
		Exception exception = assertThrows(EmailException.class, () -> {
			email.addReplyTo("", "");
		});
		String expectedMessage = "javax.mail.internet.AddressException: Illegal address in string ``''";
		String exceptionMessage = exception.getMessage();
		assertEquals(expectedMessage, exceptionMessage);
	}

	/*
	 * testAddReplyTo3() passes valid emails to addReplyTo() and ensures they are
	 * added successfully
	 */
	public void testAddReplyTo3() throws EmailException {
		String[] replyTo = { "user1@gmail.com", "user2@gmail.com", " user3@gmail.com", "user4@gmail.com " };
		for (String s : replyTo)
			email.addReplyTo(s);
		assertEquals(replyTo.length, email.getReplyToAddresses().size());
		for (int i = 0; i < replyTo.length; i++) {
			String to = replyTo[i].trim();
			assertEquals(to, email.getReplyToAddresses().get(i).toString());
			assertEquals(to, email.getReplyToAddresses().get(i).toString());
			assertEquals(to, email.getReplyToAddresses().get(i).toString());
		}
	}

	/*
	 * testBuildMimeMessage1() ensures a mimeMessage is generated with a proper
	 * recipient
	 */
	public void testBuildMimeMessage1() throws EmailException {
		email.setSubject("Test Subject");
		email.setContent("Hello", EmailConstants.TEXT_PLAIN);
		email.addTo("TestUser2@gmail.com");
		email.buildMimeMessage();
	}

	/*
	 * testBuildMimeMessage2() ensures a mimeMessage is generated with a proper CC
	 * recipient
	 */
	public void testBuildMimeMessage2() throws EmailException {
		email.setSubject("Test Subject");
		email.setContent("Hello", EmailConstants.TEXT_PLAIN);
		email.addCc("TestUser2@gmail.com");
		email.buildMimeMessage();
	}

	/*
	 * testBuildMimeMessage3() ensures a mimeMessage is generated with a proper BCC
	 * recipient
	 */
	public void testBuildMimeMessage3() throws EmailException {
		email.setSubject("Test Subject");
		email.setContent("Hello", EmailConstants.TEXT_PLAIN);
		email.addBcc("TestUser2@gmail.com");
		email.buildMimeMessage();
	}

	/*
	 * testBuildMimeMessage4() ensures a mimeMessage is generated with a call to
	 * replyTo()
	 */
	public void testBuildMimeMessage4() throws EmailException {
		email.setSubject("Test Subject");
		email.setContent("Hello", EmailConstants.TEXT_PLAIN);
		email.addBcc("TestUser2@gmail.com");
		email.addReplyTo("TestUser2@gmail.com");
		email.buildMimeMessage();
	}

	/*
	 * testBuildMimeMessage5() ensures an EmailException is thrown when no
	 * recipients are supplied
	 */
	public void testBuildMimeMessage5() throws EmailException {
		email.setSubject("Test Subject");
		email.setContent("Hello", EmailConstants.TEXT_PLAIN);

		Exception exception = assertThrows(EmailException.class, () -> {
			email.buildMimeMessage();
		});
		String exceptionMessage = exception.getMessage();
		String expectedMessage = "At least one receiver address required";
		assertEquals(expectedMessage, exceptionMessage);

	}

	/*
	 * testBuildMimeMessage6() ensures an EmailException is thrown when a sender is
	 * not specified
	 */
	public void testBuildMimeMessage6() throws EmailException {
		emailNoSender.setSubject("Test Subject");
		emailNoSender.setContent("Hello", EmailConstants.TEXT_PLAIN);

		Exception exception = assertThrows(EmailException.class, () -> {
			emailNoSender.buildMimeMessage();
		});
		String exceptionMessage = exception.getMessage();
		String expectedMessage = "From address required";
		assertEquals(expectedMessage, exceptionMessage);

	}

	/*
	 * testBuildMimeMessage7() ensures buildMimeMessage works with a TEXT_HTML
	 * message type
	 */
	public void testBuildMimeMessage7() throws EmailException {
		email.setSubject("Test Subject");
		email.setContent("<h1>Hello!</h1>", EmailConstants.TEXT_HTML);
		email.addBcc("TestUser2@gmail.com");
		email.addReplyTo("TestUser2@gmail.com");
		email.buildMimeMessage();

	}

	/*
	 * testBuildMimeMessage8() ensures buildMimeMessage works with a MimeMultipart
	 * message type
	 */
	public void testBuildMimeMessage8() throws EmailException {
		email.setSubject("Test Subject");
		MimeMultipart mime = new MimeMultipart("MIME-Version: 1.0\r\n" + " X-Mailer: MailBee.NET 8.0.4.428\r\n"
				+ " Subject: This is the subject of a sample message\r\n" + " To: user@example.com\r\n"
				+ " Content-Type: multipart/alternative;\r\n" + " boundary=\"XXXXboundary text\"\r\n" + "\r\n"
				+ "--XXXXboundary text\r\n" + " Content-Type: text/plain;\r\n" + " charset=\"utf-8\"\r\n"
				+ " Content-Transfer-Encoding: quoted-printable\r\n" + "\r\n"
				+ "This is the body text of a sample message.\r\n" + "\r\n" + "--XXXXboundary text\r\n"
				+ " Content-Type: text/html;\r\n" + " charset=\"utf-8\"\r\n"
				+ " Content-Transfer-Encoding: quoted-printable\r\n" + "\r\n"
				+ "<pre>This is the body text of a sample message.</pre>\r\n" + "--XXXXboundary text--");
		email.setContent(mime);
		email.updateContentType("multipart/alternative");
		email.addBcc("TestUser2@gmail.com");
		email.addReplyTo("TestUser2@gmail.com");
		email.buildMimeMessage();

	}

	/*
	 * testBuildMimeMessage9() ensures buildMimeMessage works with a MimeMultipart
	 * message type but with no updateContentType() call
	 */
	public void testBuildMimeMessage9() throws EmailException {
		email.setSubject("Test Subject");
		MimeMultipart mime = new MimeMultipart("MIME-Version: 1.0\r\n" + " X-Mailer: MailBee.NET 8.0.4.428\r\n"
				+ " Subject: This is the subject of a sample message\r\n" + " To: user@example.com\r\n"
				+ " Content-Type: multipart/alternative;\r\n" + " boundary=\"XXXXboundary text\"\r\n" + "\r\n"
				+ "--XXXXboundary text\r\n" + " Content-Type: text/plain;\r\n" + " charset=\"utf-8\"\r\n"
				+ " Content-Transfer-Encoding: quoted-printable\r\n" + "\r\n"
				+ "This is the body text of a sample message.\r\n" + "\r\n" + "--XXXXboundary text\r\n"
				+ " Content-Type: text/html;\r\n" + " charset=\"utf-8\"\r\n"
				+ " Content-Transfer-Encoding: quoted-printable\r\n" + "\r\n"
				+ "<pre>This is the body text of a sample message.</pre>\r\n" + "--XXXXboundary text--");
		email.setContent(mime);
		email.addBcc("TestUser2@gmail.com");
		email.addReplyTo("TestUser2@gmail.com");
		email.buildMimeMessage();

	}

	/*
	 * testBuildMimeMessage10() ensures buildMimeMessage works with an email with no
	 * body
	 */
	public void testBuildMimeMessage10() throws EmailException {
		email.setSubject("Test Subject");
		email.addBcc("TestUser2@gmail.com");
		email.addReplyTo("TestUser2@gmail.com");
		email.buildMimeMessage();
	}

	/*
	 * testBuildMimeMessage11() ensures buildMimeMessage works with a header
	 * supplied
	 */
	public void testBuildMimeMessage11() throws EmailException {
		email.setSubject("Test Subject");
		email.addBcc("TestUser2@gmail.com");
		email.setContent("Hello", EmailConstants.TEXT_PLAIN);
		email.addReplyTo("TestUser2@gmail.com");
		email.addHeader("Header1", "HeaderValue1");
		email.buildMimeMessage();
	}

	/*
	 * testBuildMimeMessage12() ensures an IllegalStateException when it is called
	 * twice
	 */
	public void testBuildMimeMessage12() throws EmailException {

		email.setSubject("Test Subject");
		email.addBcc("TestUser2@gmail.com");
		emailNoSender.setContent("Hello", EmailConstants.TEXT_PLAIN);
		email.addReplyTo("TestUser2@gmail.com");
		email.addHeader("Header1", "HeaderValue1");
		email.buildMimeMessage();
		Exception exception = assertThrows(IllegalStateException.class, () -> {
			email.buildMimeMessage();
		});

		String exceptionMessage = exception.getMessage();
		String expectedMessage = "The MimeMessage is already built.";
		assertEquals(expectedMessage, exceptionMessage);
	}

	/*
	 * testBuildMimeMessage13() ensures an EmailException is thrown when the POP
	 * server is used but cannot be reached
	 */
	public void testBuildMimeMessage13() throws EmailException {

		email.setSubject("Test Subject");
		email.addBcc("TestUser2@gmail.com");
		emailNoSender.setContent("Hello", EmailConstants.TEXT_PLAIN);
		email.addReplyTo("TestUser2@gmail.com");
		email.addHeader("Header1", "HeaderValue1");
		email.setPopBeforeSmtp(true, "", "", "");

		Exception exception = assertThrows(EmailException.class, () -> {
			email.buildMimeMessage();
		});

		String exceptionMessage = exception.getMessage();
		assertTrue(exceptionMessage.toLowerCase().contains("connect failed"));
	}

	/*
	 * testGetHostName() ensures it returns the host name successfully
	 */
	public void testGetHostName() throws EmailException {
		assertEquals(HOST, email.getHostName());
	}

	/*
	 * testGetMailSession1() ensures an EmailException is thrown in the case of a
	 * valid email without a host set
	 */
	public void testGetMailSession1() throws EmailException {
		Email tempEmail = new SimpleEmail();
		tempEmail.setSubject("Test Subject");
		tempEmail.addBcc("TestUser2@gmail.com");
		tempEmail.setContent("Hello", EmailConstants.TEXT_PLAIN);
		tempEmail.addReplyTo("TestUser2@gmail.com");
		tempEmail.addHeader("Header1", "HeaderValue1");
		Exception exception = assertThrows(EmailException.class, () -> {
			tempEmail.buildMimeMessage();
		});

		String exceptionMessage = exception.getMessage();
		String expectedMessage = "Cannot find valid hostname for mail session";
		assertEquals(expectedMessage, exceptionMessage);
	}

	/*
	 * testGetMailSession2() ensures an getMailSession() reads the host from the
	 * System's properties in the case one is not supplied
	 */
	public void testGetMailSession2() throws EmailException {
		String tempHost = "temp-host";
		System.setProperty(EmailConstants.MAIL_HOST, tempHost);
		Email tempEmail = new SimpleEmail();
		tempEmail.getMailSession();
		assertEquals(tempHost, tempEmail.getHostName());
	}

	/*
	 * testGetMailSession3() ensures an getMailSession() successfully sets the
	 * attribute for checking the server's identity
	 */
	public void testGetMailSession3() throws EmailException {
		String tempHost = "temp-host";
		System.setProperty(EmailConstants.MAIL_HOST, tempHost);
		Email tempEmail = new SimpleEmail();
		tempEmail.setSSL(true);
		tempEmail.setSSLCheckServerIdentity(true);
		Session s = tempEmail.getMailSession();
		assertEquals("true", s.getProperty(EmailConstants.MAIL_SMTP_SSL_CHECKSERVERIDENTITY));
	}

	/*
	 * testGetSocketConnectionTimeout() ensures the socketConnectionTimeout is set
	 * to the default value, 60000
	 */
	public void testGetSocketConnectionTimeout() throws EmailException {
		assertEquals(60000, email.getSocketConnectionTimeout());
	}

	/*
	 * testGetSentDate1() ensures the current date is returned prior to delivery of
	 * the email
	 */
	public void testGetSentDate1() throws EmailException {
		assertEquals(email.getSentDate(), new Date());
	}

	/*
	 * testGetSentDate2() ensures the time date the email was sent is accurate with
	 * a threshold of 5 seconds
	 */
	public void testGetSentDate2() throws EmailException, InterruptedException {
		email.setSubject("Test Subject");
		email.setContent("Hello", EmailConstants.TEXT_PLAIN);
		email.addTo("TestUser2@gmail.com");
		Date dateSent = new Date();
		email.setSentDate(dateSent);
		email.send();
		assertTrue(Math.abs(new Date().getTime() - dateSent.getTime()) < 5000);

	}

	/*
	 * testSend1() ensures a EmailException is thrown when no recipient is supplied
	 */
	public void testSend1() throws EmailException {
		email.setSubject("Test Subject");
		email.setContent("Hello", EmailConstants.TEXT_PLAIN);

		Exception exception = assertThrows(EmailException.class, () -> {
			email.send();
		});
		String exceptionMessage = exception.getMessage();
		String expectedMessage = "At least one receiver address required";
		assertEquals(expectedMessage, exceptionMessage);
	}

	/*
	 * testSend2() sends a valid email and confirms an ID was generated
	 */
	public void testSend2() throws EmailException {
		email.setSubject("Test Subject");
		email.setContent("Hello", EmailConstants.TEXT_PLAIN);
		email.addTo("TestUser2@gmail.com");
		assertEquals("QlebYH", email.send());
	}

	/*
	 * testSetFrom1() ensures an AdressException is thrown when setFrom is supplied
	 * an empty string
	 */
	public void testSetFrom1() throws EmailException {
		Exception exception = assertThrows(EmailException.class, () -> {
			email.setFrom("");
		});
		String exceptionMessage = exception.getMessage();
		String expectedMessage = "javax.mail.internet.AddressException: Illegal address in string ``''";
		assertEquals(expectedMessage, exceptionMessage);
	}

	/*
	 * testSetFrom2() ensures an NullPointerException is thrown when setFrom is
	 * supplied a null string
	 */
	public void testSetFrom2() throws EmailException {
		Exception exception = assertThrows(NullPointerException.class, () -> {
			email.setFrom(null);
		});
		String exceptionMessage = exception.getMessage();
		assertEquals(null, exceptionMessage);
	}

	/*
	 * testSetFrom3() passes valid emails to setFrom and ensures they were set
	 * correctly
	 */
	public void testSetFrom3() throws EmailException {
		String from[] = { "user1@gmail.com", " user1@gmail.com", "user1@gmail.com " };
		for (int i = 0; i < from.length; i++) {
			emailNoSender.setFrom(from[i]);
			assertEquals(from[i].trim(), email.getFromAddress().toString());
		}
	}

	/*
	 * testUpdateContentType() ensures the content is changed when supplied a
	 * content type
	 */
	public void testUpdateContentType() throws EmailException {
		String contentType = "multipart/alternative";
		email.updateContentType(contentType);
		assertEquals(contentType, email.contentType);
	}

	/*
	 * tearDown() is called at the end of JUnit's lifetime and cleans up the emails
	 * that were created
	 */
	public void tearDown() {
		email = null;
		emailNoSender = null;
	}

}
