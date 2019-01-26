package com.kasia.controller;

import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class MyControllerAdvice {
    @Autowired
    private UserService userService;

    @ModelAttribute("user")/*load auth user in attribute with name 'user'*/
    public User getAuthenticationUser(Principal principal) {
        return principal == null ? null : userService.findUserByEmail(principal.getName());
    }


}
