package com.jss.sdmp.notification;

import com.jss.sdmp.notification.model.FcmClientInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FcmClientInfoRepository extends MongoRepository<FcmClientInfo, String> {

    Optional<FcmClientInfo> findByUserUsername(String username);
}
