package com.jss.sdmp.notification.firebase;

import com.google.firebase.messaging.*;

public class FcmNotificationUtil {

    public static void send(String token, String title, String body) {

        Message message = Message.builder()
                .setNotification(new Notification(title, body))
                .setToken(token)
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
