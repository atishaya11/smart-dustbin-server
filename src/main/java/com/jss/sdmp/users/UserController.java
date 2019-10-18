package com.jss.sdmp.users;

import com.jss.sdmp.users.dto.UserBean;
import com.jss.sdmp.users.dto.UserDto;
import com.jss.sdmp.users.exception.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody() @Valid UserDto user, final BindingResult result) {

        UserBean registered = null;
        boolean userExists = false;
        if (!result.hasErrors()) {
            try {
                registered = createUserAccount(user);
            } catch (UserExistsException e) {
                result.rejectValue("username", "user.error.registered", "User already registered.");
                userExists = true;
            }
        }
        Map<String, String> errors = new HashMap<>();
        for(FieldError error : result.getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }

        if (userExists) {
            errors.clear();
            errors.put("error", "Already registered.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
        }
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getGlobalErrors()) {
                errors.put("error", objectError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    private UserBean createUserAccount(UserDto userDto) throws UserExistsException {
        return userService.registerNewUserAccount(userDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserBean>> user(@RequestParam String query){
        List<UserBean> userBeans = userService.getUsersByQuery(query);
        return ResponseEntity.ok().body(userBeans);
    }
}
