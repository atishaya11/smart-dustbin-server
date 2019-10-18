package com.jss.sdmp.management.ward;

import com.jss.sdmp.management.ward.dto.WardBean;
import com.jss.sdmp.management.ward.dto.WardDto;
import com.jss.sdmp.management.ward.model.Ward;
import com.jss.sdmp.users.UserRepository;
import com.jss.sdmp.users.UserService;
import com.jss.sdmp.users.model.User;
import com.jss.sdmp.util.Constants;
import com.jss.sdmp.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WardServiceImpl implements WardService{

    private final WardRepository wardRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public WardServiceImpl(WardRepository wardRepository, UserService userService, UserRepository userRepository) {
        this.wardRepository = wardRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public WardDto create(WardBean wardBean) {
        if (wardBean == null || wardBean.getName() == null
                || wardBean.getName().isBlank()) {
            throw new IllegalArgumentException();
        }

        Ward ward = new Ward(wardBean.getName());
        ward.setDescription(wardBean.getDescription());
        ward.setSupervisors(Collections.emptyList());
        ward = wardRepository.save(ward);
        return Mapper.getWardDto(ward);
    }

    @Override
    public Optional<WardDto> get(String id) {
        return wardRepository.findById(id)
                .map(Mapper::getWardDto);
    }

    @Override
    public Page<WardDto> getAll(String query, Pageable pageable) {
        if (query == null) {
            return wardRepository.findAllBySearch(query, pageable)
                .map(Mapper::getWardDto);
        }

        return wardRepository.findAll(pageable)
                .map(Mapper::getWardDto);
    }

    @Override
    public void delete(String id) {
        wardRepository.deleteById(id);
    }

    /* TODO Currently it is assumed that one ward has one supervisor only,
    *  Correct functionality need to be implemented for add and remove supervisor methods
    *  Code quality can be improved for below methods
    *  */

    @Override
    public WardDto addSupervisor(String wardId, String supervisorUsername) {
        Optional<Ward> wardOptional = wardRepository.findById(wardId);
        
        if (wardOptional.isEmpty()) {
            throw new IllegalArgumentException("Ward does not exist.");
        }
        Ward ward = wardOptional.get();

        for (User user : ward.getSupervisors()) {
            if (user.getUsername().equals(supervisorUsername)) {
                return Mapper.getWardDto(ward);
            }
        }

        User supervisor = userRepository.findByUsername(supervisorUsername);
        if (supervisor == null) {
            throw new IllegalArgumentException("Supervisor account does not exist.");
        }

        if (ward.getSupervisors() != null) {
            ward.getSupervisors().add(supervisor);
        } else {
            ward.setSupervisors(new ArrayList<>());
            ward.getSupervisors().add(supervisor);
        }
        userService.addRole(supervisor.getUsername(), Constants.ROLE_SUPERVISOR);
        wardRepository.save(ward);
        return Mapper.getWardDto(ward);
    }

    @Override
    public void removeSupervisor(String wardId, String supervisorUsername) {
        Optional<Ward> wardOptional = wardRepository.findById(wardId);
        if (wardOptional.isEmpty()) {
            throw new IllegalArgumentException("Ward does not exist.");
        }
        Ward ward = wardOptional.get();
        List<User> supervisors = ward.getSupervisors();
        boolean removed = supervisors.removeIf(
                supervisor -> supervisor.getUsername().equals(supervisorUsername));
        if (removed) {
            userService.removeRole(supervisorUsername, Constants.ROLE_SUPERVISOR);
            wardRepository.save(ward);
        }
    }
}
