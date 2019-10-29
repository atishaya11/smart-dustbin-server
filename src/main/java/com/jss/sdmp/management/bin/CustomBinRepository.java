package com.jss.sdmp.management.bin;

import com.jss.sdmp.management.bin.model.Bin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomBinRepository {

    Page<Bin> findAllByWard(String wardId, Pageable pageable);
}
