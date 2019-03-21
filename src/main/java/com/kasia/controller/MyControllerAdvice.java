package com.kasia.controller;

import com.kasia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {
    @Autowired
    private MySessionController sessionC;

    @ModelAttribute("user")
    public User getUser() {
        return sessionC.getUser();
    }
}
