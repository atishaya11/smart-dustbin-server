package com.jss.sdmp.management.bin;

import com.jss.sdmp.data.model.BinData;
import com.jss.sdmp.management.bin.dto.BinDto;
import com.jss.sdmp.management.bin.dto.BinSearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/bin")
public class BinController {

    private final Logger logger = LoggerFactory.getLogger(BinController.class);

    private final BinService binService;

    public BinController(BinService binService) {
        this.binService = binService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<BinDto>> getAll(Principal principal, BinSearchRequest binSearchRequest) {
        return ResponseEntity.ok(binService.getAll(binSearchRequest, Pageable.unpaged()));
    }

    @GetMapping("/{bin}")
    public ResponseEntity<BinDto> get(Principal principal, @PathVariable String bin) {
        return ResponseEntity.ok(binService.get(bin));
    }

    @GetMapping("/{bin}/logs")
    public ResponseEntity<Page<BinData>> getLogs(Principal principal, @PathVariable String bin) {
        return ResponseEntity.ok(binService.getAllLogs(bin, Pageable.unpaged()));
    }
}
