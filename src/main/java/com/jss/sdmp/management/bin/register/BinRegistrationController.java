package com.jss.sdmp.management.bin.register;

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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/dustbin")
public class BinRegistrationController {

    private final NotificationService notificationService;

    private final Logger logger = LoggerFactory.getLogger(BinRegistrationController.class);

    @Autowired
    public BinRegistrationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/bin/register")
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<String> register(Principal principal, BinRegisterDto binRegisterDto) {
        logger.info("Registration from android: " + binRegisterDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/bin/status")
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<String> checkStatus(Principal principal, @RequestParam String bin) {

        try {
            logger.info(new ObjectMapper().writeValueAsString("Success" + bin));
            String username = principal.getName();
            Notification notification = new Notification();
            notification.setTitle("Dustbin Registered");
            notification.setBody("Identification Number: " + bin);
            notification.setUser(new UserBean(username));
            Map<String, String> data = new HashMap<>();
            data.put("bin", bin);
            notification.setData(data);
            notificationService.send(notification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/bin/{bin}/activate")
    public ResponseEntity<String> activate(@PathVariable String bin) {
        try {
            logger.info(new ObjectMapper().writeValueAsString("Registered: " + bin));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
