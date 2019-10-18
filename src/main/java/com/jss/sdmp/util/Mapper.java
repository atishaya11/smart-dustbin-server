package com.jss.sdmp.util;

import com.jss.sdmp.management.ward.dto.WardDto;
import com.jss.sdmp.management.ward.model.Ward;
import com.jss.sdmp.users.dto.UserBean;
import com.jss.sdmp.users.model.Role;
import com.jss.sdmp.users.model.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {


    public static UserBean getUserBean(User user) {
        if(user == null)
            return null;

        UserBean userBean = new UserBean();
        userBean.setId(user.getId());
        userBean.setUsername(user.getUsername());
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());
        userBean.setAuthorities(getRoles(user));
        return userBean;
    }

    private static String getRoles(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));
    }

    public static WardDto getWardDto(Ward ward) {
        if(ward == null)
            return null;

        WardDto wardDto = new WardDto();
        wardDto.setId(ward.getId());
        wardDto.setName(ward.getName());
        wardDto.setDescription(ward.getDescription());
        wardDto.setSupervisors(getSupervisorDtoList(ward.getSupervisors()));
        return wardDto;
    }

    private static List<UserBean> getSupervisorDtoList(List<User> supervisors) {
        return supervisors == null ? Collections.emptyList() :
                (supervisors.stream()
                        .map(Mapper::getUserBean)
                        .collect(Collectors.toList()));

    }
}
