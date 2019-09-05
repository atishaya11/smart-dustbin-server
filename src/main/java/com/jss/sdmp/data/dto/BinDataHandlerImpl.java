package com.jss.sdmp.data.dto;

import com.jss.sdmp.data.BinDataHandler;
import com.jss.sdmp.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BinDataHandlerImpl implements BinDataHandler {

    private final NotificationService notificationService;

    @Autowired
    public BinDataHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
