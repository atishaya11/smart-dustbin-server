package com.jss.sdmp.management.ward;

import com.jss.sdmp.management.ward.model.Ward;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WardRepository extends MongoRepository<Ward, String> {
}
