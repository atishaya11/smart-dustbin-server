package com.jss.sdmp.notification;

import com.jss.sdmp.notification.model.NotificationType;
import com.jss.sdmp.users.dto.UserBean;
import com.jss.sdmp.users.model.User;

public class Notification {

    private UserBean user;

    private String title;

    private String body;

    private NotificationType notificationType;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
