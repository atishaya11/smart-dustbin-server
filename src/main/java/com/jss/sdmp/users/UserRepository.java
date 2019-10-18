package com.jss.sdmp.users;


import com.jss.sdmp.users.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    User findByUsername(String user);
    
    @Query("{$or : [{ username : {$regex : ?0, $options : 'i'}}, " +
        "{ firstName : {$regex : ?0, $options : 'i'}}, " +
        "{ lastName : {$regex : ?0, $options : 'i'}}]}")
    List<User> findAllBySearch(String query, Pageable pageable);
}