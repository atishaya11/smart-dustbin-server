package com.jss.sdmp.notification;

import com.jss.sdmp.notification.firebase.FcmNotificationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Async
    @Override
    public void send(Notification notification) {
        FcmNotificationService.send();
    }
}
