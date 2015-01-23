import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail
{
    final String USERNAME;
    final String PASSWORD;
    final String SMTP_HOST;
    final String SMTP_PORT;

    /**
     * Default constructor - takes no parameters
     */
    public SendEmail()
    {
    	//Can input email credentials here
    	USERNAME = "email@domain";
    	PASSWORD = "password";
    	SMTP_HOST = "smtp.host.com";
    	SMTP_PORT = "port#";
    }

    /**
     * Constructor - takes parameters to determine email settings
     * @param uname Email address or user name
     * @param pword Email account password
     * @param host SMTP host name
     * @param port SMTP port number
     */
    public SendEmail(String uname, String pword, String host, String port)
    {
    	USERNAME = uname;
    	PASSWORD = pword;
    	SMTP_HOST = host;
    	SMTP_PORT = port;
    }

    public void send(String to, String subject, String body)
    {
    	String from = USERNAME;

    	Properties properties = new Properties();
    	properties.put("mail.smtp.auth", "true");
    	properties.put("mail.pop3.socketFactory.class", "SSL_FACTORY");
    	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	properties.put("mail.smtp.socketFactory.fallback", "false");
    	properties.put("mail.smtp.socketFactory.port", SMTP_PORT);
    	properties.put("mail.smtp.startssl.enable", "true");
    	properties.put("mail.smtp.host", SMTP_HOST);
    	properties.put("mail.smtp.port", SMTP_PORT);

    	Session session = Session.getInstance(properties,
    		new javax.mail.Authenticator()
    	{
    		protected PasswordAuthentication getPasswordAuthentication()
    		{
    			return new PasswordAuthentication(USERNAME, PASSWORD);
    		}
    	});

    	try
    	{
    		MimeMessage message = new MimeMessage(session);
    		message.setFrom(new InternetAddress(from));
    		message.addRecipient(Message.RecipientType.TO,  new InternetAddress(to));
    		message.setSubject(subject);
    		message.setText(body);
    		Transport.send(message);
    		//System.out.println("Sent message successfully....");
    	} catch (MessagingException mex)
    	{
    		mex.printStackTrace();
    	}
    }
}