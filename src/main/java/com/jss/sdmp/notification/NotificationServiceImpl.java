package com.jss.sdmp.notification;

import com.jss.sdmp.notification.firebase.FcmNotificationUtil;
import com.jss.sdmp.notification.dto.FcmClientInfoDto;
import com.jss.sdmp.notification.model.FcmClientInfo;
import com.jss.sdmp.users.UserRepository;
import com.jss.sdmp.users.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final FcmClientInfoRepository fcmClientInfoRepository;

    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(FcmClientInfoRepository fcmClientInfoRepository, UserRepository userRepository) {
        this.fcmClientInfoRepository = fcmClientInfoRepository;
        this.userRepository = userRepository;
    }

    @Async
    @Override
    public void send(Notification notification) {

        if (notification == null || notification.getUser() == null) {
            throw new IllegalArgumentException();
        }
        String username = notification.getUser().getUsername();
        Optional<FcmClientInfo> fcmClientInfoOptional = fcmClientInfoRepository.findByUserUsername(username);
        if (fcmClientInfoOptional.isPresent()) {
            FcmClientInfo fcmClientInfo = fcmClientInfoOptional.get();
            if (fcmClientInfo.getToken() != null) {
                String title = notification.getTitle();
                String body = notification.getBody();
                String token = fcmClientInfo.getToken();
                FcmNotificationUtil.send(token, title, body);
            }
        }

    }

    @Async
    @Override
    public void sendAll(List<Notification> notifications) {
    }

    @Override
    public void updateFcmClientToken(String username, FcmClientInfoDto fcmClientInfoDto) {

        if (fcmClientInfoDto == null || username == null) {
            throw new IllegalArgumentException();
        }

        Optional<FcmClientInfo> fcmClientInfoOptional = fcmClientInfoRepository.findByUserUsername(username);
        if (fcmClientInfoOptional.isPresent()) {
            FcmClientInfo fcmClientInfo = fcmClientInfoOptional.get();
            fcmClientInfo.setToken(fcmClientInfoDto.getToken());
            fcmClientInfo.setUpdatedAt(Instant.now());
            fcmClientInfoRepository.save(fcmClientInfo);
        } else {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Invalid username. Cannot update fcm token.");
            }
            FcmClientInfo fcmClientInfo = getFcmClientTokenFromDto(fcmClientInfoDto);
            fcmClientInfo.setUser(user);
            fcmClientInfoRepository.save(fcmClientInfo);
        }

    }

    @Override
    public void deleteFcmClientToken(String username) {

        if (username == null) {
            throw new IllegalArgumentException();
        }
        FcmClientInfoDto fcmClientInfoDto = new FcmClientInfoDto();
        fcmClientInfoDto.setToken(null);
        updateFcmClientToken(username, fcmClientInfoDto);
    }

    private FcmClientInfo getFcmClientTokenFromDto(FcmClientInfoDto fcmClientInfoDto) {
        FcmClientInfo fcmClientInfo = new FcmClientInfo();
        fcmClientInfo.setToken(fcmClientInfoDto.getToken());
        Instant now = Instant.now();
        fcmClientInfo.setCreatedAt(now);
        fcmClientInfo.setUpdatedAt(now);
        return fcmClientInfo;
    }
}
