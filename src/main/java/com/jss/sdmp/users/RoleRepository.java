package com.jss.sdmp.users;


import com.jss.sdmp.users.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, Integer> {

    Role findByName(String name);
}
