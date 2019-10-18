package com.jss.sdmp.notification;

import com.jss.sdmp.notification.model.FcmClientInfo;
import com.jss.sdmp.users.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FcmClientInfoRepository extends MongoRepository<FcmClientInfo, String> {

    Optional<FcmClientInfo> findByUser(User user);

    List<FcmClientInfo> findAllByUser(User user);

}
