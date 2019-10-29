package com.jss.sdmp.management.bin;

import com.jss.sdmp.management.bin.model.Bin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BinRepository extends MongoRepository<Bin, String>, CustomBinRepository {

    Optional<Bin> findByBin(String bin);

}


