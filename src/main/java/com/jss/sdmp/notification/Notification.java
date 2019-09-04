package com.jss.sdmp.notification;

import com.jss.sdmp.notification.model.NotificationType;
import com.jss.sdmp.users.model.User;

public class Notification {

    private User user;

    private NotificationType notificationType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
