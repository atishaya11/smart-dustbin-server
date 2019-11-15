package com.jss.sdmp.management.bin.register;

import com.jss.sdmp.exception.BinNotFoundException;
import com.jss.sdmp.exception.ForbiddenException;
import com.jss.sdmp.exception.WardNotFoundException;
import com.jss.sdmp.management.bin.BinRepository;
import com.jss.sdmp.management.bin.model.Bin;
import com.jss.sdmp.management.ward.WardRepository;
import com.jss.sdmp.management.ward.model.Ward;
import com.jss.sdmp.notification.Notification;
import com.jss.sdmp.notification.NotificationService;
import com.jss.sdmp.users.UserRepository;
import com.jss.sdmp.users.exception.UserNotFoundException;
import com.jss.sdmp.users.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BinRegistrationServiceImpl implements BinRegistrationService {

    private final NotificationService notificationService;

    private final Logger logger = LoggerFactory.getLogger(BinRegistrationServiceImpl.class);

    private final BinRepository binRepository;

    private final UserRepository userRepository;

    private final WardRepository wardRepository;

    @Autowired
    public BinRegistrationServiceImpl(NotificationService notificationService,
                                      BinRepository binRepository, UserRepository userRepository,
                                      WardRepository wardRepository) {
        this.notificationService = notificationService;
        this.binRepository = binRepository;
        this.userRepository = userRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public void register(String username, BinRegisterDto binRegisterDto) {

        if (binRegisterDto.getWardId() == null) {
            throw new IllegalArgumentException();
        }

        Optional<Ward> optionalWard = wardRepository.findById(binRegisterDto.getWardId());

        Ward ward = optionalWard.orElseThrow(WardNotFoundException::new);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("Supervisor not found.");
        }

        if (user.getRoles().stream().noneMatch(role -> role.getName().equals("ROLE_SUPERVISOR"))) {
            throw new ForbiddenException("Registration not allowed to: " + user.getUsername());
        }

        Optional<Bin> bin = binRepository.findByBin(binRegisterDto.getBin());

        if (bin.isEmpty()) {
            String binIdNo = binRegisterDto.getBin();

            GeoJsonPoint geoJsonPoint =
                new GeoJsonPoint(binRegisterDto.getLat(), binRegisterDto.getLng());

            Bin newBin = new Bin(binIdNo, Instant.now(), geoJsonPoint, user, ward);
            newBin.setLandmark(binRegisterDto.getLandmark());
            newBin = binRepository.save(newBin);

            logger.info("Bin registered with BIN: " + newBin.getBin());
        }
    }

    @Override
    public void activate(String binId) {
        Optional<Bin> optionalBin = binRepository.findByBin(binId);

        if (optionalBin.isPresent()) {
            Bin bin = optionalBin.get();
            if (!bin.isActive()) {
                bin.setActivatedAt(Instant.now());
                bin.setActive(true);
                binRepository.save(bin);

                Notification notification = new Notification();
                notification.setTitle("Dustbin Registered");
                notification.setBody("Identification Number: " + bin.getBin());
                notification.setUser(bin.getInstalledBy());

                Map<String, String> data = new HashMap<>();
                data.put("bin", bin.getBin());
                notification.setData(data);

                notificationService.send(notification);

                logger.info("Bin activated with BIN: " + bin.getBin());
            }
        }
    }

    @Override
    public boolean checkStatus(String binId) {
        Optional<Bin> optionalBin = binRepository.findByBin(binId);

        if (optionalBin.isPresent()) {
            Bin bin = optionalBin.get();
            return bin.isActive();
        }

        throw new BinNotFoundException();
    }
}
