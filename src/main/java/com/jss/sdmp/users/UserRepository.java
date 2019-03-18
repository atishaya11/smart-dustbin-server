package com.jss.sdmp.users;


import com.jss.sdmp.users.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    User findByUsername(String user);
}