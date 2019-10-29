package com.jss.sdmp.data;

import com.jss.sdmp.data.model.BinData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BinDataRepository extends MongoRepository<BinData, String> {

    Page<BinData> findAllByBin(String bin, Pageable pageable);
}
