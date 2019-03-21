package com.kasia.controller;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.service.MyStringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private MyStringFormatter formatter;

    @ModelAttribute("user")
    public User getUser() {
        return sessionC.getUser();
    }

    @ModelAttribute("budget")
    public Budget getBudget() {
        return sessionC.getBudget();
    }

    @ModelAttribute("myFormatter")
    public MyStringFormatter getMyFormatter() {
        return formatter;
    }
}
