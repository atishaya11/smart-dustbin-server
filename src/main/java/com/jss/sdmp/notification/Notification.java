package com.jss.sdmp.notification;

import com.jss.sdmp.notification.model.NotificationType;
import com.jss.sdmp.users.dto.UserBean;
import com.jss.sdmp.users.model.User;

import java.util.Map;

public class Notification {

    private User user;

    private String title;

    private String body;

    private NotificationType notificationType;

    private Map<String, String> data;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
