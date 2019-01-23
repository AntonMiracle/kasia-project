package com.kasia.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@RequestMapping("/login")
public class LoginController {

    @PostMapping(params = "registration=registration")
    public String registration() {
        System.out.println("=========== LoginController#registration");
        return "registration";
    }

    @GetMapping
    public String enter() {
        System.out.println("=========== LoginController#enter");
        return "login";
    }
}
