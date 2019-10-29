package com.jss.sdmp.management.bin;

import com.jss.sdmp.data.BinDataRepository;
import com.jss.sdmp.data.model.BinData;
import com.jss.sdmp.exception.BinNotFoundException;
import com.jss.sdmp.management.bin.dto.BinDto;
import com.jss.sdmp.management.bin.dto.BinSearchRequest;
import com.jss.sdmp.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BinServiceImpl implements BinService {

    private final Logger logger = LoggerFactory.getLogger(BinServiceImpl.class);

    private final BinRepository binRepository;

    private final BinDataRepository binDataRepository;

    public BinServiceImpl(BinRepository binRepository, BinDataRepository binDataRepository) {
        this.binRepository = binRepository;
        this.binDataRepository = binDataRepository;
    }

    @Override
    public BinDto get(String bin) {
        return binRepository.findByBin(bin)
            .map(Mapper::getBinDto)
            .orElseThrow(BinNotFoundException::new);
    }

    @Override
    public Page<BinDto> getAll(BinSearchRequest binSearchRequest, Pageable pageable) {

        String wardId = binSearchRequest.getWardId();

        if (wardId == null) {
            return binRepository.findAll(pageable)
                .map(Mapper::getBinDto);
        } else {
            return binRepository.findAllByWard(wardId, pageable)
                .map(Mapper::getBinDto);

        }
    }

    @Override
    public Page<BinData> getAllLogs(String bin, Pageable pageable) {
        return binDataRepository.findAllByBin(bin, pageable);
    }
}
