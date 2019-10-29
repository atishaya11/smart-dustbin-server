package com.jss.sdmp.management.ward;

import com.jss.sdmp.management.ward.model.Ward;

import java.util.List;

public interface CustomWardRepository {

    List<Ward> findAllBySupervisor(String username);
}
