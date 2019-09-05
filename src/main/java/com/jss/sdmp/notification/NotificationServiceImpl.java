package com.jss.sdmp.notification;

import com.jss.sdmp.notification.firebase.FcmNotificationUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Async
    @Override
    public void send(Notification notification) {
        FcmNotificationUtil.send();
    }
}
