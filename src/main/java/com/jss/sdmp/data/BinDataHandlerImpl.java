package com.jss.sdmp.data;

import com.jss.sdmp.data.dto.BinDataDto;
import com.jss.sdmp.data.model.BinData;
import com.jss.sdmp.management.bin.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BinDataHandlerImpl implements BinDataHandler {

    private final BinService binService;

    private final BinDataRepository binDataRepository;

    @Autowired
    public BinDataHandlerImpl(BinService binService, BinDataRepository binDataRepository) {
        this.binService = binService;
        this.binDataRepository = binDataRepository;
    }

    @Override
    public void handle(BinDataDto binDataDto) {
        BinData data = new BinData();
        data.setBin(binDataDto.getBinId());
        data.setPercentage(binDataDto.getPercentage());
        data.setInstant(Instant.now());
        binDataRepository.save(data);
    }

}
