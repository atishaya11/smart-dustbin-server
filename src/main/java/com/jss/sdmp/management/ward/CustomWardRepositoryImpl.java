package com.jss.sdmp.management.ward;

import com.jss.sdmp.management.ward.model.Ward;
import com.jss.sdmp.users.UserRepository;
import com.jss.sdmp.users.exception.UserNotFoundException;
import com.jss.sdmp.users.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomWardRepositoryImpl implements CustomWardRepository {

    private final MongoTemplate mongoTemplate;

    private final UserRepository userRepository;

    public CustomWardRepositoryImpl(MongoTemplate mongoTemplate, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }


    @Override
    public List<Ward> findAllBySupervisor(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("Supervisor does not exist.");
        }

        String userId = user.getId();

        Query query = Query.query(Criteria
                .where("supervisors.$id")
                .is(new ObjectId(userId)));
        return mongoTemplate.find(query, Ward.class);
    }

}
