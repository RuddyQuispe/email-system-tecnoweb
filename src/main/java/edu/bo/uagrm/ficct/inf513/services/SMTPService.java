package edu.bo.uagrm.ficct.inf513.services;

import edu.bo.uagrm.ficct.inf513.utils.Address;
import edu.bo.uagrm.ficct.inf513.utils.Email;
import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 15/9/21 17:01
 */
public class SMTPService implements Runnable {

    private Email email;
    private Session session;
    private MimeMessage message;

    public SMTPService(Email email) {
        this.email = email;
        // get info to .env file
        Dotenv dotenv = Dotenv.configure()
                // address file .env
                .directory(Address.addressFileENV)
                .load();
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", dotenv.get("SMTP_HOST"));
        properties.setProperty("mail.smtp.port", dotenv.get("SMTP_PORT"));
        properties.setProperty("mail.smtp.tls.enable", "true");   //when uses tecnoweb
//        properties.setProperty("mail.smtp.ssl.enable", "true");     //when uses Gmail
        properties.setProperty("mail.smtp.auth", "true");

        this.session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(dotenv.get("SMTP_USER"), dotenv.get("SMTP_PASSWD"));
            }
        });
        this.message = new MimeMessage(this.session);
        try {
            this.message.setFrom(new InternetAddress(dotenv.get("SMTP_MAIL")));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            InternetAddress[] toAddresses = {new InternetAddress(this.email.getTo())};
            this.message.setRecipients(MimeMessage.RecipientType.TO, toAddresses);
            this.message.setSubject(email.getSubject());
            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart htmlPart = new MimeBodyPart();

            htmlPart.setContent(email.getMessage(), "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);
            message.saveChanges();

            Transport.send(message);
        } catch (NoSuchProviderException | AddressException ex) {
            Logger.getLogger(SMTPService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
