package edu.bo.uagrm.ficct.inf513.services;

import edu.bo.uagrm.ficct.inf513.utils.Info;
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
    private static Info info = Info.getInstance();

    public SMTPService(Email email) {
        this.email = email;
    }

    @Override
    public void run() {
        try {
            // get info to .env file
            Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.host", info.environmentVariables.get("SMTP_HOST"));
            properties.setProperty("mail.smtp.port", info.environmentVariables.get("SMTP_PORT"));
            properties.setProperty("mail.smtp.tls.enable", "true");   //when uses tecnoweb
            // properties.setProperty("mail.smtp.ssl.enable", "true");     //when uses Gmail
            properties.setProperty("mail.smtp.auth", "false");
            this.session = Session.getDefaultInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            info.environmentVariables.get("SMTP_USER"),
                            info.environmentVariables.get("SMTP_PASSWD"));
                }
            });
            this.message = new MimeMessage(this.session);
            this.message.setFrom(new InternetAddress(info.environmentVariables.get("SMTP_MAIL")));
            InternetAddress[] toAddresses = {new InternetAddress(this.email.getFrom())};
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
            System.out.println("Error thread smtp service start(): " + ex);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
