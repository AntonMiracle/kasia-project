package com.kasia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.kasia.controller.ViewName.HOME;

@Controller
@RequestMapping(HOME)
public class HomeController {
    @GetMapping
    public String openHome() {
        return HOME;
    }
}
