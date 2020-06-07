package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reddit.clone.reddit.domain.NotificationEmail;
import reddit.clone.reddit.exception.RedditException;
import reddit.clone.reddit.service.MailContentBuilder;
import reddit.clone.reddit.service.MailService;

@RequiredArgsConstructor
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Value("${application.emails.verification}")
    private String verificationEmail;
    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    // TODO: use RabbitMQ
    @Override
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(verificationEmail);
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Activation email send!!");
        } catch (MailException ex) {
            throw new RedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }

}
