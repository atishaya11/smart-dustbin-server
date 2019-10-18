package com.jss.sdmp.management.ward;

import com.jss.sdmp.management.ward.model.Ward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface WardRepository extends MongoRepository<Ward, String> {


    @Query("{$or : [{ name : {$regex : ?0, $options : 'i'}}, " +
        "{ description : {$regex : ?0, $options : 'i'}}]}")
    Page<Ward> findAllBySearch(String query, Pageable pageable);
}
