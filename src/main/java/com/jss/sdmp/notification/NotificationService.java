package com.jss.sdmp.notification;

import com.jss.sdmp.notification.dto.FcmClientInfoDto;

import java.util.List;

public interface NotificationService {

    void send(Notification notification);

    void sendAll(List<Notification> notifications);

    void updateFcmClientToken(String username, FcmClientInfoDto fcmClientInfoDto);

    void deleteFcmClientToken(String username);

}
