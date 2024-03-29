package com.jss.sdmp.management.ward;

import com.jss.sdmp.management.ward.dto.WardBean;
import com.jss.sdmp.management.ward.dto.WardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/ward")
public class WardController {

    private final WardService wardService;

    @Autowired
    public WardController(WardService wardService) {
        this.wardService = wardService;
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Page<WardDto>> getAll(@RequestParam(required = false) String query) {
        return ResponseEntity.ok()
                .body(wardService.getAll(query,
                        PageRequest.of(0, Integer.MAX_VALUE)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<WardDto> get(@PathVariable String id) {
        return ResponseEntity.of(wardService.get(id));
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<WardDto> create(@RequestBody WardBean ward) {
        return ResponseEntity.ok(wardService.create(ward));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String id) {
        wardService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/supervisor")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<WardDto> addSupervisor(@PathVariable String id,
                                                 @RequestParam String username) {
        return ResponseEntity.ok(wardService.addSupervisor(id, username));
    }

    @DeleteMapping("/{id}/supervisor/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> removeSupervisor(@PathVariable String id,
                                                 @PathVariable String username) {
        wardService.removeSupervisor(id, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/supervisor_wards")
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<List<WardDto>> getAllWardsBySupervisor(Principal principal) {
        return ResponseEntity.ok()
            .body(wardService.getWardsBySupervisor(principal.getName()));
    }

}
