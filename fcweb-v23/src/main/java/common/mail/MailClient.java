package common.mail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.smtp.SMTPMessage;

public class MailClient implements ConnectionListener,TransportListener{

	private static final Logger log = LoggerFactory.getLogger(MailClient.class);

	private static Properties mailProps = null;

	boolean debug = true;

	String mailServer = null;
	String user = null;
	String password = null;
	String mailer = this.getClass().getName();

	String ambiente = null;
	String mitt = null;

	/**
	 * @return Returns the mailServer.
	 */
	public String getMailServer() {
		return mailServer;
	}

	/**
	 * @param mailServer
	 *            The mailServer to set.
	 */
	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            The user to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}

	public MailClient(Properties p) {

		try {

			mailProps = p;

			mailServer = mailProps.getProperty("mail.smtp.host");
			user = mailProps.getProperty("mail.user");
			password = mailProps.getProperty("mail.password");

			ambiente = mailProps.getProperty("ambiente");
			mitt = mailProps.getProperty("from");

			log.info(" ambiente: " + ambiente);
			log.info(" mailServer: " + mailServer);
			log.info(" user: " + user);
			log.info(" password: " + password);
			log.info(" from: " + mitt);

		} catch (Exception ex) {
			log.error("Error while reading properties ", ex);
		}

	}

	public void sendMail(String from, String[] to, String[] cc, String[] bcc,
			String subject, String messageBody, String typeMessage,
			String priority, String[] attachments) throws IOException,
			MessagingException, AddressException, NamingException {

		log.info("****************************************");
		log.info("INIZIO ESECUZIONE sendMail              ");
		log.info("****************************************");

		if (from == null) {
			from = mitt;
		}
		log.info(" from: " + from);
		log.info(" subject: " + subject);
		log.info(" message: " + messageBody);

		Session session = buildSession();

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));

		InternetAddress[] addressTo = new InternetAddress[to.length];
		for (int t = 0; t < to.length; t++) {
			addressTo[t] = new InternetAddress(to[t]);
		}
		message.addRecipients(Message.RecipientType.TO, addressTo);

		InternetAddress[] addressCC = null;
		if (cc != null && cc.length != 0) {
			addressCC = new InternetAddress[cc.length];
			for (int c = 0; c < cc.length; c++) {
				addressCC[c] = new InternetAddress(cc[c]);
			}
		}
		message.setRecipients(Message.RecipientType.CC, addressCC);

		InternetAddress[] addressBcc = null;
		if (bcc != null && bcc.length != 0) {
			addressBcc = new InternetAddress[bcc.length];
			for (int bc = 0; bc < bcc.length; bc++) {
				addressBcc[bc] = new InternetAddress(bcc[bc]);
			}
		}
		message.setRecipients(Message.RecipientType.BCC, addressBcc);

		message.setSubject(subject);

		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		message.setSentDate(date);

		if (attachments != null) {

			MimeMultipart mp = new MimeMultipart();

			MimeBodyPart text = new MimeBodyPart();
			text.setDisposition(Part.INLINE);

			if (typeMessage.equals("text/html")) {
				text.setDataHandler(new DataHandler(new HTMLDataSource(messageBody)));
			} else {
				text.setContent(MimeUtility.encodeText(messageBody), typeMessage);
			}
			mp.addBodyPart(text);

			addAtachments(attachments, mp);

			message.setContent(mp);

		} else {

			if (typeMessage.equals("text/html")) {
				message.setDataHandler(new DataHandler(new HTMLDataSource(messageBody)));
			} else {
				message.setText(messageBody);
			}
		}

		message.setHeader("X-Mailer", mailer);
		if (!priority.equals("3")) {
			message.setHeader("X-Priority", priority);
		}
		message.saveChanges(); // implicit with send()

		Transport transport = session.getTransport("smtp");
		transport.addConnectionListener(this);
		transport.addTransportListener(this);
		if (ambiente.equals("SVILUPPO")) {
			transport.connect(mailServer, user, password);
		} else if (ambiente.equals("PRODUZIONE")) {
			transport.connect(user, password);
		}
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();

		log.info("****************************************");
		log.info("FINE ESECUZIONE sendMail              ");
		log.info("****************************************");

	}

	protected void addAtachments(String[] attachments, Multipart multipart)
			throws MessagingException, AddressException {
		for (int i = 0; i <= attachments.length - 1; i++) {
			MimeBodyPart file_part = new MimeBodyPart();
			File file = new File(attachments[i]);
			FileDataSource fds = new FileDataSource(file);
			DataHandler dh = new DataHandler(fds);
			file_part.setFileName(file.getName());
			file_part.setDisposition(Part.ATTACHMENT);
			file_part.setDescription("Attached file: " + file.getName());
			file_part.setDataHandler(dh);
			multipart.addBodyPart(file_part);
		}
	}

	/*
	 * Inner class to act as a JAF datasource to send HTML e-mail content
	 */
	class HTMLDataSource implements DataSource{
		private String html;

		public HTMLDataSource(String htmlString) {
			html = htmlString;
		}

		// Return html string in an InputStream.
		// A new stream must be returned each time.
		public InputStream getInputStream() throws IOException {
			if (html == null)
				throw new IOException("Null HTML");
			return new ByteArrayInputStream(html.getBytes());
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("This DataHandler cannot write HTML");
		}

		public String getContentType() {
			return "text/html";
		}

		public String getName() {
			return "JAF text/html dataSource to send e-mail only";
		}
	}

	public void sendMail2(String from, String[] to, String[] cc, String[] bcc,
			String subject, String messageBody, String typeMessage,
			String priority, Map<String, InputStream> images) throws IOException,
			MessagingException, AddressException, NamingException {

		log.info("****************************************");
		log.info("INIZIO ESECUZIONE sendMail              ");
		log.info("****************************************");

		Session session = buildSession();

		SMTPMessage message = new SMTPMessage(session);

		message.setFrom(new InternetAddress(from));

		InternetAddress[] addressTo = new InternetAddress[to.length];
		for (int t = 0; t < to.length; t++) {
			addressTo[t] = new InternetAddress(to[t]);
		}
		message.addRecipients(Message.RecipientType.TO, addressTo);

		InternetAddress[] addressCC = null;
		if (cc != null && cc.length != 0) {
			addressCC = new InternetAddress[cc.length];
			for (int c = 0; c < cc.length; c++) {
				addressCC[c] = new InternetAddress(cc[c]);
			}
		}
		message.setRecipients(Message.RecipientType.CC, addressCC);

		InternetAddress[] addressBcc = null;
		if (bcc != null && bcc.length != 0) {
			addressBcc = new InternetAddress[bcc.length];
			for (int bc = 0; bc < bcc.length; bc++) {
				addressBcc[bc] = new InternetAddress(bcc[bc]);
			}
		}
		message.setRecipients(Message.RecipientType.BCC, addressBcc);

		message.setSubject(subject);

		Calendar cal = Calendar.getInstance();
		message.setSentDate(cal.getTime());

		MimeMultipart content = new MimeMultipart("related");
		// HTML part
		MimeBodyPart textPart = new MimeBodyPart();
		log.debug(messageBody);
		textPart.setText(messageBody, "US-ASCII", "html");
		content.addBodyPart(textPart);

		Iterator<?> it = images.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry) it.next();
			// log.debug(pairs.getKey() + " = " + pairs.getValue());
			MimeBodyPart imagePart = new MimeBodyPart();
			
			InputStream inputStream = ( InputStream) pairs.getValue();
	        File somethingFile = File.createTempFile("test", ".png");
	        try {
	            FileUtils.copyInputStreamToFile(inputStream, somethingFile);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
	        } finally {
	            IOUtils.closeQuietly(inputStream);
	        }
	        imagePart.attachFile(somethingFile);

		    imagePart.setContentID("<" + (String) pairs.getKey() + ">");
			imagePart.setDisposition(MimeBodyPart.INLINE);
			content.addBodyPart(imagePart);
		}
		message.setContent(content);

		Transport.send(message);

		log.info("****************************************");
		log.info("FINE ESECUZIONE sendMail              ");
		log.info("****************************************");
	}

	public Session buildSession() {

//		Properties p = new Properties();
//		p.put("mail.transport.protocol", mailProps.getProperty("mail.transport.protocol"));
//		p.put("mail.host", mailProps.getProperty("mail.smtp.host"));
//		p.put("mail.password", mailProps.getProperty("mail.password"));
//		p.put("mail.smtp.auth", mailProps.getProperty("mail.smtp.auth"));
//		p.put("mail.smtp.host", mailProps.getProperty("mail.smtp.host"));
//		p.put("mail.smtp.port", mailProps.getProperty("mail.smtp.port"));
//		p.put("mail.user", mailProps.getProperty("mail.user"));

		mailProps.put("mail.host", mailProps.getProperty("mail.smtp.host"));
		
		final PasswordAuthentication usernamePassword = new PasswordAuthentication(mailProps.getProperty("mail.user"),mailProps.getProperty("mail.password"));
		Authenticator auth = new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				return usernamePassword;
			}
		};

		Session session = Session.getInstance(mailProps, auth);
		session.setDebug(Boolean.parseBoolean(mailProps.getProperty("mail.debug")));

		return session;
	}

	// implement ConnectionListener interface
	public void opened(ConnectionEvent e) {
		// log.debug(">>> ConnectionListener.opened()");
	}

	public void disconnected(ConnectionEvent e) {
		// log.debug(">>> ConnectionListener.disconnected()");
	}

	public void closed(ConnectionEvent e) {
		// log.debug(">>> ConnectionListener.closed()");
	}

	// implement TransportListener interface
	public void messageDelivered(TransportEvent e) {
		log.info(">>> TransportListener.messageDelivered().");
		log.info(" Valid Addresses:");
		Address[] valid = e.getValidSentAddresses();
		if (valid != null) {
			for (int i = 0; i < valid.length; i++)
				log.info("    " + valid[i]);
		}
	}

	public void messageNotDelivered(TransportEvent e) {
		log.info(">>> TransportListener.messageNotDelivered().");
		log.info(" Invalid Addresses:");
		Address[] invalid = e.getInvalidAddresses();
		if (invalid != null) {
			for (int i = 0; i < invalid.length; i++)
				log.info("    " + invalid[i]);
		}
	}

	public void messagePartiallyDelivered(TransportEvent e) {
		// SMTPTransport doesn't partially deliver msgs
		log.info(">>> SMTPTransport doesn't partially deliver msgs");
		log.info(" partially Addresses:");
		Address[] invalid = e.getInvalidAddresses();
		if (invalid != null) {
			for (int i = 0; i < invalid.length; i++)
				log.info("    " + invalid[i]);
		}
	}

	// public static void main(String[] args) throws Exception {
	//
	// try {
	//
	// // EVERIS
	// System.setProperty("http.proxyHost", "10.0.8.102");
	// System.setProperty("http.proxyPort", "8080");
	//
	// Properties p = new Properties();
	// p.setProperty("mail.debug", "true");
	// p.setProperty("mail.password", "k!OHGW@%");
	// p.setProperty("mail.smtp.auth", "true");
	// p.setProperty("mail.smtp.host", "fclt.hostingtt.de");
	// p.setProperty("mail.smtp.port", "25");
	// p.setProperty("mail.transport.protocol", "smtp");
	// p.setProperty("mail.user", "notifiche@fclt.hostingtt.de");
	//
	// p.setProperty("to", "davide.cremona@gmail.com");
	// p.setProperty("from", "notifiche@fclt.hostingtt.de");
	// p.setProperty("ambiente", "PRODUZIONE");
	//
	// MailClient client = new MailClient(p);
	//
	// String fromS = (String) p.getProperty("from");
	// String toS = (String) p.getProperty("to");
	//
	// String[] to = null;
	// if (toS != null && !toS.equals("")) {
	// to = Utils.tornaArrayString(toS, ";");
	// }
	//
	// String subject = "test Ogg";
	// String cid = "c:\\temp\\BALOTELLI.png";
	// String message = "<html><head>" + "<title>This is not usually
	// displayed</title>" + "</head>\n" + "<body><div><b>Hi there!</b></div>" +
	// "<div>Sending HTML in email is so <i>cool!</i> </div>\n" + "<div>And
	// here's an image: <img src=\"cid:" + cid + "\" /></div>\n" + "<div>I hope
	// you like it!</div></body></html>";
	//
	// String[] cc = null;
	// String[] bcc = null;
	// // String[] att = new String[] { "c:\\temp\\BALOTELLI.png" };
	// Map<String, String> map = new HashMap<String, String>();
	// map.put(cid, cid);
	//
	// client.sendMail2(fromS, to, cc, bcc, subject, message, "text/plain", "1",
	// map);
	//
	// } catch (MessagingException mex) {
	// log.error("Eccezione nella gesetione del messaggio", mex);
	// } catch (IOException e) {
	// log.error("Errore di IO", e);
	// } catch (Exception e) {
	// log.error("Errore in fase di invio mail", e);
	// }
	// }

}