package com.jss.sdmp.management.ward;

import com.jss.sdmp.management.ward.dto.WardBean;
import com.jss.sdmp.management.ward.dto.WardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WardService {

    WardDto create(WardBean ward);

    Optional<WardDto> get(String id);

    Page<WardDto> getAll(String query, Pageable pageable);

    void delete(String id);

    WardDto addSupervisor(String wardId, String supervisor);

    void removeSupervisor(String wardId, String supervisor);

    List<WardDto> getWardsBySupervisor(String name);
}
