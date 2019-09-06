package com.jss.sdmp.notification;

import com.jss.sdmp.notification.dto.FcmClientInfoDto;
import com.jss.sdmp.users.dto.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user/settings")
public class NotificationSettingsController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationSettingsController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/token")
    public void updateFcmClientRegistrationToken (Principal principal, @RequestParam String token) {
        String username = principal.getName();
        FcmClientInfoDto fcmClientInfoDto = new FcmClientInfoDto();
        fcmClientInfoDto.setToken(token);
        notificationService.updateFcmClientToken(username, fcmClientInfoDto);
    }

    @DeleteMapping("/token")
    public void deleteFcmClientRegistrationToken (Principal principal, @RequestParam String token) {
        String username = principal.getName();
        notificationService.deleteFcmClientToken(username);
    }

    @GetMapping("/test_trigger")
    public void testTrigger(Principal principal) {
        String username = principal.getName();
        Notification notification = new Notification();
        notification.setTitle("Test Title");
        notification.setBody("Test Notify");
        notification.setUser(new UserBean(username));
        notificationService.send(notification);
    }
}
