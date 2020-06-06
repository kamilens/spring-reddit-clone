package reddit.clone.reddit.service;

import reddit.clone.reddit.domain.NotificationEmail;

public interface MailService {

    void sendMail(NotificationEmail notificationEmail);

}
