package basket.watch.backend.notification;

import lombok.RequiredArgsConstructor;

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

@RequiredArgsConstructor
@Singleton
public class EmailService {

    private final MailProperties mailProperties;

    private Session session = null;

    @PostConstruct
    public void init() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", mailProperties.getSmtpHost());
        this.session = Session.getInstance(properties, null);
    }

    public void sendNotification(String message, String subject, String to) {
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(mailProperties.getFrom(), mailProperties.getFromPersonal()));
            mimeMessage.setSubject(subject, "UTF-8");
            mimeMessage.setContent(message, "text/html; charset=UTF-8");
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            Transport.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
