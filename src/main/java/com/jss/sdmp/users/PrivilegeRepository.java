package com.jss.sdmp.users;


import com.jss.sdmp.users.model.Privilege;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivilegeRepository extends MongoRepository<Privilege, Integer> {

    Privilege findByName(String name);

}
