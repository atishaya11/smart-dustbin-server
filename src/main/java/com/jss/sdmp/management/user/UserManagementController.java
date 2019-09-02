package com.jss.sdmp.management.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserManagementController {

}
