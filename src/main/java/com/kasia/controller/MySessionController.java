package com.kasia.controller;

import com.kasia.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class MySessionController {
    @Resource(name = "sessionUser")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user != null && user.getId() > 0) this.user = user;
    }

    public boolean isUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !auth.getName().equals("anonymousUser");
    }
}
