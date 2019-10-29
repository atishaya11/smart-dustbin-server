package com.jss.sdmp.data;

import com.jss.sdmp.data.dto.BinDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bin")
public class BinDataReceiver {

    private final BinDataHandler binDataHandler;

    private static final Logger logger = LoggerFactory.getLogger(BinDataReceiver.class);

    @Autowired
    public BinDataReceiver(BinDataHandler binDataHandler) {
        this.binDataHandler = binDataHandler;
    }

    @GetMapping("/data")
    public void receive(BinDataDto binData) {
        binDataHandler.handle(binData);
    }

}
