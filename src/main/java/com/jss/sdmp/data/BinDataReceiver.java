package com.jss.sdmp.data;

import com.jss.sdmp.data.dto.BinDataDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BinDataReceiver {

    private BinDataHandler binDataHandler;

    @GetMapping("/data")
    public void receive(BinDataDto binData) {

    }

}
