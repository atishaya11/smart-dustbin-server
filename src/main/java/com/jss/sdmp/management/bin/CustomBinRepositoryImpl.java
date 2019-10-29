package com.jss.sdmp.management.bin;

import com.jss.sdmp.management.bin.model.Bin;
import com.jss.sdmp.users.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomBinRepositoryImpl implements CustomBinRepository {

    private final MongoTemplate mongoTemplate;

    private final UserRepository userRepository;

    public CustomBinRepositoryImpl(MongoTemplate mongoTemplate, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }


    @Override
    public Page<Bin> findAllByWard(String wardId, Pageable pageable) {
        Query query = Query.query(Criteria
                .where("ward.$id")
                .is(new ObjectId(wardId)))
            .with(pageable);

        /*
        * db.bin.aggregate([ {$match : { '_id':  ObjectId("")}},
                {$lookup: {
                  from: "binData",
                  let: { bin: "$bin", id : "$_id"},
                  pipeline : [{$match : { $expr: { $eq: [ "$bin",  "$$bin"]}}},{$sort : {instant : 1}}, {$limit : 1}],
                  as: "bin_data"
                }

              }, {$project : {'bin_data.bin' : 0, 'bin_data._id' : 0}}, {$unwind : "$bin_data"}]).pretty();*/
        List<Bin> list = mongoTemplate.find(query, Bin.class);
        return PageableExecutionUtils.getPage(list, pageable,
            () -> mongoTemplate.count(query, Bin.class));
    }

}
