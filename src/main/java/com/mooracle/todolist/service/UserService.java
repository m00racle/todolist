package com.mooracle.todolist.service;

import com.mooracle.todolist.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/** ENTRY LIST:
 *  ENTRY 5: Implementing the UserDetailsService
 *  */

public interface UserService extends UserDetailsService { //5-1: UserDetailsService is part of spring security
    User findByUsername(String username);//5-2: make sure User comes from com.mooracle library
}
