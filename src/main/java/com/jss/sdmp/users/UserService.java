package com.jss.sdmp.users;

import com.jss.sdmp.users.dto.UserBean;
import com.jss.sdmp.users.dto.UserDto;
import com.jss.sdmp.users.exception.UserExistsException;

public interface UserService {

    UserBean registerNewUserAccount(UserDto userDto) throws UserExistsException;

    void createAdmin();

}
