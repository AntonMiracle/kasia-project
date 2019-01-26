package com.kasia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {
    private final String PROFILE_ATTRIBUTE_NAME = "profileAtt";

    @GetMapping("profile")
    public String budgetAdd(Model model) {
        model.addAttribute(PROFILE_ATTRIBUTE_NAME, PROFILE_ATTRIBUTE_NAME);
        return "home";
    }

    @GetMapping()
    public String budgetBack() {
        return "home";
    }
}
