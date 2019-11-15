package com.jss.sdmp.util;

import com.jss.sdmp.management.bin.dto.BinDto;
import com.jss.sdmp.management.bin.dto.BinStatus;
import com.jss.sdmp.management.bin.model.Bin;
import com.jss.sdmp.management.ward.dto.WardDto;
import com.jss.sdmp.management.ward.model.Ward;
import com.jss.sdmp.users.dto.UserBean;
import com.jss.sdmp.users.model.Role;
import com.jss.sdmp.users.model.User;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
        return getWardDto(ward, true);
    }

    public static WardDto getWardDto(Ward ward, boolean withSupervisors) {
        if(ward == null)
            return null;

        WardDto wardDto = new WardDto();
        wardDto.setId(ward.getId());
        wardDto.setName(ward.getName());
        wardDto.setDescription(ward.getDescription());

        if (withSupervisors)
            wardDto.setSupervisors(getSupervisorDtoList(ward.getSupervisors()));

        return wardDto;
    }

    private static List<UserBean> getSupervisorDtoList(List<User> supervisors) {
        return supervisors == null ? Collections.emptyList() :
                (supervisors.stream()
                        .map(Mapper::getUserBean)
                        .collect(Collectors.toList()));

    }


    public static BinDto getBinDto(Bin bin) {
        if(bin == null)
            return null;

        BinDto binDto = new BinDto();
        binDto.setId(bin.getId());
        binDto.setBin(bin.getBin());
        binDto.setRegisteredAt(bin.getRegisteredAt());
        binDto.setActivatedAt(bin.getActivatedAt());
        binDto.setLocation(bin.getLocation());
        binDto.setActive(bin.isActive());
        binDto.setInstalledBy(getUserBean(bin.getInstalledBy()));
        binDto.setLandmark(bin.getLandmark() == null ? "Near landmark" : bin.getLandmark());

        BinStatus binStatus = new BinStatus();
        binStatus.setComment("The bin status is currently set to random.");
        binStatus.setLastUpdatedAt(Instant.now().minusMillis(new Random().nextInt(1000 * 60 * 60 * 2)));
        binStatus.setPercentage(new Random().nextInt(90) + 10);

        binDto.setStatus(binStatus);
        return binDto;
    }
}
