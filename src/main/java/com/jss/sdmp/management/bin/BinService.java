package com.jss.sdmp.management.bin;

import com.jss.sdmp.data.model.BinData;
import com.jss.sdmp.management.bin.dto.BinDto;
import com.jss.sdmp.management.bin.dto.BinSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BinService {

    BinDto get(String bin);

    Page<BinDto> getAll(BinSearchRequest binSearchRequest, Pageable pageable);

    Page<BinData> getAllLogs(String bin, Pageable pageable);
}
