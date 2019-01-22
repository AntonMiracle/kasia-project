package com.kasia.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@EnableAutoConfiguration
public class LoginController {

    @PostMapping(value = "/login", params = "registration")
    public String registration() {
        return "redirect:registration";
    }
}
