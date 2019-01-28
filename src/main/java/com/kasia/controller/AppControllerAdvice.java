package com.kasia.controller;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class AppControllerAdvice {
    @Autowired
    private UserService uService;

    @ModelAttribute("user")/*load auth user in attribute with name 'user'*/
    public User getAuthenticationUser(Principal principal) {
        return principal == null ? null : uService.findUserByEmail(principal.getName());
    }

    @ModelAttribute("allOwnBudgets")
    public Set<Budget> getAllOwnBudget(Principal principal) {
        User user = getAuthenticationUser(principal);
        if (user != null) {
            return uService.findOwnBudgets(user.getId());
        }
        return new HashSet<>();
    }


}
