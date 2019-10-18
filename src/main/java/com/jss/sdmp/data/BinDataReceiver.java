package com.jss.sdmp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jss.sdmp.data.dto.BinDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BinDataReceiver {

    private final BinDataHandler binDataHandler;

    private static final Logger logger = LoggerFactory.getLogger(BinDataReceiver.class);

    @Autowired
    public BinDataReceiver(BinDataHandler binDataHandler) {
        this.binDataHandler = binDataHandler;
    }

    @GetMapping("/bin/data")
    public void receive(BinDataDto binData) {
        try {
            logger.info(new ObjectMapper().writeValueAsString(binData));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
