package com.kasia.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@RequestMapping("/login")
public class LoginController  {

    @PostMapping(params = "registration")
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String login() {
        return "home";
    }

    @GetMapping
    public String enter() {
        return "login";
    }
}
