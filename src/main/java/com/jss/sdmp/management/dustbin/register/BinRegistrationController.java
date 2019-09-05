package com.jss.sdmp.management.dustbin.register;

import com.jss.sdmp.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dustbin")
public class BinRegistrationController {

    private final NotificationService notificationService;

    @Autowired
    public BinRegistrationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/test_trigger")
    public void testTrigger() {
        notificationService.send(null);
    }

}
