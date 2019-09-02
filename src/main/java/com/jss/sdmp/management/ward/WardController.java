package com.jss.sdmp.management.ward;

import com.jss.sdmp.management.ward.dto.WardDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ward")
public class WardController {

    @PostMapping("/")
    public void create(@RequestBody WardDto wardDto) {

    }


}
