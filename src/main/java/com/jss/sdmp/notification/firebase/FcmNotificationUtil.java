package com.jss.sdmp.notification.firebase;

import com.google.firebase.messaging.*;

import java.util.Map;

public class FcmNotificationUtil {

    public static void send(String token, String title, String body) {
        Message message = Message.builder()
                .setNotification(new Notification(title, body))
                .setToken(token)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println(response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public static void send(String token, String title, String body, Map<String, String> data) {
        Message message = Message.builder()
            .setNotification(new Notification(title, body))
            .setToken(token)
            .putAllData(data)
            .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println(response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
