package com.kasia.controller;

import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("home")
public class HomeController {
    @Autowired
    private UserService uService;
    private final String PROFILE_ATTRIBUTE_NAME = "profileAtt";
    private final String USER_ATTRIBUTE_NAME = "user";

    @GetMapping("profile")
    public String openProfile(Model model, Principal principal) {
        User user = uService.findUserByEmail(principal.getName());
        System.out.println("============== # openProfile user:");
        System.out.println(user);
        model.addAttribute(PROFILE_ATTRIBUTE_NAME, PROFILE_ATTRIBUTE_NAME);
        model.addAttribute(USER_ATTRIBUTE_NAME, user);
        return "home";
    }

    @GetMapping()
    public String budgetBack() {
        return "home";
    }
}
