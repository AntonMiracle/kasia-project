package com.kasia.controller;

import com.kasia.model.Budget;
import com.kasia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {
    @Autowired
    private MySessionController sessionController;

    @ModelAttribute("user")
    public User getUser() {
        return sessionController.getUser();
    }

    @ModelAttribute("budget")
    public Budget getBudget() {
        return sessionController.getBudget();
    }
}
