package com.jss.sdmp.management.bin.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/api/bin")
public class BinRegistrationController {

    private final Logger logger = LoggerFactory.getLogger(BinRegistrationController.class);

    private final BinRegistrationService binRegistrationService;

    @Autowired
    public BinRegistrationController(BinRegistrationService binRegistrationService) {
        this.binRegistrationService = binRegistrationService;
    }

    @GetMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<String> register(Principal principal, BinRegisterDto binRegisterDto) {

        if (principal != null) {
            String username = principal.getName();
            binRegistrationService.register(username, binRegisterDto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<String> checkStatus(Principal principal, @RequestParam String bin) {

        if (principal != null) {
            boolean active = binRegistrationService.checkStatus(bin);
            return ResponseEntity.ok(active ? "ACTIVE" : "NOT_ACTIVE");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activate(@RequestParam String bin) {
        binRegistrationService.activate(bin);
        return ResponseEntity.ok().build();
    }

}
