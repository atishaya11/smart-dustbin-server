package com.jss.sdmp.util;

import com.jss.sdmp.users.dto.UserBean;
import com.jss.sdmp.users.model.User;

public class Mapper {


    public static UserBean getUserBean(User user) {
        if(user == null)
            return null;

        UserBean userBean = new UserBean();
        userBean.setId(user.getId());
        userBean.setUsername(user.getUsername());
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());

        return userBean;
    }
}
