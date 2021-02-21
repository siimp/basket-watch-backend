package basket.watch.backend.notification;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Singleton
public class EmailService {

    private MailProperties mailProperties;

    private Session session = null;

    @PostConstruct
    public void init() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", mailProperties.getSmtpHost());
        this.session = Session.getInstance(properties, null);
    }

    public void sendNotification(String message, String to) {
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
            mimeMessage.setFrom(new InternetAddress(mailProperties.getFrom(), "Basket-Watch info (no-reply)"));
            mimeMessage.setSubject("Basket-Watch (no-reply)", "UTF-8");
            mimeMessage.setText(message, "UTF-8");
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            Transport.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
