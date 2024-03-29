package com.jss.sdmp.users;

import com.jss.sdmp.users.dto.UserBean;
import com.jss.sdmp.users.dto.UserDto;
import com.jss.sdmp.users.exception.UserExistsException;
import com.jss.sdmp.users.exception.UserNotFoundException;
import com.jss.sdmp.users.model.Role;
import com.jss.sdmp.users.model.User;
import com.jss.sdmp.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserBean registerNewUserAccount(UserDto userDto) throws UserExistsException {

        if (usernameExists(userDto.getUsername())) {
            throw new UserExistsException("There is an account with the given details: " + userDto.getUsername());
        }

        User user = createUser(userDto);
        userRepository.save(user);
        return Mapper.getUserBean(user);
    }

    private boolean usernameExists(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    private User createUser(UserDto userDto){
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        return user;
    }


    @Override
    public void createAdmin() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User admin = userRepository.findByUsername("admin");
        if(admin == null){
            User user = new User();
            user.setUsername(adminUsername);
            user.setFirstName("admin");
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setRoles(new HashSet<>(Arrays.asList(adminRole)));
            userRepository.save(user);
        }
    }

    @Override
    public void addRole(String username, String roleStr) {
        Role role = roleRepository.findByName(roleStr);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        boolean hasRole = false;
        for (Role r : user.getRoles()) {
            if (r.getName().equals(role.getName())) {
                hasRole = true;
                break;
            }
        }
        if (!hasRole) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    @Override
    public void removeRole(String username, String roleStr) {
        Role role = roleRepository.findByName(roleStr);

        if (role == null) {
            throw new IllegalArgumentException();
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        boolean removed = user.getRoles().removeIf(r -> r.getName().equals(roleStr));
        if (removed) {
            userRepository.save(user);
        }
    }

    @Override
    public List<UserBean> getUsersByQuery(String query) {
        return userRepository.findAllBySearch(query, PageRequest.of(0, 10))
            .stream()
            .map(Mapper::getUserBean)
            .collect(Collectors.toList());
    }

    @Override
    public UserBean getUser(String username) {
        return Mapper.getUserBean(userRepository.findByUsername(username));
    }
}