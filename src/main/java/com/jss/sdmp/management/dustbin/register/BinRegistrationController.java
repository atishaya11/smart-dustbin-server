package com.jss.sdmp.management.dustbin.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jss.sdmp.notification.Notification;
import com.jss.sdmp.notification.NotificationService;
import com.jss.sdmp.users.dto.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PastOrPresent;
import java.security.Principal;
import java.util.Random;


@RestController
@RequestMapping("/api")
public class BinRegistrationController {

    private final NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(BinRegistrationController.class);

    @Autowired
    public BinRegistrationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/bin/status")
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public ResponseEntity<String> checkStatus(Principal principal, @RequestParam String din) {

        Random random = new Random();
        int rand = random.nextInt(1000) + 1;
        if (rand > 200) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            logger.info(new ObjectMapper().writeValueAsString("Success" + din));
            String username = principal.getName();
            Notification notification = new Notification();
            notification.setTitle("Dustbin Registered");
            notification.setBody("Identification Number: " + din);
            notification.setUser(new UserBean(username));
            notificationService.send(notification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/bin/activate")
    public ResponseEntity<String> register(@RequestParam String din) {
        try {
            logger.info(new ObjectMapper().writeValueAsString("Registered: " + din));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
