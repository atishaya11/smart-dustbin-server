package com.jss.sdmp.notification.firebase;

import com.google.firebase.messaging.*;

public class FcmNotificationUtil {

    public static void send() {
        String token = "cowZ0zqMGbg:APA91bH8Jt-UPYL-JmwJeAF1F9F_FmA4PBXACw-31usyNKzl5PIeRYTM91w9lpSUrhUZk1qfK-dh2Ds_G1PpVo7Iu7clEsV525lU4juMOueRgZzNyasl6yp6y5X3jLc-NmACElHXmOq7";
        //String token = "dMEPvlk8GWs:APA91bFLRFRfMFfMlmaNCpzXLV1799pHqbO04CX4opoQ7eW6bS6158S7fEJVci4Ya3XjigSP_mOeSSApos1g5L8HVaisDLpx9-Eq3hdlM-FroFCWGlCSeNmxYxqF35Hn8bq8ntlAIZgM";

        Message message = Message.builder()
                .setNotification(new Notification("Test", "Test notify from server"))
                .setToken(token)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println(response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
