package com.jss.sdmp.management.ward.dto;

import com.jss.sdmp.users.dto.UserBean;

import java.util.List;

public class WardDto {

    private String id;

    private String name;

    private String description;

    private List<UserBean> supervisors;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserBean> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<UserBean> supervisors) {
        this.supervisors = supervisors;
    }
}
