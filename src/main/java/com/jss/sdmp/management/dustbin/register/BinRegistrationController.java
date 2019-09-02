package com.jss.sdmp.management.dustbin.register;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/dustbin")
public class BinRegistrationController {

    @GetMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
